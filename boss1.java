package Project3_6713249;
/*
import javax.swing.*;
import java.awt.event.*;

public class boss1 extends BossBase {

    private JLabel markLabel;
    private JLabel laserLabel;

    public boss1(GameEngine game, int difficulty) {
        super(game, difficulty);

        // load images
        imgNormal   = new MyImageIcon(constants.Boss1).resize(200, 150);
        imgAttack   = new MyImageIcon(constants.Boss1_Damage1).resize(200, 150);
        imgHurt     = new MyImageIcon(constants.Boss1_shooted).resize(200, 150);
        imgDead     = new MyImageIcon(constants.Boss1_die).resize(200, 150);
        setIcon(imgNormal);

        // initial position: top center
        int startX = (constants.FRAME_WIDTH - getWidth()) / 2;
        int startY = 50;
        setBounds(startX, startY, getWidth(), getHeight());

        // stats depend on difficulty
        initStats();

        // create mark & laser labels
        markLabel  = new JLabel(new MyImageIcon(constants.Boss_direct_and_damage).resize(50, 20));
        markLabel.setVisible(false);

        laserLabel = new JLabel(new MyImageIcon(constants.BOSS1_Damage2).resize(20, 400));
        laserLabel.setVisible(false);

        // add them to game
        game.addGameObject(markLabel);
        game.addGameObject(laserLabel);

        // movement: follow player X
        moveTimer = new Timer(30, e -> moveStep());

        // attack every attackInterval ms
        attackTimer = new Timer(attackInterval, e -> startAttack());
    }

    @Override
    protected void initStats() {
        if (difficulty == 0) {         // Easy
            maxHP = hp = 50;
            moveSpeed = 3;
            attackInterval = 2000;     // 2 sec
            contactDamage = 10;
        } else if (difficulty == 1) {  // Medium
            maxHP = hp = 80;
            moveSpeed = 5;
            attackInterval = 1500;
            contactDamage = 15;
        } else {                       // Hard
            maxHP = hp = 120;
            moveSpeed = 7;
            attackInterval = 1000;
            contactDamage = 20;
        }
    }

    private void moveStep() {
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

        // keep inside frame
        int minX = 0;
        int maxX = constants.FRAME_WIDTH - getWidth();
        if (newX < minX) newX = minX;
        if (newX > maxX) newX = maxX;

        setLocation(newX, getY());
    }

    private void startAttack() {
        PlayerLabel player = game.getPlayerLabel();
        if (player == null) return;

        setIcon(imgAttack);

        // place mark at player's current X on ground
        int markX = player.getX() + player.getWidth() / 2 - markLabel.getWidth() / 2;
        int markY = constants.GROUND_Y;
        markLabel.setBounds(markX, markY, markLabel.getWidth(), markLabel.getHeight());
        markLabel.setVisible(true);

        // after 500 ms, fire laser
        new Timer(500, e -> {
            ((Timer)e.getSource()).stop();
            fireLaser(markX);
        }).start();
    }

    private void fireLaser(int markX) {
        markLabel.setVisible(false);

        // laser starts from top, above mark
        int laserX = markX + markLabel.getWidth()/2 - laserLabel.getWidth()/2;
        int laserY = 0;

        laserLabel.setBounds(laserX, laserY, laserLabel.getWidth(), laserLabel.getHeight());
        laserLabel.setVisible(true);

        Timer laserTimer = new Timer(20, null);
        laserTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newY = laserLabel.getY() + 20;
                laserLabel.setLocation(laserLabel.getX(), newY);

                PlayerLabel player = game.getPlayerLabel();
                if (player != null && laserLabel.getBounds().intersects(player.getBounds())) {
                    // hit player
                    game.damagePlayer(contactDamage);
                }

                // reached ground or below
                if (newY > constants.GROUND_Y) {
                    laserLabel.setVisible(false);
                    laserTimer.stop();
                    setIcon(imgNormal);
                }
            }
        });
        laserTimer.start();
    }
}*/
