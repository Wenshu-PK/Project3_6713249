package Project3_6713249;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameResultDialog extends SelectionDialog {

    private GameEngine game;
    private mainFrame menu;

    public GameResultDialog(GameEngine gaame, boolean win, int time, int dmg, int hpmax, int hpremain, mainFrame owner, int d) {
        // --------------------------
        // parent constructor
        // --------------------------
        super(constants.BG_END, "Result", owner);
        GameResultDialog current = this;

        this.game = gaame;
        this.menu = owner;

        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Window Close
        /*for (WindowListener wl : getWindowListeners()) {
            removeWindowListener(wl);
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (game != null) {
                    game.dispose();
                }
                menu.setVisible(true);
            }
        });*/
        // ----------------------------------------------------
        // contentpane ( SelectionDialog)
        // ----------------------------------------------------
        JLabel msg = new JLabel(win ? "YOU WIN!" : "YOU LOSE!", SwingConstants.CENTER);
        msg.setFont(new Font("Monospaced", Font.BOLD, 150));
        msg.setForeground(Color.YELLOW);
        msg.setBounds(0, 20, frameWidth, 180);
        contentpane.add(msg);

        // WIN CASE
        if (win) {

            int score = Math.max(0, (1000000 / (time + dmg)) * (d + 1));

            JLabel scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
            scoreLabel.setFont(new Font("Monospaced", Font.PLAIN, 36));
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setBounds(0, 220, frameWidth, 50);
            contentpane.add(scoreLabel);

            JLabel hpLabel = new JLabel("Player hp: " + hpremain + " / " + hpmax, SwingConstants.CENTER);
            hpLabel.setFont(new Font("Monospaced", Font.PLAIN, 36));
            hpLabel.setForeground(Color.RED);
            hpLabel.setBounds(0, 320, frameWidth, 50);
            contentpane.add(hpLabel);

            JLabel TLabel = new JLabel("Survival Time: " + time + " s ", SwingConstants.CENTER);
            TLabel.setFont(new Font("Monospaced", Font.PLAIN, 36));
            TLabel.setForeground(Color.CYAN);
            TLabel.setBounds(0, 420, frameWidth, 50);
            contentpane.add(TLabel);

            menuButtonLabel next = new menuButtonLabel(
                    constants.NEXTBUTTON, constants.NEXTBUTTON_HOVER,
                    200, 60, menu
            );

            next.setBounds(frameWidth - 200 - margin, frameHeight - 75 - margin, 200, 60);

            next.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e) || e.getButton() == MouseEvent.BUTTON2) {
                        JLabel msg2 = new JLabel("Can't click");
                        msg2.setFont(new Font("Monospaced", Font.BOLD, 20));
                        msg2.setForeground(Color.RED);
                        msg2.setBounds(constants.frameWidth - 200 - margin, constants.frameHeight - 190 - margin, frameWidth, 180);
                        contentpane.add(msg2);
                        contentpane.revalidate();
                        contentpane.repaint();

                        System.out.println("click ignored");
                        return; // 
                    }
                    current.dispose();
                    new NameAndIconDialog(game, game.getScoreManager(), score, menu);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    next.setAltIcon();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    next.setMainIcon();
                }
            });

            contentpane.add(next);

        } else {

            // LOSE CASE
            menuButtonLabel back = new menuButtonLabel(
                    constants.BACKBUTTON, constants.BACKBUTTON_HOVER,
                    200, 60, menu
            );

            back.setBounds(frameWidth - 200 - margin, frameHeight - 75 - margin, 200, 60);

            back.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e) || e.getButton() == MouseEvent.BUTTON2) {
                        JLabel msg2 = new JLabel("Can't click");
                        msg2.setFont(new Font("Monospaced", Font.BOLD, 20));
                        msg2.setForeground(Color.RED);
                        msg2.setBounds(constants.frameWidth - 200 - margin, constants.frameHeight - 190 - margin, frameWidth, 180);
                        contentpane.add(msg2);
                        contentpane.revalidate();
                        contentpane.repaint();

                        System.out.println("click ignored");
                        return; // 
                    }
                    dispose();
                    game.dispose();
                    menu.setVisible(true);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    back.setAltIcon();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    back.setMainIcon();
                }
            });

            contentpane.add(back);
        }

        setVisible(true);
    }
}
