/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project3_6713249;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author iamja
 */
class PlayerLabel extends JLabel
{
    
    private GameEngine      parentFrame;
    private MyImageIcon     leftImg, rightImg;
    
    private int width   = constants.PLAYER_WIDTH;
    private int height  = constants.PLAYER_HEIGHT;
    private int curY    = constants.GROUND_Y;
    private int curX    = 100;
    
    private boolean jumping    = false;
    public void setJumping(boolean j)   { jumping = j;}
    
    
    private int jumpVelocity;
    private final int JUMP_STRENGTH = -18;
    
    public PlayerLabel(GameEngine pf)
    {
        parentFrame = pf;
        leftImg = new MyImageIcon(constants.PlAYER_LEFT).resize(width, height);
        rightImg = new MyImageIcon(constants.PlAYER_LEFT).resize(width, height);
        
        setIcon(leftImg);
        setBounds(curX, curY, width, height);
    }
    public void updateLocation(int d)
    {
        int frameW = parentFrame.getContentPane().getWidth();
        if (d == 0)
        {   
            curX = curX - 10;
            if(jumping == true){
                curY = curY + jumpVelocity;
                jumpVelocity += 1;
                if(curY >= constants.GROUND_Y){
                    curY = constants.GROUND_Y;
                    jumping = false;
                }
            }
            if(curX + width - 150 < 0)
            {
                curX = 30;
            }
        }
        else if(d == 1)
        {
            curX = curX + 10;
            if(jumping == true){
                curY = curY + jumpVelocity;
                jumpVelocity += 1;
                if(curY >= constants.GROUND_Y){
                    curY = constants.GROUND_Y;
                    jumping = false;
                }
            }
            if(curX + width + 100 > frameW )
            {
                curX = frameW - width - 30;
            }
        }
        setLocation(curX, curY);
        repaint();             
    }
    public void jump() {
    if (!jumping && curY == constants.GROUND_Y) {
        jumping = true;
        jumpVelocity = JUMP_STRENGTH;
    }
}
}
