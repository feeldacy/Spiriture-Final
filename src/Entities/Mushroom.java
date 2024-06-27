package Entities;

import Main.Game;
import gameState.Playing;

import java.awt.geom.Rectangle2D;
import static Utilz.Constants.EnemyConstants.*;
import static Utilz.Constants.Directions.*;
import static Utilz.HelpMethods.IsFloor;

public class Mushroom extends Enemy{

    private int attackBoxOffsetX;

    public Mushroom(float x, float y){
        super(x, y, MUSHROOM_WIDTH, MUSHROOM_HEIGHT, MUSHROOM);
        initHitBox(30, 31);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(80 * Game.SCALE), (int)(31 * Game.SCALE));
        attackBoxOffsetX = (int)(Game.SCALE * 25);
    }

    public void update(int[][] lvlData, Playing playing){
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitBox.x - attackBoxOffsetX;
        attackBox.y = hitBox.y;
    }

    private void updateBehavior(int[][] lvlData, Playing playing) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir) {
            inAirChecks(lvlData, playing);
        } else {
            switch (state) {
                case IDLE:
                    if (IsFloor(hitBox, lvlData))
                        newState(RUNNING);
                    else
                        inAir = true;
                    break;
                case RUNNING:
                    if (canSeePlayer(lvlData, playing.getPlayer())) {
                        turnTowardsPlayer(playing.getPlayer());
                        if (isPlayerCloseForAttack(playing.getPlayer()))
                            newState(ATTACK);
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 3 && !attackChecked)
                        checkPlayerHit(attackBox, playing.getPlayer());
                    break;
                case HIT:
                    break;
            }
        }
    }

    public int flipX() {
        if (walkDir == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkDir == RIGHT)
            return -1;
        else
            return 1;
    }


}
