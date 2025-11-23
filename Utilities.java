package Project3_6713249;

import java.awt.Image;
import javax.swing.ImageIcon;

interface constants {

    static final String PATH = "src/main/java/Project3_6713249/imageP/";
    static final String SOUND_PATH = "src/main/java/Project3_6713249/soundP/";
    static final String PATHP = "src/main/java/Project3_6713249/";
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
    
    //selecticon
    static final String ICON_1 = PATH + "icon1.png";
    static final String ICON_2 = PATH + "icon2.png";
    static final String ICON_3 = PATH + "icon3.png";
    //end
    static final String BG_END = PATH + "bg_end.png";
    static final String BG_TABLE = PATH + "bg_table.png";
    
    
    //Storage Data .csv
    static final String TABLEFILE = PATHP + "scores.txt";
    
    // boss1 battle sprites
    static final String BOSS1_NORMAL    = PATH + "Boss1.png";
    static final String BOSS1_ATTACK    = PATH + "Boss1_Damage1.png";
    static final String BOSS1_HURT      = PATH + "Boss1_shooted.png";
    static final String BOSS1_DEFEATED  = PATH + "Boss1_die.png";

    // boss1 attack parts
    static final String BOSS1_MARK      = PATH + "Boss_direct_and_damage.png";
    static final String BOSS1_LASER     = PATH + "Boss1_Damage2.png";
    
    // boss2 battle sprites
    static final String BOSS2_NORMAL    = PATH + "Boss2.png";
    static final String BOSS2_ATTACK    = PATH + "Boss2_Damage.png";
    static final String BOSS2_HURT      = PATH + "Boss2_shooted.png";
    static final String BOSS2_DEFEATED  = PATH + "Boss2_die.png";

    // boss2 attack parts
    static final String BOSS2_BOMB      = PATH + "Boss2_bomb.gif";
    static final String BOSS2_EXPLOSION = PATH + "Explosion.gif";

    
    static final String HP_BOSS = PATH + "full_HP_BOSS1.png";
    static final String HP_BOSS_EMPTY = PATH + "empthy_HP_BOSS.png";
    static final String HP_PLAYER = PATH + "full_HP_Player.png";
    static final String HP_PLAYER_EMPTY = PATH + "empthy_HP_Player.png";
    
    static final int frameWidth = 1188;
    static final int frameHeight = 668;
    
    static final int bBarWidth = 881;
    static final int bBarHeight = 120;
    
    static final int pBarWidth = 467;
    static final int pBarHeight = 67;
    
    // [Max] Added specific path for sound files to separate them from images.
    
    
    // [Max] Added constants for the selectable music tracks.
    static final String SONG_TITANIUM       = SOUND_PATH + "titanium.wav";
    static final String SONG_CREATIVE       = SOUND_PATH + "creative.wav";
    static final String SONG_EONA           = SOUND_PATH + "eona.wav";
    
    // [Max] Added constant for the Settings dialog background image.
    static final String SETTING_BG          = PATH + "BG_setting.png"; 
    
  
    
    static final String FILE_BG             = PATH + "BG2_PlaySC_floor.png";
    // [Max] Backgrounds
    //static final String SETTING_BG          = PATH + "BG_setting.png"; 
    
    // [Max] Buttons
    //static final String SETTINGBUTTON       = PATH + "setting.png";
    
    
    static final String PlAYER1_LEFT         = PATH + "player1_walk_fillGun_flip.gif";
    static final String PLAYER1_RIGHT        = PATH + "player1_walk_fillGun.gif";
    static final String PLAYER1_JUMP_RIGHT   = PATH + "player1_jump.png";
    static final String PLAYER1_JUMP_LEFT    = PATH + "player1_jump_flip1.png";
    static final String PLAYER1_SHOT_LEFT    = PATH + "player_shooted_flip1.png";
    static final String PLAYER1_SHOT_RIGHT   = PATH + "player_shooted.png";
    static final String PLAYER1_DEAD         = PATH + "player_died.png";
    static final String PlAYER2_LEFT         = PATH + "player2_walk_fillGun_flip.gif";
    static final String PLAYER2_RIGHT        = PATH + "player2_walk_fillGun.gif";
    static final String PLAYER2_JUMP_RIGHT   = PATH + "player2_jump.png";
    static final String PLAYER2_JUMP_LEFT    = PATH + "player2_jumpflip1.png";
    static final String PLAYER2_SHOT_LEFT    = PATH + "player2_shooted_flip1.png";
    static final String PLAYER2_SHOT_RIGHT   = PATH + "player2_shooted.png";
    static final String PLAYER2_DEAD         = PATH + "player2_died.png";
    static final String PLAYER3_LEFT         = PATH + "player3_walk_fillGun_flip.gif";
    static final String PLAYER3_RIGHT        = PATH + "player3_walk_fillGun.gif";
    static final String PLAYER3_JUMP_RIGHT   = PATH + "player3_jump.png";
    static final String PLAYER3_JUMP_LEFT    = PATH + "player3_jumpflip.png";
    static final String PLAYER3_SHOT_LEFT    = PATH + "player3_shooted_flip1.png";
    static final String PLAYER3_SHOT_RIGHT   = PATH + "player3_shooted.png";
    static final String PLAYER3_DEAD         = PATH + "player3_died.png";
    
    static final String PLAYER_PROJ_IMAGE1   = PATH + "player_Damage1.png";
    static final String PLAYER_PROJ_IMAGE2   = PATH + "player_Damage2.png";
    
    static final String PLAYER_GUN           = PATH + "GUN.png";
    static final String PLAYER_GUN_CHARGE    = PATH + "GUN_chart1.png";
    
    static final String PLAYER_GUN_R         = PATH + "GUN_flip.png";
    static final String PLAYER_GUN_CHARGE_R  = PATH + "GUN_chart1_flip.png";
    
    static final int GUN_WIDTH               = 57;
    static final int GUN_HEIGHT              = 15;
    
    static final int SMALL_PLAYER_PROJECTILE_DIMENSION = 33;
    static final int BIG_PLAYER_PROJECTILE_DIMENSION = 41;
    // [Max] Sounds Files
    static final int GROUND_Y     = 500;
    static final int PLAYER_WIDTH    = 79;
    static final int PLAYER_HEIGHT   = 79; 
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
