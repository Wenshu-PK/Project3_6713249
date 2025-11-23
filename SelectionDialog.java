package Project3_6713249;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 *
 * @author sawan
 */
public class SelectionDialog extends JDialog {

    protected int frameWidth = constants.frameWidth;
    protected int frameHeight = constants.frameHeight;
    protected JLabel contentpane;
    protected mainFrame parentFrame;

    // shared layout values for all selection dialogs
    protected int margin = 60;
    protected int charSize = 300;
    protected int charY = 100;
    protected int radioY = charY + charSize + 20;
    protected int spacing = 40;
    protected int centerX = frameWidth / 2;
    protected int char2X = centerX - charSize / 2;
    protected int char1X = char2X - spacing - charSize;
    protected int char3X = char2X + charSize + spacing;

    // shared radio buttons
    protected JRadioButton jRadioButton1, jRadioButton2, jRadioButton3;

    // shared return button
    protected menuButtonLabel returnButton;

    public SelectionDialog(String bg_path, String name, mainFrame owner) {
        super(owner, name);
        this.parentFrame = owner;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); 
            }
        });

        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);

        contentpane = new JLabel();
        contentpane.setLayout(null);
        MyImageIcon background = new MyImageIcon(bg_path);
        contentpane.setIcon(background);
        setContentPane(contentpane);
    }
}
