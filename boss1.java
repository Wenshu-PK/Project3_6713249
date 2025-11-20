package Project3_6713249;

import javax.swing.*;
import java.awt.event.*;

public class boss1 extends boss {

    private JLabel markLabel;   // red mark on ground
    private JLabel laserLabel;  // laser beam

    private boolean isAttacking = false;
    private boolean laserActive      = false;
    private boolean laserHitThisShot = false;

    // delays (milliseconds) – set by initStats()
    private int prepDelayMs;      // time between mark and laser
    private int laserDurationMs;  // how long laser stays visible

    public boss1(GameEngine game, int difficulty) {
        super(game, difficulty);

        // ----- load sprites -----
        MyImageIcon normal = new MyImageIcon(constants.BOSS1_NORMAL);
        MyImageIcon attack = new MyImageIcon(constants.BOSS1_ATTACK);
        MyImageIcon hurt   = new MyImageIcon(constants.BOSS1_HURT);
        MyImageIcon dead   = new MyImageIcon(constants.BOSS1_DEFEATED);

        setSprites(normal, attack, hurt, dead);

        
        int bossW = normal.getIconWidth();
        int bossH = normal.getIconHeight();
        
        setSize(bossW, bossH);
        
        // initial position: near top center
        int startX = (constants.frameWidth - getWidth()) / 2;
        int startY = 80;
        setBounds(startX, startY, bossW, bossH);

        // stats depend on difficulty
        initStats();

        // ----- create mark & laser labels -----
        // --- mark label ---
        MyImageIcon markIcon = new MyImageIcon(constants.BOSS1_MARK);
        markLabel = new JLabel(markIcon);
        markLabel.setSize(markIcon.getIconWidth(), markIcon.getIconHeight());
        markLabel.setVisible(false);

        // --- laser label ---
        MyImageIcon laserIcon = new MyImageIcon(constants.BOSS1_LASER);
        laserLabel = new JLabel(laserIcon);
        laserLabel.setSize(laserIcon.getIconWidth(), laserIcon.getIconHeight());
        laserLabel.setVisible(false);

        game.addGameObject(markLabel);
        game.addGameObject(laserLabel);
    }

    @Override
    protected void initStats() {
        if (difficulty == 0) {          // Easy
            maxHP = hp = 200;
            moveSpeed = 3;
            contactDamage = 10;
            attackInterval  = 2500;     // ms between each attack
            prepDelayMs     = 900;      // time from mark → laser
            laserDurationMs = 450;      // how long laser stays visible
        } else if (difficulty == 1) {   // Normal
            maxHP = hp = 250;
            moveSpeed = 4;
            contactDamage = 15;
            attackInterval  = 2100;
            prepDelayMs     = 700;
            laserDurationMs = 380;
        } else {                        // Hard
            maxHP = hp = 300;
            moveSpeed = 5;
            contactDamage = 20;
            attackInterval  = 1700;
            prepDelayMs     = 500;
            laserDurationMs = 320;
        }
    }
    
    // ------------ main boss loop (Thread) ------------
    @Override
    public void run() {
        int tickMs = 30;           // how often we update (ms)
        int timeSinceLastAttack = 0;
        int attackTime = 0;        // time inside current attack phase

        while (game.getRunning() && hp > 0) {

            if (!isAttacking) {
                // normal movement: follow player X
                moveStep();

                timeSinceLastAttack += tickMs;
                if (timeSinceLastAttack >= attackInterval) {
                    startAttackPhase();
                    timeSinceLastAttack = 0;
                    attackTime = 0;
                }
            } else {
                // update attack timing
                attackTime += tickMs;
                updateAttack(attackTime);
            }

            try {
                Thread.sleep(tickMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // keep UFO above the player, unless attacking
    private void moveStep() {
        if (isAttacking) return;

        PlayerLabel player = game.getPlayerLabel();
        if (player == null) return;

        int playerCenterX = player.getX() + player.getWidth() / 2;
        int bossCenterX   = getX() + getWidth() / 2;

        int dx = 0;
        if (bossCenterX < playerCenterX) {
            dx = moveSpeed;
        } else if (bossCenterX > playerCenterX) {
            dx = -moveSpeed;
        }

        int newX = getX() + dx;

        int minX = 0;
        int maxX = constants.frameWidth - getWidth();
        if (newX < minX) newX = minX;
        if (newX > maxX) newX = maxX;

        setLocation(newX, getY());
    }

    // ---------- attack phase control ----------

    // start full attack: show mark under boss
    private void startAttackPhase() {
        isAttacking      = true;
        laserActive      = false;
        laserHitThisShot = false;

        int bossCenterX = getX() + getWidth() / 2;

        int markX = bossCenterX - markLabel.getWidth() / 2;
        int markY = constants.GROUND_Y + constants.PLAYER_HEIGHT - markLabel.getHeight();
        markLabel.setBounds(markX, markY, markLabel.getWidth(), markLabel.getHeight());
        markLabel.setVisible(true);

        // at this moment only mark is visible, laser will come later in updateAttack
        setIcon(imgNormal);   // still normal look while marking
    }

    // called every tick while attacking
    private void updateAttack(int attackTimeMs) {

        // 1) after prepDelayMs → show laser under boss
        if (!laserActive && attackTimeMs >= prepDelayMs) {
            showLaserUnderBoss();
            laserActive = true;
        }

        // 2) while laser is active and before end of duration → check hit once
        if (laserActive && attackTimeMs < prepDelayMs + laserDurationMs) {
            PlayerLabel player = game.getPlayerLabel();
            if (player != null &&
                !laserHitThisShot &&
                laserLabel.getBounds().intersects(player.getBounds())) {

                // hit only once per shot
                game.damagePlayer(contactDamage);
                laserHitThisShot = true;
                try { Thread.sleep(300); } 
                catch (InterruptedException e) { e.printStackTrace();}
            }
        }

        // 3) after laser duration → end attack
        if (attackTimeMs >= prepDelayMs + laserDurationMs) {
            endAttack();
        }
    }

    private void showLaserUnderBoss() {
        int bossCenterX = getX() + getWidth() / 2;

        int laserX = bossCenterX - laserLabel.getWidth() / 2;

        // Y position: slightly under the UFO hull
        int OFFSET_HULL = (int)(getHeight() * 0.1);
        int laserY = getY() + getHeight() - OFFSET_HULL;

        laserLabel.setBounds(laserX, laserY,
                             laserLabel.getWidth(), laserLabel.getHeight());
        laserLabel.setVisible(true);

        // change boss image to attack sprite
        if (imgAttack != null) {
            setIcon(imgAttack);
        }
    }

    private void endAttack() {
        // hide mark & laser
        markLabel.setVisible(false);
        laserLabel.setVisible(false);

        // back to normal movement
        isAttacking      = false;
        laserActive      = false;
        laserHitThisShot = false;

        if (imgNormal != null) {
            setIcon(imgNormal);
        }
    }
}