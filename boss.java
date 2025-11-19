package Project3_6713249;

import javax.swing.*;
import java.awt.event.*;

public abstract class boss extends JLabel {

    protected GameEngine game;
    protected int difficulty;     // 0 easy, 1 medium, 2 hard
    protected int maxHP;
    protected int hp;

    protected int moveSpeed;
    protected int attackInterval; // milliseconds
    protected int contactDamage;

    protected MyImageIcon imgNormal;
    protected MyImageIcon imgAttack;
    protected MyImageIcon imgHurt;
    protected MyImageIcon imgDead;

    protected Timer moveTimer;
    protected Timer attackTimer;

    public boss(GameEngine game, int difficulty) {
        this.game = game;
        this.difficulty = difficulty;

        setOpaque(false);
        setSize(200, 150); // default size (you can change)
    }

    // called by GameEngine after adding boss to drawpane
    public void start() {
        if (moveTimer   != null) moveTimer.start();
        if (attackTimer != null) attackTimer.start();
    }

    public void stop() {
        if (moveTimer   != null) moveTimer.stop();
        if (attackTimer != null) attackTimer.stop();
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp <= 0) {
            hp = 0;
            setIcon(imgDead);
            stop();
            //game.gameOver(true); // player wins
        } else {
            setIcon(imgHurt);
            // quickly switch back to normal
            new Timer(200, e -> {
                setIcon(imgNormal);
                ((Timer)e.getSource()).stop();
            }).start();
        }
    }

    // each boss will set its own stats
    protected abstract void initStats();
}
