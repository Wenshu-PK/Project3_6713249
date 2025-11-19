/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project3_6713249;

//Anun Luechaphongthip 6713253
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class GameEngine extends JFrame
{
     private JPanel             contentpane;
     private JLabel             drawpane;
     
     private PlayerLabel        playerLabel;
     private GameEngine         currentFrame;
     
     private MyImageIcon        backgroundImg;    
     private MySoundEffect      themeSound;
    
     private int framewidth  = constants.FRAME_WIDTH;
     private int frameheight = constants.FRAME_HEIGHT;
     private int playerHP, bossHP;
     private boolean iRunning;
     
     
     public void removeItem(JLabel item)  { drawpane.remove(item); repaint(); }
     
     public GameEngine()
     {
         setTitle("George Droid battle");
         setSize(framewidth, frameheight);
         setLocationRelativeTo(null);
         setVisible(true);
         setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
         currentFrame = this;
         this.iRunning = true;
         
         contentpane = (JPanel)getContentPane();
	 contentpane.setLayout( new BorderLayout() );        
         AddComponents();
     }
     public void AddComponents()
     {
         backgroundImg  = new MyImageIcon(constants.FILE_BG).resize(framewidth, frameheight-100);
         drawpane = new JLabel();
	 drawpane.setIcon(backgroundImg);
         drawpane.setLayout(null);
         //themeSound = new MySoundEffect(MyConstants.FILE_THEME); 
         //themeSound.playLoop(); themeSound.setVolume(0.2f);
         
         playerLabel = new PlayerLabel(currentFrame);
         drawpane.add(playerLabel);
         this.addKeyListener( new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {} 
            @Override
            public void keyPressed(KeyEvent e) {
                int kc = e.getKeyCode();
                if(kc == KeyEvent.VK_A) { playerLabel.updateLocation(0);}
                else if(kc == KeyEvent.VK_D) { playerLabel.updateLocation(1);}
                else if(kc == KeyEvent.VK_SPACE) { playerLabel.jump();}
            }
         });
         contentpane.add(drawpane, BorderLayout.CENTER);
         validate();
     }
     public void setGirlThread()
    {
        Thread playerThread = new Thread() {
            public void run()
            {
                while (iRunning)
                {
                    
                }          
            } // end run
        }; // end thread creation
        playerThread.start();
    }    
}
