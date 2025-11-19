package Project3_xxx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class boss extends JLabel {

    protected GameEngine game;
    protected int difficulty;

    protected int maxHP;
    protected int hp;
    protected int moveSpeed;
    protected int attackInterval;
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
    }

    public void start() {
        if (moveTimer != null) moveTimer.start();
        if (attackTimer != null) attackTimer.start();
    }

    public void stop() {
        if (moveTimer != null) moveTimer.stop();
        if (attackTimer != null) attackTimer.stop();
    }

    protected void setSprites(MyImageIcon normal,
                              MyImageIcon attack,
                              MyImageIcon hurt,
                              MyImageIcon dead) {

        this.imgNormal = normal;
        this.imgAttack = attack;
        this.imgHurt   = hurt;
        this.imgDead   = dead;

        if (imgNormal != null) setIcon(imgNormal);
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;

        if (hp == 0) {
            if (imgDead != null) setIcon(imgDead);
            stop();
        } else {
            if (imgHurt != null) {
                setIcon(imgHurt);
                new Timer(150, e -> {
                    setIcon(imgNormal);
                    ((Timer)e.getSource()).stop();
                }).start();
            }
        }
    }

    protected boolean hitPlayer(Rectangle box) {
        PlayerLabel p = game.getPlayerLabel();
        if (p == null) return false;
        return box.intersects(p.getBounds());
    }

    protected abstract void initStats();

    public int getHP() { return hp; }
    public int getMaxHP() { return maxHP; }
}
