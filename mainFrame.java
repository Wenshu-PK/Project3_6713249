package Project3_6713249;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainFrame extends JFrame {

    private mainFrame menuframe = this;
    private JLabel contentpane;
    private menuButtonLabel startButton, settingButton, exitButton, creditButton, howbutton;
    private bossSelection boss;
    private playerselection player;
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 75;
    private final int SPACING_X = 40;
    private final int SPACING_Y = 20;
    private final int ROW1_Y = 300;

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
      
        startButton = new menuButtonLabel(constants.STARTBUTTON, constants.STARTBUTTON_HOVER, 200, 75, this);
        startButton.setInitialLocation(centerX, row1Y);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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

        settingButton = new menuButtonLabel(constants.SETTINGBUTTON, constants.SETTINGBUTTON_HOVER, 200, 75, this);
        settingButton.setInitialLocation(leftX, row2Y);
        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicked: setting");

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

        exitButton = new menuButtonLabel(constants.EXITBUTTON, constants.EXITBUTTON_HOVER, 200, 75, this);
        exitButton.setInitialLocation(rightX, row3Y);   // [ Exit    ]
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicked: exit");
                dispose();

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

        creditButton = new menuButtonLabel(constants.EXITBUTTON, constants.EXITBUTTON_HOVER, 200, 75, this);
        creditButton.setInitialLocation(leftX, row3Y);   // [ Credit  
        creditButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicked: credit");

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

        howbutton = new menuButtonLabel(constants.EXITBUTTON, constants.EXITBUTTON_HOVER, 200, 75, this);
        howbutton.setInitialLocation(rightX, row2Y);
        howbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicked: how to play");

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

    }

}
