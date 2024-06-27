package Utilz;

import Items.*;
import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Utilz.Constants.ItemConstants.*;

public class HelpMethods {

    public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!isSolid(x, y, lvlData))
            if (!isSolid(x + width, y + height, lvlData))
                if (!isSolid(x + width, y, lvlData))
                    if (!isSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    private static boolean isSolid(float x, float y, int[][] lvlData){
        int maxWidth = lvlData[0].length * Game.TILE_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;
        float xIndex = x / Game.TILE_SIZE;
        float yIndex = y / Game.TILE_SIZE;

        return isTileSolid((int)xIndex, (int)yIndex, lvlData);
    }

    private static int GetTileValue(float xPos, float yPos, int[][] lvlData) {
        int xCord = (int) (xPos / Game.TILE_SIZE);
        int yCord = (int) (yPos / Game.TILE_SIZE);
        return lvlData[yCord][xCord];
    }
    public static boolean IsEntityInWater(Rectangle2D.Float hitbox, int[][] lvlData) {
        if (GetTileValue(hitbox.x, hitbox.y + hitbox.height, lvlData) != 61)
            if (GetTileValue(hitbox.x + hitbox.width, hitbox.y + hitbox.height, lvlData) != 61)
                return false;
        return true;
    }

    public static boolean isTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        switch (value) {
            case 60, 61, 62:
                return false;
            default:
                return true;
        }

    }

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILE_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILE_SIZE;
            int xOffset = (int) (Game.TILE_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * Game.TILE_SIZE;
    }

    public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILE_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILE_SIZE;
            int yOffset = (int) (Game.TILE_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else
            // Jumping
            return currentTile * Game.TILE_SIZE;

    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;
        return true;
    }

    public static boolean isFloor(Rectangle2D.Float hitBox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0)
            return isSolid(hitBox.x + hitBox.width + xSpeed, hitBox.y + hitBox.height + 1, lvlData);
        else
            return isSolid(hitBox.x + xSpeed, hitBox.y + hitBox.height + 1, lvlData);
    }

    public static boolean IsFloor(Rectangle2D.Float hitBox, int[][] lvlData) {
        if (!isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData))
            if (!isSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData))
                return false;
        return true;
    }

//    public static boolean isAllTilesClear(int xStart, int xEnd, int y, int[][] lvlData) {
//        for (int i = 0; i < xEnd - xStart; i++)
//            if (isTileSolid(xStart + i, y, lvlData))
//                return false;
//        return true;
//    }

    public static boolean isAllTileWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++){
            if (!isTileSolid(xStart + i, y + 1, lvlData))
                return false;
        }
        return true;
    }

    public static boolean isSightClear(int[][] lvlData, Rectangle2D.Float firstHitBox, Rectangle2D.Float secondHitBox, int yTile) {
        int firstXTile = (int) (firstHitBox.x / Game.TILE_SIZE);
        int secondXTile = (int) (secondHitBox.x / Game.TILE_SIZE);

        if (firstXTile > secondXTile)
            return isAllTileWalkable(secondXTile, firstXTile, yTile, lvlData);
        else
            return isAllTileWalkable(firstXTile, secondXTile, yTile, lvlData);

    }

    public static int[][] GetLevelData(BufferedImage img){
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];
        for (int j = 0; j < img.getHeight(); j++){
            for (int i = 0; i < img.getWidth(); i++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 63){
                    value = 0;
                }
                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }

}
