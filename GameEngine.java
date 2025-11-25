package Project3_6713249;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class GameEngine extends JFrame {

    private JPanel contentpane;
    private JLabel drawpane;

    private PlayerLabel playerLabel;
    private boss bossLabel;
    private GameEngine currentFrame;
    private HPBar bossHPBar;
    private HPBar playerHPBar;
    private GunLabel gunLabel;

    private MyImageIcon backgroundImg;
    private MySoundEffect themeSound;

    private int framewidth = constants.frameWidth;
    private int frameheight = constants.frameHeight;
    private int damageTaken;
    private int diff;
    private double chargeStartTime;
    private double gameStartTime;
    private double gameEndTime;
    private double timePlayed;
    private boolean charging = false;
    private boolean pA,pD;
    private boolean iRunning;

    //louis
    private mainFrame menu;

    private ScoreManager scoreManager = new ScoreManager();

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public boolean getRunning() {
        return iRunning;
    }

    public void removeItem(JLabel item) {
        drawpane.remove(item);
        repaint();
    }

    // So boss can know the player position
    public PlayerLabel getPlayerLabel() {
        return playerLabel;
    }

    // allow boss to add extra labels (laser, marks, explosions...) to game area
    public void addGameObject(JLabel lbl) {
        drawpane.add(lbl);
        drawpane.repaint();
    }

    private void createBoss(int bossType, int difficulty) {

        if (bossType == 1) {
            bossLabel = new boss1(this, difficulty);
        } else if (bossType == 2) {
            bossLabel = new boss2(this, difficulty);
        }

        if (bossLabel != null) {
            // add boss to the game area
            drawpane.add(bossLabel);

            // start boss thread
            Thread bossThread = new Thread(bossLabel);
            bossThread.start();
        }
    }

    public GameEngine(int p, int b, int d, mainFrame menu) {
        setTitle("Chocolate Hunter: Battle");
        setSize(framewidth, frameheight);
        setLocationRelativeTo(null);
        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        currentFrame = this;
        this.iRunning = true;
        this.diff = d;
        this.menu = menu;

        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());
        AddComponents(p, b, d);
        setVisible(true);
        gameStartTime = System.currentTimeMillis();
    }

    private void AddComponents(int p, int b, int d) {
        backgroundImg = new MyImageIcon(constants.FILE_BG).resize(framewidth, frameheight);
        drawpane = new JLabel();
        drawpane.setPreferredSize(new Dimension(framewidth, frameheight));
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);

        playerLabel = new PlayerLabel(currentFrame, p);
        gunLabel = new GunLabel(playerLabel);
        playerLabel.setGunLabel(gunLabel);
        createBoss(b, d);
        drawpane.add(gunLabel);
        drawpane.add(playerLabel);
        Thread playerThread = new Thread(playerLabel);
        playerThread.start();

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                int kc = e.getKeyCode();
                if (kc == KeyEvent.VK_A || kc == KeyEvent.VK_D) {
                    if(kc == KeyEvent.VK_A)
                    {pA = false;}
                    else if(kc == KeyEvent.VK_D)
                    {pD = false;}
                    if(!pA && !pD) { playerLabel.setMove(false); }                    
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int kc = e.getKeyCode();
                switch (kc) {
                    case KeyEvent.VK_A:
                        Pressing(1);
                        playerLabel.setMove(true);
                        playerLabel.setDirection(0);
                        break;
                    case KeyEvent.VK_D:
                        Pressing(2);
                        playerLabel.setMove(true);
                        playerLabel.setDirection(1);
                        break;
                    case KeyEvent.VK_SPACE:
                        playerLabel.jump(charging);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.getButton() == MouseEvent.BUTTON2) {
                    return;
                }
                charging = true;
                gunLabel.gCharging(true);
                playerLabel.setSpeed(5);
                chargeStartTime = System.currentTimeMillis();
                double chargeDuration = System.currentTimeMillis() - chargeStartTime;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.getButton() == MouseEvent.BUTTON2) {
                    return;
                }
                if (charging) {
                    int finalX = e.getX();
                    int finalY = e.getY();
                    charging = false;
                    gunLabel.gCharging(false);
                    double chargeDuration = System.currentTimeMillis() - chargeStartTime;
                    if (chargeDuration >= 3000) {
                        playerProjLabel pLProj = new playerProjLabel(currentFrame, bossLabel, playerLabel, 2, finalX, finalY);
                        drawpane.add(pLProj);
                        Thread pLProjThread = new Thread(pLProj);
                        pLProjThread.start();
                    } else {
                        playerProjLabel pProj = new playerProjLabel(currentFrame, bossLabel, playerLabel, 1, finalX, finalY);
                        drawpane.add(pProj);
                        Thread pProjThread = new Thread(pProj);
                        pProjThread.start();
                    }
                    playerLabel.setSpeed(8);
                }
            }
        });
        
        bossHPBar = new HPBar(currentFrame, 1, bossLabel.getMaxHP());
        drawpane.add(bossHPBar);
        playerHPBar = new HPBar(currentFrame, 2, playerLabel.getMaxHP());
        drawpane.add(playerHPBar);
        playerLabel.setHPBar(playerHPBar);
        bossLabel.setHPBar(bossHPBar);
        contentpane.add(drawpane, BorderLayout.CENTER);
        validate();
    }
    public void Pressing(int i)  
    {
        if(i == 1)
        {
            pA = true;
        }
        else
        {
            pD = true;
        }
    }
    public void GameEnd(boolean win) {
        iRunning = false;
        gameEndTime = System.currentTimeMillis();
        timePlayed = (gameEndTime - gameStartTime) / 1000;
        damageTaken = playerLabel.getMaxHP() - playerLabel.getHP();
        try { Thread.sleep(3000); } 
        catch (InterruptedException e) { e.printStackTrace(); }
        SwingUtilities.invokeLater(() -> {
            new GameResultDialog(this, win, (int) timePlayed, damageTaken, playerLabel.getMaxHP(), playerLabel.getHP(), menu, diff);

        });
    }
}

