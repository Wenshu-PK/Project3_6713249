/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project3_6713249;

//Anun Luechaphongthip 6713253
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class GameEngine extends JFrame
{
     private JPanel             contentpane;
     private JLabel             drawpane;
     
     private PlayerLabel        playerLabel;
     private boss               bossLabel;
     private GameEngine         currentFrame;
     private HPBar              bossHPBar;
     private HPBar              playerHPBar;
 
     
     private MyImageIcon        backgroundImg;    
     private MySoundEffect      themeSound;
    
     private int framewidth  = constants.frameWidth;
     private int frameheight = constants.frameHeight;
     private int playerHP, bossHP;
     private boolean iRunning;
     
     public boolean getRunning()     {return iRunning;}
     public void removeItem(JLabel item)  { drawpane.remove(item); repaint(); }
     
     // So boss can know the player position
    public PlayerLabel getPlayerLabel() {
    return playerLabel;
    }
    
    // So boss can damage player HP bar
    public void damagePlayer(int dmg) {
        if (playerHPBar != null) {
            playerHPBar.takeDamage(dmg);
            if (playerHPBar.getHP() <= 0) {
                // TODO: show lose screen later
                System.out.println("Player died");
                iRunning = false;
            }
        }
    }

    // So boss can take damage (if you add player attack later)
    public void damageBoss(int dmg) {
        if (bossHPBar != null) {
            bossHPBar.takeDamage(dmg);
            if (bossHPBar.getHP() <= 0) {
                // TODO: show win screen later
                System.out.println("Boss died");
                iRunning = false;
            }
        }
    }

    // allow boss to add extra labels (laser, marks, explosions...) to game area
    public void addGameObject(JLabel lbl) {
        drawpane.add(lbl);
        drawpane.repaint();
    }

    private void createBoss(int bossType, int difficulty) {

        if (bossType == 1) {
            bossLabel = new boss1(this, difficulty);
        }
        // later:
        // else if (bossType == 2) bossLabel = new boss2(this, difficulty);
        // else if (bossType == 3) bossLabel = new boss3(this, difficulty);

        if (bossLabel != null) {
            // add boss to the game area
            drawpane.add(bossLabel);

            // start boss thread
            Thread bossThread = new Thread(bossLabel);
            bossThread.start();
        }
    }

    
    
     public GameEngine(int p, int b, int d)
     {
         setTitle("George Droid battle");
         setSize(framewidth, frameheight);
         setLocationRelativeTo(null);
         
         setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
         currentFrame = this;
         this.iRunning = true;
         
         contentpane = (JPanel)getContentPane();
	 contentpane.setLayout( new BorderLayout() );        
         AddComponents(p,b,d);
         setVisible(true);
     }
     public void AddComponents(int p, int b, int d)
     {
         
         backgroundImg  = new MyImageIcon(constants.FILE_BG).resize(framewidth, frameheight);
         drawpane = new JLabel();
         drawpane.setPreferredSize(new Dimension(framewidth, frameheight));
	 drawpane.setIcon(backgroundImg);
         drawpane.setLayout(null);
         
         //themeSound = new MySoundEffect(MyConstants.FILE_THEME); 
         //themeSound.playLoop(); themeSound.setVolume(0.2f);
         
         playerLabel = new PlayerLabel(currentFrame, p);
         drawpane.add(playerLabel);
         Thread playerThread = new Thread(playerLabel);
         playerThread.start();
         this.addKeyListener( new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                int kc = e.getKeyCode();
                if(kc == KeyEvent.VK_A || kc == KeyEvent.VK_D)
                { playerLabel.setMove(false); playerLabel.setDirection(-1);}
            } 
            @Override
            public void keyPressed(KeyEvent e) {
                int kc = e.getKeyCode();
                if(kc == KeyEvent.VK_A) { playerLabel.setMove(true); playerLabel.setDirection(0);}
                else if(kc == KeyEvent.VK_D) { playerLabel.setMove(true); playerLabel.setDirection(1);}
                else if(kc == KeyEvent.VK_SPACE) { playerLabel.jump();}
            }
         });
         // --- create boss ---
        createBoss(b, d);    // bossLabel is created and thread started

        // boss HP bar uses real boss max HP (fallback 100 if something weird)
        int bossMaxHP = (bossLabel != null) ? bossLabel.getMaxHP() : 100;
        bossHPBar = new HPBar(currentFrame, 1, bossMaxHP);
        drawpane.add(bossHPBar);

        // player HP bar (100 for now)
        playerHPBar = new HPBar(currentFrame, 2, 100);
        drawpane.add(playerHPBar);

        contentpane.add(drawpane, BorderLayout.CENTER);
        validate();
    }
}

