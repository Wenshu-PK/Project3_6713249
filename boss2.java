package Project3_6713249;

import javax.swing.*;
import java.util.Random;
import java.awt.*;

public class boss2 extends boss {

    // --- attack elements ---
    private static final int MAX_MARKS = 5;   // 1 on player + up to 4 random (hard)

    private JLabel[] markLabels      = new JLabel[MAX_MARKS];
    private JLabel[] bombLabels      = new JLabel[MAX_MARKS];
    private JLabel[] explosionLabels = new JLabel[MAX_MARKS];

    private double[] bombX  = new double[MAX_MARKS];
    private double[] bombY  = new double[MAX_MARKS];
    private double[] bombVx = new double[MAX_MARKS];
    private double[] bombVy = new double[MAX_MARKS];

    private boolean[] bombActive      = new boolean[MAX_MARKS];
    private boolean[] explosionActive = new boolean[MAX_MARKS];
    private int[]     explosionTimeMs = new int[MAX_MARKS];

    private int markCount = 0;

    // attack state
    private boolean isAttacking = false;
    private boolean bombsCreated = false;

    // timing (ms) – set by initStats()
    private int prepDelayMs;                 // time to show red marks before bombs are thrown
    private int explosionDurationMs = 1000;  // explosion visible time

    // movement
    private int dirX = 1;      // horizontal direction: 1 = right, -1 = left
    private int bobDir = 1;    // vertical bob direction
    private int bobMinY = 80;  // min Y for bobbing
    private int bobMaxY = 200; // max Y for bobbing

    private Random rand = new Random();

    public boss2(GameEngine game, int difficulty) {
        super(game, difficulty);

        // ----- load sprites -----
        MyImageIcon normal = new MyImageIcon(constants.BOSS2_NORMAL);
        MyImageIcon attack = new MyImageIcon(constants.BOSS2_ATTACK);
        MyImageIcon hurt   = new MyImageIcon(constants.BOSS2_HURT);
        MyImageIcon dead   = new MyImageIcon(constants.BOSS2_DEFEATED);

        setSprites(normal, attack, hurt, dead);

        // size big enough for every sprite
        int bossW = Math.max(Math.max(normal.getIconWidth(),  attack.getIconWidth()),
                             Math.max(hurt.getIconWidth(),    dead.getIconWidth()));
        int bossH = Math.max(Math.max(normal.getIconHeight(), attack.getIconHeight()),
                             Math.max(hurt.getIconHeight(),   dead.getIconHeight()));

        setSize(bossW, bossH);

        // initial position: near top center
        int startX = (constants.frameWidth - bossW) / 2;
        int startY = 120;
        setBounds(startX, startY, bossW, bossH);

        // stats depend on difficulty
        initStats();
    }

    @Override
    protected void initStats() {
        if (difficulty == 0) {          // Easy
            maxHP = hp = 220;
            moveSpeed = 3;
            contactDamage = 8;
            attackInterval  = 2500;
            prepDelayMs     = 900;
        } else if (difficulty == 1) {   // Normal
            maxHP = hp = 260;
            moveSpeed = 4;
            contactDamage = 12;
            attackInterval  = 2100;
            prepDelayMs     = 750;
        } else {                        // Hard
            maxHP = hp = 300;
            moveSpeed = 5;
            contactDamage = 16;
            attackInterval  = 1700;
            prepDelayMs     = 600;
        }
    }

