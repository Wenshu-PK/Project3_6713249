package Project3_6713249;

import javax.swing.*;
import java.awt.event.*;

public abstract class boss extends JLabel {

    // reference to main game engine
    protected GameEngine game;

    // difficulty: 0 = easy, 1 = medium, 2 = hard
    protected int difficulty;

    // stats
    protected int maxHP;
    protected int hp;
    protected int moveSpeed;       // pixels per movement step
    protected int attackInterval;  // milliseconds between attacks
    protected int contactDamage;   // damage when boss attack hits player

    // images / sprites
    protected MyImageIcon imgNormal;
    protected MyImageIcon imgAttack;
    protected MyImageIcon imgHurt;
    protected MyImageIcon imgDead;

    // timers (created in subclasses)
    protected Timer moveTimer;
    protected Timer attackTimer;

    // ---------- constructor ----------
    public boss(GameEngine game, int difficulty) {
        this.game = game;
        this.difficulty = difficulty;

        setOpaque(false);        // we will use transparent PNGs
        setSize(200, 150);       // default size; boss1/2/3 can change with setBounds()
    }

    // ---------- control timers ----------
    // called by GameEngine after boss is created
    public void start() {
        if (moveTimer   != null) moveTimer.start();
        if (attackTimer != null) attackTimer.start();
    }

    public void stop() {
        if (moveTimer   != null) moveTimer.stop();
        if (attackTimer != null) attackTimer.stop();
    }

    // ---------- sprite helper ----------
    // subclasses call this once after loading resized images
    protected void setSprites(MyImageIcon normal,
                              MyImageIcon attack,
                              MyImageIcon hurt,
                              MyImageIcon dead) {
        this.imgNormal = normal;
        this.imgAttack = attack;
        this.imgHurt   = hurt;
        this.imgDead   = dead;

        if (imgNormal != null) {
            setIcon(imgNormal);
        }
    }

    // ---------- HP / damage ----------
    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;

        // tell GameEngine so it can update the boss HP bar
        if (game != null) {
            game.damageBoss(dmg);    // make sure you implement this in GameEngine
        }

        if (hp == 0) {
            if (imgDead != null) {
                setIcon(imgDead);
            }
            stop();
            // later: game.gameOver(true); // player wins
        } else {
            if (imgHurt != null && imgNormal != null) {
                setIcon(imgHurt);
                new Timer(150, e -> {
                    setIcon(imgNormal);
                    ((Timer)e.getSource()).stop();
                }).start();
            }
        }
    }

    public int getHP()    { return hp; }
    public int getMaxHP() { return maxHP; }

    // each concrete boss (boss1, boss2, boss3) must implement this
    // and set maxHP, moveSpeed, attackInterval, contactDamage depending on difficulty
    protected abstract void initStats();
}
