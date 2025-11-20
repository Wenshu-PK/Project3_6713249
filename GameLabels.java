/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project3_6713249;
import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author iamja
 */
class PlayerLabel extends JLabel implements Runnable
{
    
    private GameEngine      parentFrame;
    private MyImageIcon     leftImg, rightImg, leftJumpImg, rightJumpImg, shotLeft, shotRight, deadImg;
    
    private int width   = constants.PLAYER_WIDTH;
    private int height  = constants.PLAYER_HEIGHT;
    private int curY    = constants.GROUND_Y;
    private int curX    = 100;
    private int direction;
    private int maxHP = 100;
    private int hp;
    
    private boolean jumping    = false;
    private boolean move       = false;
    public void setJumping(boolean j)   {jumping = j;}
    public void setMove(boolean m)      {move = m;}
    public void setDirection(int d)     {direction = d;}
    
    public int getMaxHP()               {return maxHP;}
    public int getHP()                  {return hp;}
    public void takeDamage(int d)            
    {
        hp -= d;
        if(d == 0)
        {
            setIcon(shotLeft);
        }
        else
        {
            setIcon(shotRight);
        }
        move = false;
        repaint();
        try { Thread.sleep(200); } 
        catch (InterruptedException e) { e.printStackTrace(); }
    }
    
    private int jumpVelocity;
    private final int JUMP_STRENGTH = -18;
    
    public PlayerLabel(GameEngine pf, int playerType)
    {
        parentFrame = pf;
        switch (playerType){
            case 1: {leftImg = new MyImageIcon(constants.PlAYER1_LEFT);
                     rightImg = new MyImageIcon(constants.PLAYER1_RIGHT);
                     leftJumpImg = new MyImageIcon(constants.PLAYER1_JUMP_LEFT);
                     rightJumpImg = new MyImageIcon(constants.PLAYER1_JUMP_RIGHT);
                     shotLeft = new MyImageIcon(constants.PLAYER1_SHOT_LEFT);
                     shotRight = new MyImageIcon(constants.PLAYER1_SHOT_RIGHT);
                     deadImg = new MyImageIcon(constants.PLAYER1_DEAD);
                     break;}
            case 2: {leftImg = new MyImageIcon(constants.PlAYER2_LEFT);
                     rightImg = new MyImageIcon(constants.PLAYER2_RIGHT);
                     leftJumpImg = new MyImageIcon(constants.PLAYER2_JUMP_LEFT);
                     rightJumpImg = new MyImageIcon(constants.PLAYER2_JUMP_RIGHT);
                     shotLeft = new MyImageIcon(constants.PLAYER2_SHOT_LEFT);
                     shotRight = new MyImageIcon(constants.PLAYER2_SHOT_RIGHT);
                     deadImg = new MyImageIcon(constants.PLAYER2_DEAD);
                     break;}
            case 3: {leftImg = new MyImageIcon(constants.PlAYER3_LEFT);
                     rightImg = new MyImageIcon(constants.PLAYER3_RIGHT);
                     leftJumpImg = new MyImageIcon(constants.PLAYER3_JUMP_LEFT);
                     rightJumpImg = new MyImageIcon(constants.PLAYER3_JUMP_RIGHT);
                     shotLeft = new MyImageIcon(constants.PLAYER3_SHOT_LEFT);
                     shotRight = new MyImageIcon(constants.PLAYER3_SHOT_RIGHT);
                     deadImg = new MyImageIcon(constants.PLAYER3_DEAD);
                     break;}
            default: break;
        }
        hp = maxHP;
        setIcon(rightImg);
        setBounds(curX, curY, width, height);
    }
    @Override
    public void run()
    {
        while(parentFrame.getRunning())
        {
            updateLocation(move, direction);
            if(hp <= 0)
            {
                setIcon(deadImg);
                repaint();
            }
        }
    }
    public void updateLocation(boolean m, int d)
    {
        int frameW = parentFrame.getContentPane().getWidth();
        if(jumping == true){
            if (d == 0)
            {
                setIcon(leftJumpImg);
            }
            else
            {
                setIcon(rightJumpImg);
            }
            curY = curY + jumpVelocity;
            jumpVelocity += 1;
            if(curY >= constants.GROUND_Y){
                curY = constants.GROUND_Y;
                jumping = false;
                if (d == 0)
                {
                    setIcon(leftImg);
                }
                else
                {
                    setIcon(rightImg);
                }
            }
        }
        if (d == 0 && m == true)
        {   
            curX = curX - 10;
            
            if(curX + width -75 < 0)
            {
                curX = 5;
            }
            if(jumping == true)
            {
                setIcon(leftJumpImg);
            }
            else
            {
                setIcon(leftImg);
            }
        }
        else if(d == 1 && m == true)
        {
            curX = curX + 10;
            if(curX + width + 10 > frameW )
            {
                curX = frameW - width;
            }
            if(jumping == true)
            {
                setIcon(rightJumpImg);
            }
            else
            {
                setIcon(rightImg);
            }
        }
        setLocation(curX, curY);
        repaint();
        try { Thread.sleep(35); } 
        catch (InterruptedException e) { e.printStackTrace(); }
    }
    public void jump() {
        if (!jumping && curY == constants.GROUND_Y) {
            jumping = true;
            jumpVelocity = JUMP_STRENGTH;
        }
    }
}
class HPBar extends JLabel
{
    private GameEngine parentFrame;

    private Image frontImg;   // filled HP bar
    private Image backImg;    // empty HP bar

    private int maxHP;
    private int currentHP;
    private int width;
    private int height;

    public HPBar(GameEngine pf, int type, int mH)
    {
        this.parentFrame = pf;
        this.maxHP = mH;
        this.currentHP = maxHP;

        switch (type)
        {
            case 1: // Boss bar
                frontImg = new MyImageIcon(constants.HP_BOSS)
                               .resize(constants.bBarWidth, constants.bBarHeight)
                               .getImage();
                backImg  = new MyImageIcon(constants.HP_BOSS_EMPTY)
                               .resize(constants.bBarWidth, constants.bBarHeight)
                               .getImage();
                width  = constants.bBarWidth;
                height = constants.bBarHeight;
                setBounds(125, 5, width, height);
                break;

            case 2: // Player bar
                frontImg = new MyImageIcon(constants.HP_PLAYER)
                               .resize(constants.pBarWidth, constants.pBarHeight)
                               .getImage();
                backImg  = new MyImageIcon(constants.HP_PLAYER_EMPTY)
                               .resize(constants.pBarWidth,  constants.pBarHeight)
                               .getImage();
                width  = constants.pBarWidth;
                height = constants.pBarHeight;
                setBounds(350, 560, width, height);
                break;
        }

        setOpaque(false); // allow transparency
        
    }
    

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // draw background HP (empty)
        g.drawImage(backImg, 0, 0, width, height, null);

        // HP percent
        float percent = (float) currentHP / maxHP;
        int filledWidth = (int) (width * percent);

        // draw front HP (filled) cropped
        g.drawImage(frontImg, 
                    0, 0, filledWidth, height,     // destination
                    0, 0, filledWidth, height,     // source
                    null);
    }

    public void takeDamage(int d)
    {
        currentHP -= d;
        if (currentHP < 0) currentHP = 0;
        repaint();
    }
}
