package Levels;

import Main.Game;
import Utilz.LoadSave;
import gameState.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private BufferedImage[] waterSprite;

    private ArrayList<Level> levels;
    private int lvlIndex = 0, aniTick, aniIndex;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprite();
        createWater();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel() {
        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
        game.getPlaying().getItemManager().loadObject(newLevel);
    }


    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevels)
            levels.add(new Level(img));
    }

    private void importOutsideSprite() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);

        levelSprite = new BufferedImage[63];
        for (int j = 0; j < 9; j++){
            for (int i = 0; i < 7; i++){
                int index = j * 7 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    public void draw(Graphics g, int lvlOffset) {
        for (int j = 0; j < Game.TILE_INHEIGHT; j++)
            for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                int x = Game.TILE_SIZE * i - lvlOffset;
                int y = Game.TILE_SIZE * j;
                if (index == 61)
                    g.drawImage(waterSprite[aniIndex], x, y, Game.TILE_SIZE, Game.TILE_SIZE, null);
                else if (index == 62)
                    g.drawImage(waterSprite[4], x, y, Game.TILE_SIZE, Game.TILE_SIZE, null);
                else
                    g.drawImage(levelSprite[index], x, y, Game.TILE_SIZE, Game.TILE_SIZE, null);
            }
    }

    private void createWater() {
        waterSprite = new BufferedImage[5];
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.WATER_TOP);
        for (int i = 0; i < 4; i++)
            waterSprite[i] = img.getSubimage(i * 32, 0, 32, 32);
        waterSprite[4] = LoadSave.GetSpriteAtlas(LoadSave.WATER_BOTTOM);
    }

    public void update() {
        updateWaterAnimation();
    }

    private void updateWaterAnimation() {
        aniTick++;
        if (aniTick >= 40) {
            aniTick = 0;
            aniIndex++;

            if (aniIndex >= 4)
                aniIndex = 0;
        }
    }

    public Level getCurrentLevel(){
        return levels.get(lvlIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }

    public int getLvlIndex() {
        return lvlIndex;
    }

    public void setLevelIndex(int lvlIndex) {
        this.lvlIndex = lvlIndex;
    }
}
