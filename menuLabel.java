
package Project3_6713249;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class BaseLabel extends JButton{
    
    protected MyImageIcon iconMain, iconAlt;
    protected int curX, curY, width, height;
    protected boolean horizontalMove, verticalMove;
    protected mainFrame parentFrame;

    public BaseLabel() {
    }

    public BaseLabel(String file_1,String file_2,int w, int h, mainFrame pf) {
        width = w;
        height = h;
        iconMain = new MyImageIcon(file_1).resize(width, height);
        setIcon(iconMain);
        setHorizontalAlignment(JLabel.CENTER);
        parentFrame = pf;
        if (file_2 != null) {
            iconAlt = new MyImageIcon(file_2).resize(width, height);
        } else {
            iconAlt = null;
        }
        
    }

    public void setInitialLocation(int x, int y) {
        curX = x;
        curY = y;
        setBounds(curX, curY, width, height);
    }
    
    
    public void setMainIcon() {
        setIcon(iconMain);
    }

    public void setAltIcon() {
        setIcon(iconAlt);
    }
   
}


class menuButtonLabel extends BaseLabel{


public menuButtonLabel(String file1,String file2 ,int w, int h, mainFrame pf) {
        super(file1,file2, w, h, pf);
    }

}