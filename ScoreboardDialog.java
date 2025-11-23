package Project3_6713249;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ScoreboardDialog extends SelectionDialog {

    private mainFrame menu;
    private GameEngine game;
    private JDialog parentDialog;

    public ScoreboardDialog(GameEngine game, ScoreManager manager, mainFrame owner, JDialog parentDialog) {

        // ------------------------------------
        // SelectionDialog
        // ------------------------------------
        super(constants.BG_TABLE, "Scoreboard", owner);

        this.menu = owner;
        this.game = game;
        this.parentDialog = parentDialog;

        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Handle close
        for (WindowListener wl : getWindowListeners()) {
            removeWindowListener(wl);
        }
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (parentDialog != null) parentDialog.dispose();
                game.dispose();
                menu.setVisible(true);
            }
        });

        // ---------------------------------------------------------
        // TABLE: Top Scores
        // ---------------------------------------------------------
        String[] cols = {"Rank", "Name", "Icon", "Score"};
        
        JLabel msg = new JLabel("SCORE⌂BOARD");
        msg.setFont(new Font("Monospaced", Font.BOLD, 50));
        msg.setForeground(Color.YELLOW);
        msg.setBounds(frameWidth/3+10 , -25, frameWidth, 180);
        contentpane.add(msg);

        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public Class<?> getColumnClass(int c) {
                return (c == 2) ? ImageIcon.class : String.class;
            }
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        int rank = 1;
        for (ScoreEntry e : manager.getTopScores()) {
            model.addRow(new Object[]{
                    rank++,
                    e.getName(),
                    IconProvider.loadIcon(e.getIconIndex(), 60),
                    String.valueOf(e.getScore())
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(80);
        table.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 160), 2, true));
        table.setFont(new Font("Monospaced", Font.BOLD, 18));
        table.setForeground(new Color(0, 0, 128));
        

        JScrollPane sp = new JScrollPane(table);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        

        
        sp.setBounds(100, 100, frameWidth - 200, frameHeight - 250);
        contentpane.add(sp);

        // ---------------------------------------------------------
        // BACK BUTTON
        // ---------------------------------------------------------
        /*JButton back = new JButton("Back to Menu");
        back.setFont(new Font("Monospaced", Font.BOLD, 28));
        back.setBounds(frameWidth - 350 - margin, frameHeight - 100 - margin, 350, 60);
*/
        menuButtonLabel back = new menuButtonLabel(
                    constants.BACKTOBUTTON, constants.BACKTOBUTTON_HOVER,
                    200, 91, menu
            );

            back.setBounds(frameWidth - 200 - margin, frameHeight - 90 - margin, 200,91);

            back.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { back.setAltIcon(); }
                @Override
                public void mouseExited(MouseEvent e) { back.setMainIcon(); }
            });

        
        back.addActionListener(ev -> {
            dispose();
            if (parentDialog != null) parentDialog.dispose();
            game.dispose();
            menu.setVisible(true);
        });

        contentpane.add(back);

        setVisible(true);
    }
}
