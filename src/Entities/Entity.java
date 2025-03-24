package Entities;



import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitBox;
    protected int aniTick, aniIndex;
    protected int state;
    protected float airSpeed;
    protected boolean inAir = false;
    protected int maxHealth;
    protected int currentHealth;
    protected Rectangle2D.Float attackBox;
    protected float walkSpeed;
    protected int z;

    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int)(attackBox.x - xLvlOffset), (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    protected void drawHitBox(Graphics g, int xLvlOffset){
        g.setColor(Color.PINK);
        g.drawRect((int)hitBox.x - xLvlOffset, (int)hitBox.y, (int)hitBox.width, (int)hitBox.height);
    }

    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    public Rectangle2D.Float getHitBox(){
        return hitBox;
    }

    public int getstate(){
        return state;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    protected void newState(int state) {
        this.state = state;
        aniTick = 0;
        aniIndex = 0;
    }
}
