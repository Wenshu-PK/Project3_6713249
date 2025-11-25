package Project3_6713249;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class mainFrame extends JFrame {

    private mainFrame menuframe = this;
    private JLabel contentpane;
    private menuButtonLabel startButton, settingButton, exitButton, creditButton, howbutton;
    private bossSelection boss;
    private playerselection player;
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 60;
    private final int SPACING_X = 100;
    private final int SPACING_Y = 30;
    private final int ROW1_Y = 380;
   

    // [Max] Added sound management variables to handle background music and volume state globally.
    private MySoundEffect currentThemeSound;
    private int currentVolume = 50; // [Max] Default volume set to 50.

    public mainFrame() {
        int centerX = (constants.frameWidth - BUTTON_WIDTH) / 2;
        int row1Y = ROW1_Y;

        int twoColWidth = BUTTON_WIDTH * 2 + SPACING_X;

        int leftX = (constants.frameWidth - twoColWidth) / 2;
        int rightX = leftX + BUTTON_WIDTH + SPACING_X;

        int row2Y = row1Y + BUTTON_HEIGHT + SPACING_Y;
        int row3Y = row2Y + BUTTON_HEIGHT + SPACING_Y;

        setTitle("Menu");
        setSize(constants.frameWidth, constants.frameHeight);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        this.requestFocus();

        setContentPane(contentpane = new JLabel());

        MyImageIcon background = new MyImageIcon(constants.MENU_BG_GIF);
        contentpane.setIcon(background);
        contentpane.setLayout(null);

        startButton = new menuButtonLabel(constants.STARTBUTTON, constants.STARTBUTTON_HOVER, BUTTON_WIDTH, BUTTON_HEIGHT, this);
        startButton.setInitialLocation(centerX, row1Y);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.getButton() == MouseEvent.BUTTON2) {
                    JLabel msg3 = new JLabel("Can't click", SwingConstants.CENTER);
                    msg3.setFont(new Font("Monospaced", Font.BOLD, 20));
                    msg3.setForeground(Color.RED);
                    int msgWidth = BUTTON_WIDTH;
                    int msgHeight = 30;
                    int msgX = centerX;
                    int msgY = row1Y - msgHeight - 5;
                    msg3.setBounds(msgX, msgY, msgWidth, msgHeight);
                    contentpane.add(msg3);
                    contentpane.revalidate();
                    contentpane.repaint();
                    
                    
                    new javax.swing.Timer(2000, ev -> {
                        contentpane.remove(msg3);
                        contentpane.revalidate();
                        contentpane.repaint();
                    }) {
                        {
                            setRepeats(false);   // ให้ทำครั้งเดียว
                        }
                    }.start();
                    
                    System.out.println("click ignored");
                    return;
                }
                System.out.println("Clicked: start");
                setVisible(false);

                new playerselection(constants.PLAYER_BG, "Player Selction", menuframe);
               

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                startButton.setAltIcon();
            }

            @Override
            public void mouseExited(MouseEvent e) {

                startButton.setMainIcon();

            }
        });

        settingButton = new menuButtonLabel(constants.SETTINGBUTTON, constants.SETTINGBUTTON_HOVER, BUTTON_WIDTH, BUTTON_HEIGHT, this);
        settingButton.setInitialLocation(leftX, row2Y);
        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (SwingUtilities.isRightMouseButton(e) || e.getButton() == MouseEvent.BUTTON2) {
                    JLabel msg3 = new JLabel("Can't click", SwingConstants.CENTER);
                    msg3.setFont(new Font("Monospaced", Font.BOLD, 20));
                    msg3.setForeground(Color.RED);
                    int msgWidth = BUTTON_WIDTH;
                    int msgHeight = 30;
                    int msgX = leftX;
                    int msgY = row2Y - msgHeight - 5;
                    msg3.setBounds(msgX, msgY, msgWidth, msgHeight);
                    contentpane.add(msg3);
                    contentpane.revalidate();
                    contentpane.repaint();
                  
                    
                    new javax.swing.Timer(2000, ev -> {
                        contentpane.remove(msg3);
                        contentpane.revalidate();
                        contentpane.repaint();
                    }) {
                        {
                            setRepeats(false);   // ให้ทำครั้งเดียว
                        }
                    }.start();
                    
                    System.out.println("click ignored");
                    return;
                }
                System.out.println("Clicked: setting");

                // [Max] Modified logic: Removed setVisible(false) to keep the main menu visible in the background.
                // setVisible(false);  <-- [Max] Commented out or removed.
                // [Max] Added instantiation of settingDialog as a modal popup on top of the menu.
                new settingDialog(constants.SETTING_BG, "Settings", menuframe);
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                settingButton.setAltIcon();
            }

            @Override
            public void mouseExited(MouseEvent e) {

                settingButton.setMainIcon();
            }
        });

        exitButton = new menuButtonLabel(constants.EXITBUTTON, constants.EXITBUTTON_HOVER, BUTTON_WIDTH, BUTTON_HEIGHT, this);
        exitButton.setInitialLocation(rightX, row3Y);   // [ Exit    ]
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.getButton() == MouseEvent.BUTTON2) {
                    JLabel msg3 = new JLabel("Can't click", SwingConstants.CENTER);
                    msg3.setFont(new Font("Monospaced", Font.BOLD, 20));
                    msg3.setForeground(Color.RED);
                    int msgWidth = BUTTON_WIDTH;
                    int msgHeight = 30;
                    int msgX = rightX;
                    int msgY = row3Y - msgHeight - 5;
                    msg3.setBounds(msgX, msgY, msgWidth, msgHeight);
                    contentpane.add(msg3);
                    contentpane.revalidate();
                    contentpane.repaint();
                   
                    
                    new javax.swing.Timer(2000, ev -> {
                        contentpane.remove(msg3);
                        contentpane.revalidate();
                        contentpane.repaint();
                    }) {
                        {
                            setRepeats(false);   // ให้ทำครั้งเดียว
                        }
                    }.start();
                    
                    System.out.println("click ignored");
                    return;
                }
                System.out.println("Clicked: exit");
                System.exit(0);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setAltIcon();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setMainIcon();
            }
        });

        creditButton = new menuButtonLabel(constants.CREDITBUTTON, constants.CREDITBUTTON_HOVER, BUTTON_WIDTH, BUTTON_HEIGHT, this);
        creditButton.setInitialLocation(leftX, row3Y);   // [ Credit  
        creditButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.getButton() == MouseEvent.BUTTON2) {
                    JLabel msg3 = new JLabel("Can't click", SwingConstants.CENTER);
                    msg3.setFont(new Font("Monospaced", Font.BOLD, 20));
                    msg3.setForeground(Color.RED);
                    int msgWidth = BUTTON_WIDTH;
                    int msgHeight = 30;
                    int msgX = leftX;
                    int msgY = row3Y - msgHeight - 5;
                    msg3.setBounds(msgX, msgY, msgWidth, msgHeight);
                    contentpane.add(msg3);
                    contentpane.revalidate();
                    contentpane.repaint();
                    
                    
                    new javax.swing.Timer(2000, ev -> {
                        contentpane.remove(msg3);
                        contentpane.revalidate();
                        contentpane.repaint();
                    }) {
                        {
                            setRepeats(false);   // ให้ทำครั้งเดียว
                        }
                    }.start();
                    
                    System.out.println("click ignored");
                    return;
                }
                System.out.println("Clicked: credit");
                setVisible(false);
                new creditDialog(constants.CREDIT, "Credit", menuframe);
              

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                creditButton.setAltIcon();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                creditButton.setMainIcon();
            }
        });

        howbutton = new menuButtonLabel(constants.HOWTOBUTTON, constants.HOWTOBUTTON_HOVER, BUTTON_WIDTH, BUTTON_HEIGHT, this);
        howbutton.setInitialLocation(rightX, row2Y);
        howbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.getButton() == MouseEvent.BUTTON2) {
                    JLabel msg3 = new JLabel("Can't click", SwingConstants.CENTER);
                    msg3.setFont(new Font("Monospaced", Font.BOLD, 20));
                    msg3.setForeground(Color.RED);
                    int msgWidth = BUTTON_WIDTH;
                    int msgHeight = 30;
                    int msgX = rightX;
                    int msgY = row2Y - msgHeight - 5;
                    msg3.setBounds(msgX, msgY, msgWidth, msgHeight);
                    contentpane.add(msg3);
                    contentpane.revalidate();
                    contentpane.repaint();
                    new javax.swing.Timer(2000, ev -> {
                        contentpane.remove(msg3);
                        contentpane.revalidate();
                        contentpane.repaint();
                    }) {
                        {
                            setRepeats(false);   // ให้ทำครั้งเดียว
                        }
                    }.start();
                    
                    System.out.println("click ignored");
                    return;
                }
                System.out.println("Clicked: how to play");
                setVisible(false);
                new howtoplayDialog(constants.HOWTOPLAT_BG, "HOw to play", menuframe);
                

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                howbutton.setAltIcon();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                howbutton.setMainIcon();
            }
        });

        contentpane.add(startButton);
        contentpane.add(settingButton);
        contentpane.add(exitButton);
        contentpane.add(creditButton);
        contentpane.add(howbutton);

        // [Max] Added line to start playing the default theme song immediately when the menu loads.
        playTheme(constants.SONG_COSMIC);

    }
    

    
    // [Max] Added method to switch background music dynamically (called from SettingDialog).
    public void playTheme(String filePath) {
        if (currentThemeSound != null) {
            currentThemeSound.stop(); // [Max] Stops the previous song before playing a new one.
        }

        if (filePath != null) {
            currentThemeSound = new MySoundEffect(filePath);
            currentThemeSound.setVolume(currentVolume); // [Max] Ensures new song plays at the current volume level.
            currentThemeSound.playLoop();
        }
    }

    // [Max] Added method to update volume globally (called from SettingDialog's slider).
    public void setVolume(int volume) {
        this.currentVolume = volume;
        if (currentThemeSound != null) {
            currentThemeSound.setVolume(volume);
        }
    }

    // [Max] Added getter for volume so the slider in SettingDialog knows the initial position.
    public int getCurrentVolume() {
        return currentVolume;
    }

    // ... (Other parts of mainFrame) ..
}
