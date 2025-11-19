package Project3_6713249;

import javax.swing.*;
import java.awt.event.*;

public class boss1 extends boss {

    private JLabel markLabel;   // red mark on ground
    private JLabel laserLabel;  // laser beam

    private boolean isAttacking = false;

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
        int startX = (constants.FRAME_WIDTH - getWidth()) / 2;
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

        // movement: follow player X (unless attacking)
        moveTimer = new Timer(30, e -> moveStep());

        // attack every attackInterval ms
        attackTimer = new Timer(attackInterval, e -> startAttack());
    }

    @Override
    protected void initStats() {
        if (difficulty == 0) {          // Easy
            maxHP = hp = 200;
            moveSpeed = 3;
            contactDamage = 10;
            attackInterval  = 2200;     // ms between each attack
            prepDelayMs     = 900;      // time from mark → laser
            laserDurationMs = 450;      // how long laser stays visible
        } else if (difficulty == 1) {   // Normal
            maxHP = hp = 250;
            moveSpeed = 4;
            contactDamage = 15;
            attackInterval  = 1800;
            prepDelayMs     = 750;
            laserDurationMs = 350;
        } else {                        // Hard
            maxHP = hp = 300;
            moveSpeed = 5;
            contactDamage = 20;
            attackInterval  = 1400;
            prepDelayMs     = 600;
            laserDurationMs = 300;
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
        int maxX = constants.FRAME_WIDTH - getWidth();
        if (newX < minX) newX = minX;
        if (newX > maxX) newX = maxX;

        setLocation(newX, getY());
    }

    // start full attack: show mark, then later fire laser
    private void startAttack() {
    if (isAttacking) return;

    PlayerLabel player = game.getPlayerLabel();
    if (player == null) return;

    // freeze movement
    isAttacking = true;

    // use UFO center, not player, so mark is exactly under the UFO
    int bossCenterX = getX() + getWidth() / 2;

    int markX = bossCenterX - markLabel.getWidth() / 2;
    int markY = constants.GROUND_Y + constants.PLAYER_HEIGHT - markLabel.getHeight();
    markLabel.setBounds(markX, markY, markLabel.getWidth(), markLabel.getHeight());
    markLabel.setVisible(true);

    // after delay, fire laser starting from UFO center
    new Timer(prepDelayMs, e -> {
        ((Timer)e.getSource()).stop();
        fireLaser(bossCenterX);
    }).start();
}


    // laser appears on the mark, checks damage, then disappears
    private void fireLaser(int bossCenterX) {
    markLabel.setVisible(false);

    setIcon(imgAttack);

    // laser X based on UFO center (same as mark)
    int laserX = bossCenterX - laserLabel.getWidth() / 2;

    // choose a Y that’s "under the UFO hull"
    // you can tweak OFFSET_HULL if it doesn’t look perfect
    int OFFSET_HULL = (int)(getHeight() * 0.1); // small gap below ship
    int laserY = getY() + getHeight() - OFFSET_HULL;

    laserLabel.setBounds(laserX, laserY,
                         laserLabel.getWidth(), laserLabel.getHeight());
    laserLabel.setVisible(true);

    int tickMs = 50;
    Timer laserTimer = new Timer(tickMs, null);

    laserTimer.addActionListener(new ActionListener() {
        int elapsed = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            elapsed += tickMs;

            PlayerLabel player = game.getPlayerLabel();
            if (player != null && laserLabel.getBounds().intersects(player.getBounds())) {
                game.damagePlayer(contactDamage);
            }

            if (elapsed >= laserDurationMs) {
                laserLabel.setVisible(false);
                laserTimer.stop();
                setIcon(imgNormal);
                isAttacking = false;  // UFO can move again
            }
        }
    });

    laserTimer.start();
}

}
