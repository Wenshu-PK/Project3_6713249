package Project3_6713249;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameResultDialog extends JDialog {

    private GameEngine game;
    private mainFrame menu;
    private int margin = 60;

    public GameResultDialog(GameEngine gaame, boolean win, int time, int dmg, mainFrame owner) {

        super(owner, "Result", true); // modal 

        this.game = gaame;
        this.menu = owner;

        setSize(constants.frameWidth, constants.frameHeight);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Handle
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (game != null) game.dispose();
                menu.setVisible(true);
            }
        });

        JLabel bgLabel = new JLabel(new MyImageIcon(constants.BG_END));
bgLabel.setLayout(null); // ปิด layout manager
setContentPane(bgLabel);

// WIN/LOSE
JLabel msg = new JLabel(win ? "YOU WIN!" : "YOU LOSE!", SwingConstants.CENTER);
msg.setFont(new Font("Monospaced", Font.BOLD, 150)); 
msg.setForeground(Color.YELLOW);
// กำหนดตำแหน่งเอง
msg.setBounds(0, 20, constants.frameWidth, 180);
bgLabel.add(msg);

if (win) {
    int score = Math.max(0, 10000 / (time + 1) - 5 * dmg);

    JLabel Score = new JLabel("Score: " + score, SwingConstants.CENTER);
    Score.setFont(new Font("Monospaced", Font.PLAIN, 36));
    Score.setForeground(Color.WHITE);
    Score.setBounds(0, 220, constants.frameWidth, 50);
    bgLabel.add(Score);

    // ปุ่ม Next
    menuButtonLabel next = new menuButtonLabel(constants.NEXTB, constants.NEXTB_HOVER, 200, 60, menu);
    next.setBounds(constants.frameWidth - 200 - margin, constants.frameHeight - 75 - margin, 200, 60);

    next.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) { next.setAltIcon(); }
        @Override
        public void mouseExited(MouseEvent e) { next.setMainIcon(); }
    });

    next.addActionListener(e -> {
        new NameAndIconDialog(game, game.getScoreManager(), score, menu, this);
    });

    bgLabel.add(next);

} else {
    menuButtonLabel back = new menuButtonLabel(constants.BACKB, constants.BACKB_HOVER, 200, 60, menu);
    back.setBounds(constants.frameWidth - 200 - margin, constants.frameHeight - 75 - margin, 200, 60);

    back.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) { back.setAltIcon(); }
        @Override
        public void mouseExited(MouseEvent e) { back.setMainIcon(); }
    });

    back.addActionListener(e -> {
        dispose();
        game.dispose();
        menu.setVisible(true);
    });

    bgLabel.add(back);
}


        setVisible(true);
    }
}
