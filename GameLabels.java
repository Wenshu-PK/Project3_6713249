/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project3_6713249;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author iamja
 */
class PlayerLabel extends JLabel implements Runnable
{
    
    private GameEngine      parentFrame;
    private GunLabel        gunLabel;
    private HPBar           HPBar;
    private MyImageIcon     leftImg, rightImg, leftJumpImg, rightJumpImg, shotLeft, shotRight, deadImg;
    
    private int width   = constants.PLAYER_WIDTH;
    private int height  = constants.PLAYER_HEIGHT;
    private int curY    = constants.GROUND_Y;
    private int curX    = 100;
    private int direction = 1;
    private int maxHP = 100;
    private int hp;
    private int speed = 8;
    
    private boolean jumping    = false;
    private boolean move       = false;
    private int jumpVelocity;
    private final int JUMP_STRENGTH = -18;
    public void setJumping(boolean j)   {jumping = j;}
    public void setMove(boolean m)      {move = m;}
    public void setDirection(int d)     {direction = d;}
    public void setGunLabel(GunLabel gl){gunLabel = gl;}
    public void setHPBar(HPBar hp)      {HPBar = hp;}
    public void setSpeed(int s)         {speed = s;}
    
    public int getMaxHP()               {return maxHP;}
    public int getHP()                  {return hp;}
    public int getCurX()                {return curX;}
    public int getCurY()                {return curY;}
    public int getDirection()           {return direction;}
    public void takeDamage(int d)            
    {
        hp -= d;
        if(hp < 0)     { hp = 0; }
        if(d == 0)
        {
            setIcon(shotLeft);
        }
        else
        {
            setIcon(shotRight);
        }
        HPBar.updateHP(hp);
        move= false;
        repaint();
    }
    
    
    
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
            case 3: {leftImg = new MyImageIcon(constants.PLAYER3_LEFT);
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
            gunLabel.updateLocation(direction);
            if(hp <= 0)
            {
                setIcon(deadImg);
                repaint();
                parentFrame.GameEnd(false);
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
            curX = curX - speed;
            
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
            curX = curX + speed;
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
    public void jump(boolean c) {
        if (!jumping && curY == constants.GROUND_Y && !c) {
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
    private int lType;
    int leftOffset, rightOffset, topOffset, buttomOffset;
    

    public HPBar(GameEngine pf, int type, int mH)
    {
        this.parentFrame = pf;
        this.maxHP = mH;
        this.currentHP = maxHP;
        this.lType = type;
        switch (type)
        {
            case 1: // Boss bar
                frontImg = new MyImageIcon(constants.HP_BOSS).getImage();
                backImg  = new MyImageIcon(constants.HP_BOSS_EMPTY).getImage();
                width      = constants.bBarWidth;
                height     = constants.bBarHeight;                       
                setBounds(125, 5, width, height);
                break;

            case 2: // Player bar
                frontImg = new MyImageIcon(constants.HP_PLAYER).getImage();
                backImg  = new MyImageIcon(constants.HP_PLAYER_EMPTY).getImage();
                width      = constants.pBarWidth;
                height     = constants.pBarHeight;
                setBounds(350, 560, width, height);
                break;
        }
        
        setOpaque(false); // allow transparency
        
    }
    

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if(lType == 1)
        {
            leftOffset = 130;
            rightOffset = 24;
            topOffset = 40;
            buttomOffset = 29;
        }
        else if(lType == 2)
        {
            leftOffset = 58;
            rightOffset = 10;
            topOffset = 9;
            buttomOffset = 9;
        }
       
        // draw background HP (empty)
        g.drawImage(backImg, 0, 0, width, height, null);

        // HP percent
        float percent = (float) currentHP / maxHP;
        int maxFillWidth = width - leftOffset - rightOffset;
        int maxFillHeight = height - topOffset - buttomOffset;
        int filledWidth = (int)(maxFillWidth * percent);

        // draw the empty background frame (full)
        g.drawImage(backImg, 0, 0, width, height, null);

        // draw the fill image inside the frame
        g.drawImage(frontImg, leftOffset, topOffset, leftOffset + filledWidth, topOffset + maxFillHeight,   // destination
                    0, 0, filledWidth, maxFillHeight, null); //source
    }

    public void updateHP(int h)
    {
        this.currentHP = Math.max(h, 0);
        repaint();
    }
}
class playerProjLabel extends JLabel implements Runnable
{
    private GameEngine          parentFrame;
    private boss                bossLabel;
    
    private MyImageIcon         proImg;
    
    private int dimension;
    private int damage;
    private int curX, curY;
    private int finalX, finalY;
    private int speed;
    private double cos, sin;
    private double totalX, totalY;
    
    private boolean yUp, xRight;
    
    public playerProjLabel(GameEngine pf, boss bl, PlayerLabel pl, int bulletType, int fX, int fY)
    {
        parentFrame = pf;
        bossLabel = bl;
        finalX = fX;
        finalY = fY;
        int px = pl.getCurX();
        int py = pl.getCurY();
        int pw = pl.getWidth();
        int ph = pl.getHeight();
        if (pl.getDirection() == 0)  // LEFT
        {
            curX = px - 40;            // small offset from left edge
        }
        else                          // RIGHT
        {
            curX = px + pw - 20;       // small offset from right edge
        }
        curY = py + (ph / 2) - 20;
        switch (bulletType)
        {
            case 1: {proImg = new MyImageIcon(constants.PLAYER_PROJ_IMAGE1); dimension = constants.SMALL_PLAYER_PROJECTILE_DIMENSION; 
                     damage = 1; speed = 4; break;}
            case 2: {proImg = new MyImageIcon(constants.PLAYER_PROJ_IMAGE2); dimension = constants.BIG_PLAYER_PROJECTILE_DIMENSION; 
                     damage = 60; speed = 2; break;}
        }
        if(curY > finalY)
        {
            yUp = true;
            totalY = (float) curY - finalY;
        }
        else
        {
            yUp = false;
            totalY = (float) finalY - curY;
        }
        if(curX > finalX)
        {
            xRight = false;
            totalX = (float) curX - finalX;
        }
        else
        {
            xRight = true;
            totalX = (float) finalX - curX;
        }
        sin = totalY / Math.sqrt(Math.pow(totalX+totalY, 2));
        cos = totalX / Math.sqrt(Math.pow(totalX+totalY, 2));
        setIcon(proImg);
        setBounds(curX, curY, dimension, dimension);
    }

    @Override
    public void run() {
        while(true && parentFrame.getRunning())
        {
            updateLocation();
            if(this.getBounds().intersects(bossLabel.getBounds()))
            {
                bossLabel.takeDamage(damage);
                parentFrame.removeItem(this);
                break;
            }
            if(curY < 0 || curY > parentFrame.getHeight() || curX < 0 || curX > parentFrame.getWidth())
            {
                parentFrame.removeItem(this);
                break;
            }
        }
    }
    public void updateLocation()
    {
        if(yUp == true)
        {
            curY -= speed * sin;
        }
        else
        {
            curY += speed * sin;
        }
        if(xRight != true)
        {
            curX -= speed * cos; 
        }
        else
        {
            curX += speed * cos;
        }
        setLocation(curX, curY);
        repaint();
        try { Thread.sleep(10); } 
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
class GunLabel extends JLabel 
{
    private PlayerLabel     playerLabel;
    private boolean         charging;
    private boolean         charged;
    
    private int width       = constants.GUN_WIDTH;
    private int height      = constants.GUN_HEIGHT;
    private int curX        = 127;
    private int curY        = constants.GROUND_Y + 20;
    
    private MyImageIcon gunImg, gunImg_Charge, gunImgF, gunImg_ChargeF;
    
    public void gCharging(boolean c)    {charging = c;}
    public void gCharged(boolean c)     {charged = c;}
    
    public GunLabel(PlayerLabel pl)
    {
        playerLabel = pl;
        gunImg = new MyImageIcon(constants.PLAYER_GUN).resize(width, height);
        gunImg_Charge = new MyImageIcon(constants.PLAYER_GUN_CHARGE).resize(width, height);       
        gunImgF = new MyImageIcon(constants.PLAYER_GUN_R).resize(width, height);
        gunImg_ChargeF = new MyImageIcon(constants.PLAYER_GUN_CHARGE_R).resize(width, height);
        setIcon(gunImg);
        setBounds(curX, curY, width, height);
    }
    public void updateLocation(int d)
    {
        if(d == 0)
        {
            curX = playerLabel.getCurX() - 27;
            if(charging == true)
            {
                setIcon(gunImg_ChargeF);
            }
            else
            {
                setIcon(gunImgF);
            }
        }
        else if(d == 1)
        {
            curX = playerLabel.getCurX() + 27;           
            if(charging == true)
            {
                setIcon(gunImg_Charge); 
            }
            else
            {
                setIcon(gunImg);
            }
        }
        curY = playerLabel.getCurY() + 20;
        setLocation(curX, curY);
        repaint();
    }

}
