package Entities;

import Levels.Level;
import Utilz.LoadSave;
import gameState.Playing;
import static Utilz.Constants.EnemyConstants.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] slimeArr, mushroomArr, bushArr;
    private Level currentLevel;
    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImgs();
    }

    private void loadEnemyImgs() {
        slimeArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.SLIME_SPRITE), 12, 5, SLIME_WIDTH_DEFAULT, SLIME_HEIGHT_DEFAULT);
        mushroomArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.MUSHROOM_SPRITE), 15, 5, MUSHROOM_WIDTH_DEFAULT, MUSHROOM_HEIGHT_DEFAULT);
        bushArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.BUSH_SPRITE), 18, 5, BUSH_WIDTH_DEFAULT, BUSH_HEIGHT_DEFAULT);
    }

    public void loadEnemies(Level level) {
        this.currentLevel = level;
    }

    public void update(int[][] lvlData){
        boolean isAnyActive = false;
        for (Slime s : currentLevel.getSlimes())
            if (s.isActive()) {
                s.update(lvlData, playing);
                isAnyActive = true;
            }
        for (Mushroom m : currentLevel.getMushrooms())
            if (m.isActive()) {
                m.update(lvlData, playing);
                isAnyActive = true;
            }
        for (Bush b : currentLevel.getBushes())
            if (b.isActive()) {
                b.update(lvlData, playing);
                isAnyActive = true;
            }

        if (!isAnyActive)
            playing.setLevelCompleted(true);
    }

    public void draw(Graphics g, int xLvlOffset){
        drawSlimes(g, xLvlOffset);
        drawMushrooms(g, xLvlOffset);
        drawBushes(g, xLvlOffset);
    }

    private void drawSlimes(Graphics g, int xLvlOffset) {
        for (Slime s : currentLevel.getSlimes())
            if (s.isActive()) {
                g.drawImage(slimeArr[s.getstate()][s.getAniIndex()], (int) s.getHitBox().x - xLvlOffset - SLIME_DRAW_OFFSET_X + s.flipX(),
                        (int) s.getHitBox().y - SLIME_DRAW_OFFSET_Y, SLIME_WIDTH * s.flipW(), SLIME_HEIGHT, null);
//            c.drawHitBox(g, xLvlOffset);
//                c.drawAttackBox(g, xLvlOffset);
            }
    }

    private void drawMushrooms(Graphics g, int xLvlOffset) {
        for (Mushroom m : currentLevel.getMushrooms())
            if (m.isActive()) {
                g.drawImage(mushroomArr[m.getstate()][m.getAniIndex()], (int) m.getHitBox().x - xLvlOffset - MUSHROOM_DRAW_OFFSET_X + m.flipX(),
                        (int) m.getHitBox().y - MUSHROOM_DRAW_OFFSET_Y, MUSHROOM_WIDTH * m.flipW(), MUSHROOM_HEIGHT, null);
//            c.drawHitBox(g, xLvlOffset);
//                c.drawAttackBox(g, xLvlOffset);
            }
    }

    private void drawBushes(Graphics g, int xLvlOffset) {
        for (Bush b : currentLevel.getBushes())
            if (b.isActive()) {
                g.drawImage(bushArr[b.getstate()][b.getAniIndex()], (int) b.getHitBox().x - xLvlOffset - BUSH_DRAW_OFFSET_X + b.flipX(),
                        (int) b.getHitBox().y - BUSH_DRAW_OFFSET_Y, BUSH_WIDTH * b.flipW(), BUSH_HEIGHT, null);
//            c.drawHitBox(g, xLvlOffset);
//                c.drawAttackBox(g, xLvlOffset);
            }
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Slime s : currentLevel.getSlimes())
            if (s.isActive())
                if (s.getstate() != DEAD && s.getstate() != HIT)
                    if (attackBox.intersects(s.getHitBox())) {
                        s.hurt(20);
                        return;
                    }

        for (Mushroom m : currentLevel.getMushrooms())
            if (m.isActive()) {
                if (m.getstate() == ATTACK && m.getAniIndex() >= 3)
                    return;
                else {
                    if (m.getstate() != DEAD && m.getstate() != HIT)
                        if (attackBox.intersects(m.getHitBox())) {
                            m.hurt(20);
                            return;
                        }
                }
            }

        for (Bush b : currentLevel.getBushes())
            if (b.isActive()) {
                if (b.getstate() != DEAD && b.getstate() != HIT)
                    if (attackBox.intersects(b.getHitBox())) {
                        b.hurt(20);
                        return;
                    }
            }
    }

    private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
        for (int j = 0; j < tempArr.length; j++)
            for (int i = 0; i < tempArr[j].length; i++)
                tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
        return tempArr;
    }

    public void resetAllEnemies() {
        for (Slime s : currentLevel.getSlimes())
            s.resetEnemy();
        for (Mushroom m : currentLevel.getMushrooms())
            m.resetEnemy();
        for (Bush b : currentLevel.getBushes())
            b.resetEnemy();
    }
}
