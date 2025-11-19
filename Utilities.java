package Project3_6713249;

import java.awt.Image;
import javax.swing.ImageIcon;

interface constants {

    static final String PATH = "src/main/java/Project3_6713249/imageP/";
    static final String SOUND_PATH = "src/main/java/Project3_6713249/soundP/";
    //static final String MENU_BG = PATH + "BACKGROUND.png";
    //static final String MENU_BG_MP4 = PATH + "BACKGROUND_MP4.mp4";
    static final String MENU_BG_GIF = PATH + "Background1.gif";

    static final String STARTBUTTON = PATH + "start.png";
    static final String STARTBUTTON_HOVER = PATH + "C1start.png";

    static final String SETTINGBUTTON = PATH + "setting.png";
    static final String SETTINGBUTTON_HOVER = PATH + "C1setting.png";

    static final String EXITBUTTON = PATH + "Exit.png";
    static final String EXITBUTTON_HOVER = PATH + "C1Exit.png";

    static final String PLAYER_BG = PATH + "BG_Select_player.png";
    static final String PLAYER_1 = PATH + "S1player1.png";
    static final String PLAYER_1_HOVER = PATH + "C1S1player1.png";
    static final String PLAYER_2 = PATH + "S1player2.png";
    static final String PLAYER_2_HOVER = PATH + "C1S1player2.png";
    static final String PLAYER_3 = PATH + "S1player3.png";
    static final String PLAYER_3_HOVER = PATH + "C1S1player3.png";

    static final String BOSS_BG = PATH + "BG_Select_Boss.png";

    static final String BOSS1 = PATH + "S1Boss1.png";
    static final String BOSS1_HOVER = PATH + "C1S1Boss.png";
    static final String BOSS2 = PATH + "S1Boss2.png";
    static final String BOSS2_HOVER = PATH + "C1S1Boss2.png";
    static final String BOSS3 = PATH + "S1Boss3.png";
    static final String BOSS3_HOVER = PATH + "C1S1Boss3.png";

    static final String RETURNBUTTON = PATH + "Return.png";
    static final String RETURNBUTTON_HOVER = PATH + "C1Return.png";

    static final String NEXTBUTTON = PATH + "Next.png";
    static final String NEXTBUTTON_HOVER = PATH + "C1Next.png";

    static final String FIGHTBUTTON = PATH + "fight.png";
    static final String FIGHTBUTTON_HOVER = PATH + "C1fight.png";
    
    static final String CREDITBUTTON = PATH + "credit.png";
    static final String CREDITBUTTON_HOVER = PATH + "C1credit.png";
    
    static final String HOWTOBUTTON = PATH + "How to play.png";
    static final String HOWTOBUTTON_HOVER = PATH + "C1How to play.png";
    static final String HOWTOPLAT_BG = PATH + "BG_how_to_play.gif";
    
    static final String OKBUTTON = PATH + "OK.png";
    static final String OKBUTTON_HOVER = PATH + "C1OK.png";
    
    static final String CREDIT = PATH + "BG_Credits.jpg";
    
    static final int frameWidth = 1188;
    static final int frameHeight = 668;
    
    // [Max] Added specific path for sound files to separate them from images.
    
    
    // [Max] Added constants for the selectable music tracks.
    static final String SONG_TITANIUM       = SOUND_PATH + "titanium.wav";
    static final String SONG_CREATIVE       = SOUND_PATH + "creative.wav";
    static final String SONG_EONA           = SOUND_PATH + "eona.wav";
    
    // [Max] Added constant for the Settings dialog background image.
    static final String SETTING_BG          = PATH + "BG_setting.png"; 
    
  
    
    static final String FILE_BG             = PATH + "BG1_PlaySC.png";
    // [Max] Backgrounds
    //static final String SETTING_BG          = PATH + "BG_setting.png"; 
    
    // [Max] Buttons
    //static final String SETTINGBUTTON       = PATH + "setting.png";
    
    
    static final String PlAYER_LEFT         = PATH + "player1_useGUN.png";
    static final String PLAYER_RIGHT        = PATH + "girl_right.png";
    static final String FILE_FRUIT          = PATH + "starfruits2.png";
    static final String FILE_STAR           = PATH + "star.gif";    
    
    static final String FILE_THEME          = SOUND_PATH + "theme.wav";
    static final String FILE_COLLECT_FX     = SOUND_PATH + "twotaps.wav";
    static final String FILE_BOUNCE_FX      = SOUND_PATH + "beep.wav";
    // [Max] Sounds Files

    static final int FRAME_WIDTH  = 1366;
    static final int FRAME_HEIGHT = 768;
    static final int GROUND_Y     = 568;
    static final int PLAYER_WIDTH    = 150;
    static final int PLAYER_HEIGHT   = 100;
    static final int ITEM_WIDTH    = 40;
    static final int ITEM_HEIGHT   = 40;  
}

class MyImageIcon extends ImageIcon {

    public MyImageIcon(String fname) {
        super(fname);
    }

    public MyImageIcon(Image image) {
        super(image);
    }

    public MyImageIcon resize(int width, int height) {
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
}
