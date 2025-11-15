package Project3_6713249;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author sawan
 */
public class SelectionDialog extends JDialog {

    protected int frameWidth = constants.frameWidth;
    protected int frameHeight = constants.frameHeight;
    protected JLabel contentpane;
    protected mainFrame parentFrame;

    protected int margin = 60;
    protected int charSize = 300;
    protected int charY = 100;
    protected int radioY = charY + charSize + 20;
    protected int spacing = 40;
    protected int centerX = frameWidth / 2;
    protected int char2X = centerX - charSize / 2;
    protected int char1X = char2X - spacing - charSize;
    protected int char3X = char2X + charSize + spacing;

    protected JRadioButton jRadioButton1, jRadioButton2, jRadioButton3;

    protected menuButtonLabel returnButton;

    public SelectionDialog(String bg_path, String name, mainFrame owner) {
        super(owner, name);
        this.parentFrame = owner;

        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);


        contentpane = new JLabel();
        contentpane.setLayout(null);
        MyImageIcon background = new MyImageIcon(bg_path);
        contentpane.setIcon(background);
        setContentPane(contentpane);
    }
}

