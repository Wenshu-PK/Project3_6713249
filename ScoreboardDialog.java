package Project3_6713249;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ScoreboardDialog extends SelectionDialog {

    private mainFrame menu;
    private GameEngine game;
   

    public ScoreboardDialog(GameEngine game, ScoreManager manager, mainFrame owner) {

        // ------------------------------------
        // SelectionDialog
        // ------------------------------------
        super(constants.BG_TABLE, "Scoreboard", owner);

        this.menu = owner;
        this.game = game;
        

        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        

        // ---------------------------------------------------------
        // TABLE: Top Scores
        // ---------------------------------------------------------
        String[] cols = {"Rank", "Name", "Icon", "Score"};

        JLabel msg = new JLabel("SCORE⌂BOARD");
        msg.setFont(new Font("Monospaced", Font.BOLD, 50));
        msg.setForeground(Color.YELLOW);
        msg.setBounds(frameWidth / 3 + 10, -25, frameWidth, 180);
        contentpane.add(msg);
        
        JLabel msg2 = new JLabel("SCORE⌂BOARD");
        msg2.setFont(new Font("Monospaced", Font.BOLD, 50));
        msg2.setForeground(Color.lightGray);
        msg2.setBounds(frameWidth / 3 + 7, -23, frameWidth, 180);
        contentpane.add(msg2);

        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public Class<?> getColumnClass(int c) {
                return (c == 2) ? ImageIcon.class : String.class;
            }

            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
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
        
        menuButtonLabel back = new menuButtonLabel(
                constants.BACKTOBUTTON, constants.BACKTOBUTTON_HOVER,
                200, 91, menu
        );

        back.setBounds(frameWidth - 200 - margin, frameHeight - 90 - margin, 200, 91);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                
                if (SwingUtilities.isRightMouseButton(e)|| e.getButton() == MouseEvent.BUTTON2) {
                    JLabel msg1 = new JLabel("Can't click");
                    msg1.setFont(new Font("Monospaced", Font.BOLD, 20));
                    msg1.setForeground(Color.RED);
                    msg1.setBounds(constants.frameWidth - 200 - margin, constants.frameHeight - 190 - margin, frameWidth, 180);
                    contentpane.add(msg1);
                    contentpane.revalidate();   // <== สำคัญ
                    contentpane.repaint();
                    
                    new javax.swing.Timer(2000, ev -> {
                        contentpane.remove(msg2);
                        contentpane.revalidate();
                        contentpane.repaint();
                    }) {
                        {
                            setRepeats(false);   // ให้ทำครั้งเดียว
                        }
                    }.start();
                    
                    System.out.println("click ignored");
                    return; // 
                }
                
                menu.setVisible(true);
                dispose();          // close Scoreboard
                
                game.dispose();     // close GameEngine
                
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

        setVisible(true);
    }
}
