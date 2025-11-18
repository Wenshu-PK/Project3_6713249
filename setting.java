package Project3_6713249;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;

// ==========================================
//  [Max] Kept MyConstants interface here for future use/reference, 
//  even though main logic might use 'constants' from Utilities.java.
// ==========================================
interface MyConstants
{
    static final String PATH       = "src/main/java/Project3_6713249/imageP/";
    // [Max] Resource Sound Paths
    static final String SOUND_PATH = "src/main/java/Project3_6713249/soundP/";
    
    static final String FILE_BG             = PATH + "BG1_PlaySC.png";
    // [Max] Backgrounds
    static final String SETTING_BG          = PATH + "BG_setting.png"; 
    
    // [Max] Buttons
    static final String SETTINGBUTTON       = PATH + "setting.png";
    static final String SETTINGBUTTON_HOVER = PATH + "C1setting.png";
    static final String EXITBUTTON          = PATH + "Exit.png";
    static final String OKBUTTON            = PATH + "OK.png";        
    static final String OKBUTTON_HOVER      = PATH + "C1OK.png";      
    
    static final String PlAYER_LEFT         = PATH + "player1_useGUN.png";
    static final String PLAYER_RIGHT        = PATH + "girl_right.png";
    static final String FILE_FRUIT          = PATH + "starfruits2.png";
    static final String FILE_STAR           = PATH + "star.gif";    
    
    static final String FILE_THEME          = SOUND_PATH + "theme.wav";
    static final String FILE_COLLECT_FX     = SOUND_PATH + "twotaps.wav";
    static final String FILE_BOUNCE_FX      = SOUND_PATH + "beep.wav";
    // [Max] Sounds Files
    static final String SONG_TITANIUM       = SOUND_PATH + "titanium.wav";
    static final String SONG_CREATIVE       = SOUND_PATH + "creative.wav";
    static final String SONG_EONA           = SOUND_PATH + "eona.wav";
    
    static final int FRAME_WIDTH  = 1366;
    static final int FRAME_HEIGHT = 768;
    static final int GROUND_Y     = 568;
    static final int PLAYER_WIDTH    = 150;
    static final int PLAYER_HEIGHT   = 100;
    static final int ITEM_WIDTH    = 40;
    static final int ITEM_HEIGHT   = 40;   
}


/*
class MyImageIcon extends ImageIcon
{
    public MyImageIcon(String fname)  { super(fname); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int width, int height)
    {
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
}
*/
// ==========================================
//  [Max] 
// ==========================================
class MySoundEffect
{
    private Clip           clip;
    private FloatControl   gainControl;          

    public MySoundEffect(String filename)
    {
        try
        {
            File file = new File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);            
            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch (Exception e) { 
            // [Max] Added error printing to debug if sound files are missing.
            System.out.println("Error loading sound: " + filename);
            e.printStackTrace(); 
        }
    }

    public void playOnce()             { if(clip!=null) { clip.setMicrosecondPosition(0); clip.start(); } }
    public void playLoop()             { if(clip!=null) { clip.loop(Clip.LOOP_CONTINUOUSLY); } }
    public void stop()                 { if(clip!=null) { clip.stop(); } }
    
    // [Max] Added/Modified setVolume to accept an integer (0-100) from the Slider.
    public void setVolume(int sliderValue)
    {
        if(gainControl == null) return;

        float gain = sliderValue / 100.0f;
        
        if (gain < 0.0f)  gain = 0.0f;
        if (gain > 1.0f)  gain = 1.0f;
        
        // [Max] Implemented logarithmic calculation to convert linear slider value to Decibels (dB).
        float dB;
        if (gain < 0.01f) {
            dB = -80.0f; // [Max] Mute if value is very low.
        } else {
            dB = (float)(Math.log(gain) / Math.log(10.0) * 20.0);
        }
        
        gainControl.setValue(dB);
    }
}

// ==========================================
// [Max]
// ==========================================
class settingDialog extends SelectionDialog {

    private menuButtonLabel okButton;
    private JSlider volumeSlider;
    private JComboBox<String> musicComboBox;

    // [Max] Defined custom size for the popup dialog (smaller than full screen).
    private int dialogWidth = 600;
    private int dialogHeight = 450;

    // [Max] Added arrays to map display names to actual file paths for the ComboBox.
    private String[] musicNames = {"Titanium", "Creative", "Eona", "Silent"};
    
    // [Max] Using 'constants' interface (likely from Utilities.java) for consistency.
    private String[] musicFiles = {
        constants.SONG_TITANIUM, 
        constants.SONG_CREATIVE, 
        constants.SONG_EONA, 
        null
    };

    public settingDialog(String bg_path, String name, mainFrame owner) {
        super(bg_path, name, owner); 

        // --- 1. Window Setup ---
        setTitle(name);
        setSize(dialogWidth, dialogHeight); 
        setLocationRelativeTo(owner);       
        setModal(true); // [Max] Set as Modal so the user cannot interact with the main menu while settings are open.

        // --- 2. Background Setup ---
        // [Max] Resized the background image to fit the smaller dialog window.
        // Note: Using local MyImageIcon class defined above.
        MyImageIcon bgIcon = new MyImageIcon(bg_path).resize(dialogWidth, dialogHeight);
        contentpane.setIcon(bgIcon);
        
        int dCenterX = dialogWidth / 2;

        // --- 3. JSlider (Volume) ---
        // [Max] Initialize slider with the current volume from mainFrame.
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, owner.getCurrentVolume());
        
        // [Max] Adjusted coordinates (Y=170) to align perfectly with the background image text.
        volumeSlider.setBounds(dCenterX - 100, 170, 200, 50); 
        volumeSlider.setOpaque(false); 
        
        // [Max] Added listener to update volume in real-time.
        volumeSlider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int volume = source.getValue();
                owner.setVolume(volume); // [Max] Calls method in mainFrame to change sound.
            }
        });
        contentpane.add(volumeSlider);

        // --- 4. JComboBox (Music) ---
        musicComboBox = new JComboBox<>(musicNames);
        
        // [Max] Adjusted coordinates (Y=275) to avoid overlapping with the "Music" label in the background.
        musicComboBox.setBounds(dCenterX - 100, 275, 200, 30);
        
        // [Max] Added listener to switch music tracks immediately upon selection.
        musicComboBox.addActionListener(e -> {
            int selectedIndex = musicComboBox.getSelectedIndex();
            String selectedFile = musicFiles[selectedIndex];
            owner.playTheme(selectedFile); 
        });

        contentpane.add(musicComboBox);

        // --- 5. OK Button ---
        // [Max] Using 'constants' for OK button image paths.
        okButton = new menuButtonLabel(constants.OKBUTTON, constants.OKBUTTON_HOVER, 150, 60, owner);
        
        // [Max] Adjusted button position (Y=340) to have proper spacing.
        okButton.setInitialLocation(dCenterX - 75, 340); 
        
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); // [Max] Closes only this popup dialog, returning to the main menu.
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