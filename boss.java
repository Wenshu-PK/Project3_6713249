package Project3_6713249;

import javax.swing.*;
import java.awt.event.*;

public abstract class boss extends JLabel implements Runnable {

    // reference to main game engine
    protected GameEngine game;
    private HPBar      Hbar;

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

    // ---------- constructor ----------
    public boss(GameEngine game, int difficulty) {
        this.game = game;
        this.difficulty = difficulty;

        setOpaque(false);        // we will use transparent PNGs
        setSize(200, 150);       // default size; boss1/2/3 can change with setBounds()
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

    // create a smaller hitbox inside a JLabel's bounds
    // shrinkX, shrinkY are 0.0 – 1.0 (fraction to cut from EACH side)
    protected Rectangle makeHitBox(JLabel src, double shrinkX, double shrinkY) {
        Rectangle r = src.getBounds();
        int dx = (int)(r.width  * shrinkX);
        int dy = (int)(r.height * shrinkY);
        return new Rectangle(
                r.x + dx,
                r.y + dy,
                r.width  - 2 * dx,
                r.height - 2 * dy
        );
    }

    // ---------- HP / damage ----------
        public void takeDamage(int dmg) {
        hp -= dmg;
        if(hp < 0)     {hp = 0;}
        Hbar.updateHP(hp);
        if (hp < 0) hp = 0;

        // tell GameEngine so it can update the boss HP bar
        if (game != null) {
            //game.damageBoss(dmg);
        }

        if (hp == 0) {
            if (imgDead != null) {
                setIcon(imgDead);
            }
            game.GameEnd(true);
            // later: game.gameOver(true); // player wins
        } else {
            if (imgHurt != null) {
                // simple: show hurt image; you can switch back to normal in your loop later if you want
                setIcon(imgHurt);
            }
        }
    }

    public int getHP()    { return hp; }
    public int getMaxHP() { return maxHP; }
    public void setHPBar(HPBar hp)  {Hbar = hp;}

    // each concrete boss (boss1, boss2, boss3) must implement this
    // and set maxHP, moveSpeed, attackInterval, contactDamage depending on difficulty
    protected abstract void initStats();

    // each boss also MUST implement run() from Runnable
    @Override
    public abstract void run();
}
