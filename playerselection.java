package Project3_6713249;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.*;
import javax.swing.JRadioButton;
import java.awt.event.ItemEvent;
import java.awt.Font;
import java.awt.Color;

public class playerselection extends SelectionDialog {

    protected JLabel char1, char2, char3;
    private menuButtonLabel nextButton;

    public playerselection(String bg_path, String name, mainFrame owner) {
        super(bg_path, name, owner);
        playerselection pervious = this;

        char1 = new JLabel();
        char1.setIcon(new MyImageIcon(constants.PLAYER_1).resize(charSize, charSize));
        char1.setBounds(char1X, charY, charSize, charSize);

        char2 = new JLabel();
        char2.setIcon(new MyImageIcon(constants.PLAYER_2).resize(charSize, charSize));
        char2.setBounds(char2X, charY, charSize, charSize);

        char3 = new JLabel();
        char3.setIcon(new MyImageIcon(constants.PLAYER_3).resize(charSize, charSize));
        char3.setBounds(char3X, charY, charSize, charSize);

        jRadioButton1 = new JRadioButton("Max");
        jRadioButton2 = new JRadioButton("Est");
        jRadioButton3 = new JRadioButton("Louis");

        Font radioFont = new Font("Monospaced", Font.BOLD, 16); // or whatever you want
        jRadioButton1.setFont(radioFont);
        jRadioButton2.setFont(radioFont);
        jRadioButton3.setFont(radioFont);

        jRadioButton1.setForeground(Color.WHITE);
        jRadioButton2.setForeground(Color.WHITE);
        jRadioButton3.setForeground(Color.WHITE);
        for (JRadioButton rb : new JRadioButton[]{jRadioButton1, jRadioButton2, jRadioButton3}) {
            rb.setOpaque(false);
            rb.setContentAreaFilled(false);
            rb.setFocusPainted(false);
        }
        ButtonGroup group = new ButtonGroup();
        group.add(jRadioButton1);
        group.add(jRadioButton2);
        group.add(jRadioButton3);

        Dimension rb1Size = jRadioButton1.getPreferredSize();
        Dimension rb2Size = jRadioButton2.getPreferredSize();
        Dimension rb3Size = jRadioButton3.getPreferredSize();

        // center each radio under its character (x + (charSize - rbWidth)/2)
        jRadioButton1.setBounds(
                char1X + (charSize - rb1Size.width) / 2,
                radioY,
                rb1Size.width,
                rb1Size.height
        );

        jRadioButton2.setBounds(
                char2X + (charSize - rb2Size.width) / 2,
                radioY,
                rb2Size.width,
                rb2Size.height
        );

        jRadioButton3.setBounds(
                char3X + (charSize - rb3Size.width) / 2,
                radioY,
                rb3Size.width,
                rb3Size.height
        );

        jRadioButton1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                char1.setIcon(new MyImageIcon(constants.PLAYER_1_HOVER).resize(charSize, charSize));
            } else {
                char1.setIcon(new MyImageIcon(constants.PLAYER_1).resize(charSize, charSize));
            }
        });
        jRadioButton2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                char2.setIcon(new MyImageIcon(constants.PLAYER_2_HOVER).resize(charSize, charSize));
            } else {
                char2.setIcon(new MyImageIcon(constants.PLAYER_2).resize(charSize, charSize));
            }
        });
        jRadioButton3.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                char3.setIcon(new MyImageIcon(constants.PLAYER_3_HOVER).resize(charSize, charSize));
            } else {
                char3.setIcon(new MyImageIcon(constants.PLAYER_3).resize(charSize, charSize));
            }
        });
        jRadioButton1.setSelected(true);

        returnButton = new menuButtonLabel(constants.RETURNBUTTON, constants.RETURNBUTTON_HOVER, 200, 75, owner);
        returnButton.setInitialLocation(margin, constants.frameHeight - 75 - margin);
        returnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.getButton() == MouseEvent.BUTTON2) {

                    System.out.println("click So sad ");
                    return; // 
                }
                System.out.println("Clicked: return");
                owner.setVisible(true);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                returnButton.setAltIcon();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                returnButton.setMainIcon();
            }
        });

        nextButton = new menuButtonLabel(constants.NEXTBUTTON, constants.NEXTBUTTON_HOVER, 200, 75, owner);
        nextButton.setInitialLocation(constants.frameWidth - 200 - margin, constants.frameHeight - 75 - margin);
        nextButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.getButton() == MouseEvent.BUTTON2) {

                    System.out.println("click ignored");
                    return; // 
                }
                int player_selected;
                if (jRadioButton1.isSelected()) {
                    System.out.println("Player 1 selected");
                    player_selected = 1;
                } else if (jRadioButton2.isSelected()) {
                    System.out.println("Player 2 selected");
                    player_selected = 2;
                } else if (jRadioButton3.isSelected()) {
                    System.out.println("Player 3 selected");
                    player_selected = 3;
                } else {
                    System.out.println("No player selected!");
                    return;
                }
                System.out.println("Click: next");
                pervious.setVisible(false);
                new bossSelection(constants.BOSS_BG, "Boss Selction", owner, pervious, player_selected);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nextButton.setAltIcon();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nextButton.setMainIcon();
            }
        });

        contentpane.add(char1);
        contentpane.add(char2);
        contentpane.add(char3);
        contentpane.add(jRadioButton1);
        contentpane.add(jRadioButton2);
        contentpane.add(jRadioButton3);
        contentpane.add(returnButton);
        contentpane.add(nextButton);

        setVisible(true);
    }

}
