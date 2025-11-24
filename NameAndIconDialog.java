package Project3_6713249;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NameAndIconDialog extends SelectionDialog {

    private mainFrame menu;
    private GameEngine game;
    private GameResultDialog parentDialog;

    public NameAndIconDialog(
            GameEngine gaame,
            ScoreManager manager,
            int score,
            mainFrame owner
            
    ) {

        // ---------------------------------
        // constructor SelectionDialog
        // ---------------------------------
        super(constants.BG_END, "Player Info", owner);

        this.game = gaame;
        this.menu = owner;
        

        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Handle closing
        /*for (WindowListener wl : getWindowListeners()) {
            removeWindowListener(wl);
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (parentDialog != null) parentDialog.dispose();
                if (game != null) game.dispose();
                menu.setVisible(true);
            }
        });*/

        // ==========================================================
        // PREVIEW ICON
        // ==========================================================
        JLabel preview = new JLabel("", SwingConstants.CENTER);
        preview.setIcon(IconProvider.loadIcon(0, 150));
        preview.setBounds(frameWidth / 2 - 75, 40, 150, 150);
        contentpane.add(preview);

        // ==========================================================
        // Enter Name
        // ==========================================================
        JLabel nameLabel = new JLabel("Enter Name:");
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
        nameLabel.setForeground(Color.YELLOW);
        nameLabel.setBounds(frameWidth / 3, 210, 400, 40);
        contentpane.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Monospaced", Font.BOLD, 26));
        nameField.setForeground(new Color(0, 0, 128));
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setBounds(frameWidth / 3, 260, 400, 50);
        nameField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 128), 10, true));      
        contentpane.add(nameField);

        // ==========================================================
        // Choose Icon (Spinner)
        // ==========================================================
        JLabel iconLabel = new JLabel("Choose Icon:");
        iconLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        iconLabel.setForeground(Color.YELLOW);
        iconLabel.setBounds(frameWidth / 3, 330, 400, 40);
        contentpane.add(iconLabel);

        String[] items = {"Icon 1", "Icon 2", "Icon 3"};
        JSpinner spinner = new JSpinner(new SpinnerListModel(items));
        spinner.setBounds(frameWidth / 3, 380, 400, 50);

        // spinner text style
        JComponent editor = spinner.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setFont(new Font("Monospaced", Font.BOLD, 24));
        tf.setForeground(new Color(0, 0, 128));
        tf.setHorizontalAlignment(SwingConstants.CENTER);
        tf.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 128), 10, true)); 

        contentpane.add(spinner);

        // update preview on spinner change
        spinner.addChangeListener(e -> {
            int idx = ((SpinnerListModel) spinner.getModel())
                    .getList().indexOf(spinner.getValue());
            preview.setIcon(IconProvider.loadIcon(idx, 150));
        });

        // ==========================================================
        // OK Button
        // ==========================================================
        menuButtonLabel ok = new menuButtonLabel(
                constants.OKBUTTON,
                constants.OKBUTTON_HOVER,
                200, 60,
                menu
        );

        ok.setBounds(
                frameWidth - 200 - margin,
                frameHeight - 75 - margin,
                200,
                60
        );

        ok.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { ok.setAltIcon(); }
            @Override public void mouseExited(MouseEvent e) { ok.setMainIcon(); }
        });

        ok.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) name = "Anonymous";

            int iconIndex = ((SpinnerListModel) spinner.getModel())
                    .getList().indexOf(spinner.getValue());

            manager.addScore(name, iconIndex, score);

            dispose();

            SwingUtilities.invokeLater(() ->
                    new ScoreboardDialog(game, manager, menu));
        });

        contentpane.add(ok);

        setVisible(true);
    }
}
