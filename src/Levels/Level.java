package Levels;

import Entities.Bush;
import Entities.Mushroom;
import Entities.Slime;
import Items.ItemContainer;
import Items.Potion;
import Main.Game;

import static Utilz.Constants.EnemyConstants.*;
import static Utilz.Constants.ItemConstants.*;
import static Utilz.HelpMethods.GetLevelData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Slime> slimes = new ArrayList<>();
    private ArrayList<Mushroom> mushrooms = new ArrayList<>();
    private ArrayList<Bush> bushes = new ArrayList<>();
    private ArrayList<Potion> potions = new ArrayList<>();
    private ArrayList<ItemContainer> containers = new ArrayList<>();
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLevelOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage img) {
        this.img = img;
        lvlData = new int[img.getHeight()][img.getWidth()];
        loadLevel();
        calcLvlOffsets();
    }

    private void loadLevel() {

        for (int y = 0; y < img.getHeight(); y++)
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                loadLevelData(red, x, y);
                loadEntities(green, x, y);
                loadObjects(blue, x, y);
            }
    }

    private void loadLevelData(int redValue, int x, int y) {
        if (redValue >= 63)
            lvlData[y][x] = 0;
        else
            lvlData[y][x] = redValue;
    }

    private void loadEntities(int greenValue, int x, int y) {
        switch (greenValue) {
            case SLIME ->  slimes.add(new Slime (x * (float )Game.TILE_SIZE, y * (float) Game.TILE_SIZE));
            case MUSHROOM -> mushrooms.add(new Mushroom(x * (float) Game.TILE_SIZE, y * (float) Game.TILE_SIZE));
            case BUSH -> bushes.add(new Bush(x * (float) Game.TILE_SIZE, y * (float) Game.TILE_SIZE));
            case 100 -> playerSpawn = new Point(x * Game.TILE_SIZE, y * Game.TILE_SIZE);
        }
    }

    private void loadObjects(int blueValue, int x, int y) {
        switch (blueValue) {
            case RED_POTION, BLUE_POTION -> potions.add(new Potion(x * Game.TILE_SIZE, y * Game.TILE_SIZE, blueValue));
            case BOX, BARREL -> containers.add(new ItemContainer(x * Game.TILE_SIZE, y * Game.TILE_SIZE, blueValue));
        }
    }

    public int[][] getLevelData() {
        return lvlData;
    }
    private void calcLvlOffsets(){
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILE_INWIDTH;
        maxLevelOffsetX = Game.TILE_SIZE * maxTilesOffset;
    }

    public ArrayList<Slime> getSlimes() {
        return slimes;
    }

    public ArrayList<Mushroom> getMushrooms() {
        return mushrooms;
    }

    public ArrayList<Bush> getBushes() {
        return bushes;
    }
    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }

    public int[][] getLvlData(){
        return lvlData;
    }

    public int getLvlOffset() {
        return maxLevelOffsetX;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public ArrayList<ItemContainer> getContainers() {
        return containers;
    }
}

