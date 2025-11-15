package Project3_6713249;

import java.awt.Image;
import javax.swing.ImageIcon;

interface constants {

    static final String PATH = "src/main/java/Project3_6713249/imageP/";
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

    static final String BOSS_BG = PATH + "BG_Select_Boss_1.png";

    static final String BOSS1 = PATH + "S1Boss1.png";
    static final String BOSS1_HOVER = PATH + "C1S1Boss1.png";
    static final String BOSS2 = PATH + "Boss1.png";
    static final String BOSS2_HOVER = PATH + "C1S1Boss1.png";
    static final String BOSS3 = PATH + "Boss1.png";
    static final String BOSS3_HOVER = PATH + "C1S1Boss1.png";

    static final String RETURNBUTTON = PATH + "Return.png";
    static final String RETURNBUTTON_HOVER = PATH + "C1Return.png";

    static final String NEXTBUTTON = PATH + "Next.png";
    static final String NEXTBUTTON_HOVER = PATH + "C1Next.png";

    static final String FIGHTBUTTON = PATH + "fight.png";
    static final String FIGHTBUTTON_HOVER = PATH + "C1fight.png";

    static final int frameWidth = 1188;
    static final int frameHeight = 668;

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
