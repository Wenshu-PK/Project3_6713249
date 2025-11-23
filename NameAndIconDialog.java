package Project3_6713249;

import javax.swing.*;
import java.awt.*;

public class NameAndIconDialog extends JDialog {

    private mainFrame menu;
    private GameEngine game;
    private JDialog parentDialog;

    public NameAndIconDialog(GameEngine gaame, ScoreManager manager, int score, mainFrame owner, JDialog parent) {
        super(owner, "Player Info", true); // modal
        this.game = gaame;
        this.menu = owner;
        this.parentDialog = parent;

        setSize(constants.frameWidth, constants.frameHeight);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Handle window closing
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (parentDialog != null) parentDialog.dispose();
                game.dispose();
                menu.setVisible(true);
            }
        });

        // Background
        JLabel bgLabel = new JLabel(new MyImageIcon(constants.BG_END));
        bgLabel.setLayout(new BorderLayout());
        setContentPane(bgLabel);

        // Center panel
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        bgLabel.add(center, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        // "Enter Name" label
        JLabel nameLabel = new JLabel("Enter Name:", SwingConstants.CENTER);
        nameLabel.setForeground(Color.YELLOW);
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        center.add(nameLabel, gbc);

        // Name text field
        gbc.gridy++;
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Monospaced", Font.BOLD, 26));
        nameField.setForeground(new Color(0, 0, 128)); // กรมท่า
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(nameField, gbc);

        // "Choose Icon" label
        gbc.gridy++;
        JLabel iconLabel = new JLabel("Choose Icon:", SwingConstants.CENTER);
        iconLabel.setForeground(Color.YELLOW);
        iconLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        center.add(iconLabel, gbc);

        // Spinner
        gbc.gridy++;
        String[] items = {"Icon 1", "Icon 2", "Icon 3"};
        JSpinner spinner = new JSpinner(new SpinnerListModel(items));
        JComponent editor = spinner.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setFont(new Font("Monospaced", Font.BOLD, 24));
        tf.setForeground(new Color(0, 0, 128)); // กรมท่า
        tf.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(spinner, gbc);

        // Preview label
        JLabel preview = new JLabel("", SwingConstants.CENTER);
        preview.setIcon(IconProvider.loadIcon(0, 150));
        bgLabel.add(preview, BorderLayout.NORTH);

        spinner.addChangeListener(e -> {
            SpinnerListModel model = (SpinnerListModel) spinner.getModel();
            int idx = model.getList().indexOf(spinner.getValue());
            preview.setIcon(IconProvider.loadIcon(idx, 150));
        });

        // OK button
        JButton ok = new JButton("OK");
        ok.setFont(new Font("Monospaced", Font.BOLD, 22));
        bgLabel.add(ok, BorderLayout.SOUTH);

        ok.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) name = "Anonymous";

            int index = ((SpinnerListModel) spinner.getModel())
                    .getList().indexOf(spinner.getValue());

            manager.addScore(name, index, score);
            dispose();

            SwingUtilities.invokeLater(() ->
                    new ScoreboardDialog(game, manager, menu, parentDialog)
            );
        });

        setVisible(true);
    }
}
