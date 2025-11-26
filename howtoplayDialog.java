package Project3_6713249;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JLabel;

public class howtoplayDialog extends SelectionDialog {

    public howtoplayDialog(String bg_path, String name, mainFrame owner) {
        super(bg_path, name, owner);
        returnButton = new menuButtonLabel(constants.OKBUTTON, constants.OKBUTTON_HOVER, 200, 75, owner);
        returnButton.setInitialLocation(constants.frameWidth - 200 - margin, constants.frameHeight - 75 - margin);
        returnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.getButton() == MouseEvent.BUTTON2) {

                    System.out.println("click ignored");
                    return; // 
                }
                System.out.println("Clicked: ok");
                owner.setVisible(true);
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
        contentpane.add(returnButton);
        setVisible(true);

    }
}
