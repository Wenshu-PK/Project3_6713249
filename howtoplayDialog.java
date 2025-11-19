
package Project3_6713249;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class howtoplayDialog extends SelectionDialog {
    
    private menuButtonLabel okButton;
    public howtoplayDialog(String bg_path, String name, mainFrame owner) {
        super(bg_path, name, owner);
        okButton = new menuButtonLabel(constants.OKBUTTON, constants.OKBUTTON_HOVER, 200, 75, owner);
        okButton.setInitialLocation(constants.frameWidth - 200 - margin, constants.frameHeight - 75 - margin);
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicked: ok");
                owner.setVisible(true);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                okButton.setAltIcon();

            }

            @Override
            public void mouseExited(MouseEvent e) {

                okButton.setMainIcon();

            }
        });
        contentpane.add(okButton);
        setVisible(true);
        
}
}
