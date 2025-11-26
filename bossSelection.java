package Project3_6713249;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ItemEvent;
import javax.swing.ButtonGroup;
import javax.swing.*;
import java.awt.event.*;

public class bossSelection extends SelectionDialog {

    protected JLabel Boss1, Boss2;
    private menuButtonLabel fightButton;
    private String[] difficulty = {"Easy", "Medium", "Hard"};
    private JComboBox Difficulty;

    private int comboWidth = 200;
    private int comboHeight = 30;
    private int comboX = (frameWidth - comboWidth) / 2;
    private int comboY = 450;

    private int bossGap = 50;

    public bossSelection(String bg_path, String name, mainFrame owner,
            playerselection prevois, int player_selected) {

        super(bg_path, name, owner);

        int totalBossWidth = (charSize * 2) + bossGap;
        int startX = (frameWidth - totalBossWidth) / 2;
        Boss1 = new JLabel();
        Boss1.setIcon(new MyImageIcon(constants.BOSS1).resize(charSize, charSize));
        Boss1.setBounds(startX, charY, charSize, charSize);

        Boss2 = new JLabel();
        Boss2.setIcon(new MyImageIcon(constants.BOSS2).resize(charSize, charSize));
        Boss2.setBounds(startX + charSize + bossGap, charY, charSize, charSize);

        jRadioButton1 = new JRadioButton("Ufo");
        jRadioButton2 = new JRadioButton("Alien");

        jRadioButton1.setForeground(Color.WHITE);
        jRadioButton2.setForeground(Color.WHITE);

        Font radioFont = new Font("Monospaced", Font.BOLD, 16); // or whatever you want
        jRadioButton1.setFont(radioFont);
        jRadioButton2.setFont(radioFont);

        for (JRadioButton rb : new JRadioButton[]{jRadioButton1, jRadioButton2}) {
            rb.setOpaque(false);
            rb.setContentAreaFilled(false);
            rb.setFocusPainted(false);
        }

        ButtonGroup group = new ButtonGroup();
        group.add(jRadioButton1);
        group.add(jRadioButton2);

        Dimension rb1Size = jRadioButton1.getPreferredSize();
        Dimension rb2Size = jRadioButton2.getPreferredSize();

        jRadioButton1.setBounds(
                startX + (charSize - rb1Size.width) / 2,
                radioY,
                rb1Size.width,
                rb1Size.height
        );

        jRadioButton2.setBounds(
                startX + charSize + bossGap + (charSize - rb2Size.width) / 2,
                radioY,
                rb2Size.width,
                rb2Size.height
        );
        jRadioButton1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Boss1.setIcon(new MyImageIcon(constants.BOSS1_HOVER).resize(charSize, charSize));
            } else {
                Boss1.setIcon(new MyImageIcon(constants.BOSS1).resize(charSize, charSize));
            }
        });
        jRadioButton2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Boss2.setIcon(new MyImageIcon(constants.BOSS2_HOVER).resize(charSize, charSize));
            } else {
                Boss2.setIcon(new MyImageIcon(constants.BOSS2).resize(charSize, charSize));
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
                prevois.setVisible(true);
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

        fightButton = new menuButtonLabel(constants.FIGHTBUTTON,
                constants.FIGHTBUTTON_HOVER, 200, 75, owner);
        fightButton.setInitialLocation(constants.frameWidth - 200 - margin, constants.frameHeight - 75 - margin);
        fightButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int boss_selected;
                if (SwingUtilities.isRightMouseButton(e) || e.getButton() == MouseEvent.BUTTON2) {

                    System.out.println("click ignored");
                    return; // 
                }

                if (jRadioButton1.isSelected()) {
                    System.out.println("Player 1 selected");
                    boss_selected = 1;
                } else if (jRadioButton2.isSelected()) {
                    System.out.println("Player 2 selected");
                    boss_selected = 2;
                } else {
                    System.out.println("No player selected!");
                    return;
                }
                new GameEngine(player_selected, boss_selected, Difficulty.getSelectedIndex(), owner);
                dispose(); //close bossSelection
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                fightButton.setAltIcon();
            }

            @Override
            public void mouseExited(MouseEvent e) {

                fightButton.setMainIcon();
            }
        });

        Difficulty = new JComboBox(difficulty);
        Difficulty.setFont(new Font("Arial", Font.PLAIN, 18));
        Difficulty.setBounds(comboX, comboY, comboWidth, comboHeight);

        contentpane.add(returnButton);
        contentpane.add(fightButton);
        contentpane.add(Difficulty);
        contentpane.add(Boss1);
        contentpane.add(Boss2);
        contentpane.add(jRadioButton1);
        contentpane.add(jRadioButton2);
        setVisible(true);
    }

}
