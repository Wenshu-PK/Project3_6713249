package Project3_6713249;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ScoreboardDialog extends JDialog {

    private mainFrame menu;
     private GameEngine game;
    private JDialog parentDialog;
    
    public ScoreboardDialog(GameEngine game, ScoreManager manager, mainFrame owner, JDialog parentDialog) {

        super(owner, "Scoreboard", true); // modal
        this.menu = owner;
        this.game = game;
        this.parentDialog = parentDialog;
        setSize(constants.frameWidth, constants.frameHeight);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

       
         addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (parentDialog != null) parentDialog.dispose(); // close GameResultDialog
                game.dispose();  // close GameEngine
                menu.setVisible(true);
            }
        });

        JLabel bgLabel = new JLabel(new MyImageIcon(constants.BG_END));
        bgLabel.setLayout(new BorderLayout());
        setContentPane(bgLabel);

        String[] cols = {"Rank", "Name", "Icon", "Score"};
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

        JScrollPane sp = new JScrollPane(table);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        bgLabel.add(sp, BorderLayout.CENTER);

        JButton back = new JButton("Back to Menu");
        bgLabel.add(back, BorderLayout.SOUTH);

        back.addActionListener(ev -> {
            dispose();          // close Scoreboard
            if (parentDialog != null) parentDialog.dispose(); // close GameResultDialog
            game.dispose();     // close GameEngine
            menu.setVisible(true);
        });

        setVisible(true);
    }
}