    // ------------ main boss loop ------------
    @Override
    public void run() {
        int tickMs = 30;
        int timeSinceLastAttack = 0;
        int attackTime = 0;

        while (game.getRunning() && hp > 0) {

            if (!isAttacking) {
                moveStep();

                timeSinceLastAttack += tickMs;
                if (timeSinceLastAttack >= attackInterval) {
                    startAttackPhase();
                    timeSinceLastAttack = 0;
                    attackTime = 0;
                }
            } else {
                attackTime += tickMs;
                updateAttack(tickMs, attackTime);
            }

            try {
                Thread.sleep(tickMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // movement: zig-zag + bob
    private void moveStep() {
        if (isAttacking) return;

        int newX = getX() + dirX * moveSpeed;

        int minX = 0;
        int maxX = constants.frameWidth - getWidth();
        if (newX < minX) {
            newX = minX;
            dirX = 1;
        } else if (newX > maxX) {
            newX = maxX;
            dirX = -1;
        }

        if (rand.nextInt(100) < 2) {
            dirX = -dirX;
        }

        int newY = getY() + bobDir * 2;
        if (newY < bobMinY) {
            newY = bobMinY;
            bobDir = 1;
        } else if (newY > bobMaxY) {
            newY = bobMaxY;
            bobDir = -1;
        }

        setLocation(newX, newY);
    }

    // ---------- attack control ----------

    private void startAttackPhase() {
        isAttacking   = true;
        bombsCreated  = false;

        int extraMarks;
        if (difficulty == 0) {
            extraMarks = 2;
        } else if (difficulty == 1) {
            extraMarks = 3;
        } else {
            extraMarks = 4;
        }
        markCount = 1 + extraMarks;
        if (markCount > MAX_MARKS) markCount = MAX_MARKS;

        MyImageIcon markIconSample = new MyImageIcon(constants.BOSS1_MARK);
        int markH = markIconSample.getIconHeight();
        int markY = constants.GROUND_Y + constants.PLAYER_HEIGHT - markH;

        PlayerLabel player = game.getPlayerLabel();
        int playerCenterX = constants.frameWidth / 2;
        if (player != null) {
            playerCenterX = player.getX() + player.getWidth() / 2;
        }

        for (int i = 0; i < markCount; i++) {
            if (markLabels[i] == null) {
                MyImageIcon markIcon = new MyImageIcon(constants.BOSS1_MARK);
                JLabel mark = new JLabel(markIcon);
                mark.setSize(markIcon.getIconWidth(), markIcon.getIconHeight());
                mark.setVisible(false);
                markLabels[i] = mark;
                game.addGameObject(markLabels[i]);
            }

            int markX;
            if (i == 0) {
                markX = playerCenterX - markLabels[i].getWidth() / 2;
            } else {
                int margin = 80;
                int maxX = constants.frameWidth - markLabels[i].getWidth() - margin;
                int minX = margin;
                markX = rand.nextInt(Math.max(1, maxX - minX + 1)) + minX;
            }

            markLabels[i].setBounds(
                    markX,
                    markY,
                    markLabels[i].getWidth(),
                    markLabels[i].getHeight()
            );
            markLabels[i].setVisible(true);

            bombActive[i]      = false;
            explosionActive[i] = false;
        }

        setIcon(imgNormal);
    }

    private void updateAttack(int tickMs, int attackTimeMs) {

        if (!bombsCreated && attackTimeMs >= prepDelayMs) {
            createBombsForMarks(tickMs);
            bombsCreated = true;
        }

        if (bombsCreated) {
            updateBombsAndExplosions(tickMs);
        }

        boolean anyActive = false;
        for (int i = 0; i < markCount; i++) {
            if (bombActive[i] || explosionActive[i]) {
                anyActive = true;
                break;
            }
        }

        if (!anyActive && attackTimeMs > prepDelayMs) {
            endAttack();
        }
    }

    private void createBombsForMarks(int tickMs) {

        int airTimeMs = 900;
        int steps = Math.max(1, airTimeMs / tickMs);

        int bossCenterX = getX() + getWidth() / 2;
        int bossBottomY = getY() + getHeight() / 2;

        MyImageIcon explosionIconSample = new MyImageIcon(constants.BOSS2_EXPLOSION);

        for (int i = 0; i < markCount; i++) {
            JLabel mark = markLabels[i];
            if (mark == null || !mark.isVisible()) continue;

            if (bombLabels[i] == null) {
                MyImageIcon bombIcon = new MyImageIcon(constants.BOSS2_BOMB);
                JLabel bomb = new JLabel(bombIcon);
                bomb.setSize(bombIcon.getIconWidth(), bombIcon.getIconHeight());
                bomb.setVisible(false);
                bombLabels[i] = bomb;
                game.addGameObject(bombLabels[i]);
            }

            bombX[i] = bossCenterX - bombLabels[i].getWidth() / 2.0;
            bombY[i] = bossBottomY;

            int markCenterX = mark.getX() + mark.getWidth() / 2;
            bombVx[i] = (markCenterX - bossCenterX) / (double)steps;

            bombVy[i] = -8.0;

            bombLabels[i].setLocation((int)bombX[i], (int)bombY[i]);
            bombLabels[i].setVisible(true);

            bombActive[i] = true;

            if (explosionLabels[i] == null) {
                JLabel expl = new JLabel(explosionIconSample);
                expl.setSize(explosionIconSample.getIconWidth(), explosionIconSample.getIconHeight());
                expl.setVisible(false);
                explosionLabels[i] = expl;
                game.addGameObject(explosionLabels[i]);
            }

            explosionActive[i] = false;
            explosionTimeMs[i] = 0;
        }

        if (imgAttack != null) {
            setIcon(imgAttack);
        }
    }

    private void updateBombsAndExplosions(int tickMs) {

    PlayerLabel player = game.getPlayerLabel();
    int gravityPerTick = 1; // how fast bomb curves down

    for (int i = 0; i < markCount; i++) {

        // -------- bombs --------
        if (bombActive[i]) {
            // move bomb
            bombVy[i] += gravityPerTick;
            bombX[i] += bombVx[i];
            bombY[i] += bombVy[i];

            bombLabels[i].setLocation((int) bombX[i], (int) bombY[i]);

            // hitbox for bomb (smaller than image)
            Rectangle bombHit = makeHitBox(bombLabels[i], 0.20, 0.20); // cut 20% each side
            Rectangle playerHit = (player != null) ? player.getBounds() : new Rectangle();

            // 1) direct hit on player
            if (player != null && bombHit.intersects(playerHit)) {

                // damage ONCE here
                player.takeDamage(contactDamage);

                // show explosion where bomb is
                int centerX = bombLabels[i].getX() + bombLabels[i].getWidth() / 2;
                int centerY = bombLabels[i].getY() + bombLabels[i].getHeight() / 2;
                startExplosionAt(i, centerX, centerY);

                bombActive[i] = false;
                bombLabels[i].setVisible(false);
            }
            else {
                // 2) bomb reaches ground mark
                JLabel mark = markLabels[i];
                if (mark != null && explosionLabels[i] != null) {

                    int groundLine = mark.getY() + mark.getHeight();        // bottom of the red mark
                    int bombBottom = (int) bombY[i] + bombLabels[i].getHeight();

                    if (bombBottom >= groundLine) {
                        // optional: small area for ground damage (simple rectangle)
                        Rectangle groundArea = new Rectangle(
                                mark.getX(),
                                groundLine - 10,   // 10px high strip above ground
                                mark.getWidth(),
                                20
                        );
                        if (player != null && groundArea.intersects(playerHit)) {
                            player.takeDamage(contactDamage / 2); // weaker splash
                        }

                        int centerX = mark.getX() + mark.getWidth() / 2;
                        int centerY = groundLine;
                        startExplosionAt(i, centerX, centerY);

                        bombActive[i] = false;
                        bombLabels[i].setVisible(false);
                    }
                }
            }
        }

        // -------- explosions --------
        if (explosionActive[i]) {
            // ONLY timer, no more damage
            explosionTimeMs[i] -= tickMs;
            if (explosionTimeMs[i] <= 0) {
                explosionActive[i] = false;
                explosionLabels[i].setVisible(false);
            }
        }
    }
}


    private void startExplosionAt(int index, int centerX, int groundY) {
        JLabel expl = explosionLabels[index];
        if (expl == null) return;

        int exW = expl.getWidth();
        int exH = expl.getHeight();

        int exX = centerX - exW / 2;
        int exY = groundY - exH+5;

        expl.setBounds(exX, exY, exW, exH);
        expl.setVisible(true);

        explosionActive[index] = true;
        explosionTimeMs[index] = explosionDurationMs;

        if (markLabels[index] != null) {
            markLabels[index].setVisible(false);
        }
    }

    private void endAttack() {

        for (int i = 0; i < markCount; i++) {
            if (markLabels[i] != null)      markLabels[i].setVisible(false);
            if (bombLabels[i] != null)      bombLabels[i].setVisible(false);
            if (explosionLabels[i] != null) explosionLabels[i].setVisible(false);

            bombActive[i]      = false;
            explosionActive[i] = false;
        }

        isAttacking  = false;
        bombsCreated = false;

        if (imgNormal != null) {
            setIcon(imgNormal);
        }
    }
}
