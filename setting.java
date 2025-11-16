
package Project3_6713249;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.sound.sampled.*;     // for sounds


// Interface for keeping constant values
interface MyConstants
{
    //----- Resource files
    static final String PATH             = "src/main/java/Project3_6713249/imageP/";
    static final String FILE_BG          = PATH + "BG1_PlaySC.png";
    static final String PlAYER_LEFT      = PATH + "player1_useGUN.png";
    static final String PLAYER_RIGHT     = PATH + "girl_right.png";
    static final String FILE_FRUIT       = PATH + "starfruits2.png";
    static final String FILE_STAR        = PATH + "star.gif";    
    
    static final String FILE_THEME       = PATH + "theme.wav";
    static final String FILE_COLLECT_FX  = PATH + "twotaps.wav";
    static final String FILE_BOUNCE_FX   = PATH + "beep.wav";
    
    //----- Sizes and locations
    static final int FRAME_WIDTH  = 1366;
    static final int FRAME_HEIGHT = 768;
    static final int GROUND_Y     = 568;
    
    static final int PLAYER_WIDTH   = 150;
    static final int PLAYER_HEIGHT  = 100;
    static final int ITEM_WIDTH   = 40;
    static final int ITEM_HEIGHT  = 40;    
}


// Auxiliary class to resize image
class MyImageIcon extends ImageIcon
{
    public MyImageIcon(String fname)  { super(fname); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int width, int height)
    {
	Image oldimg = this.getImage();
	Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
        return new MyImageIcon(newimg);
    }
}


// Auxiliary class to play sound effect (support .wav or .mid file)
class MySoundEffect
{
    private Clip         clip;
    private FloatControl gainControl;         

    public MySoundEffect(String filename)
    {
	try
	{
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);            
            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	catch (Exception e) { e.printStackTrace(); }
    }
    public void playOnce()             { clip.setMicrosecondPosition(0); clip.start(); }
    public void playLoop()             { clip.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop()                 { clip.stop(); }
    public void setVolume(float gain)
    {
        if (gain < 0.0f)  gain = 0.0f;
        if (gain > 1.0f)  gain = 1.0f;
        float dB = (float)(Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
}
