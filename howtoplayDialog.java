
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
                        JLabel msg2 = new JLabel("Can't click");
                        msg2.setFont(new Font("Monospaced", Font.BOLD, 20));
                        msg2.setForeground(Color.RED);
                        msg2.setBounds(constants.frameWidth - 200 - margin, constants.frameHeight - 190 - margin, frameWidth, 180);
                        contentpane.add(msg2);
                        contentpane.revalidate();
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
