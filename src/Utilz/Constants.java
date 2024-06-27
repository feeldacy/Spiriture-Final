package Utilz;

import Main.Game;

public class Constants {

    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final int ANI_SPEED = 25;

    public static class ItemConstants {

        public static final int RED_POTION = 0;
        public static final int BLUE_POTION = 1;
        public static final int BARREL = 2;
        public static final int BOX = 3;

        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 10;

        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
        public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);

        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
        public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case RED_POTION, BLUE_POTION:
                    return 7;
                case BARREL, BOX:
                    return 8;
            }
            return 1;
        }
    }

    public static class EnemyConstants{

        public static final int SLIME = 0;
        public static final int MUSHROOM = 1;
        public static final int BUSH = 2;

        public static final int IDLE = 0;
        public static final int ATTACK = 1;
        public static final int DEAD = 2;
        public static final int HIT = 3;
        public static final int RUNNING = 4;

        public static final int MUSHROOM_WIDTH_DEFAULT = 80;
        public static final int MUSHROOM_HEIGHT_DEFAULT = 64;
        public static final int MUSHROOM_WIDTH = (int) (MUSHROOM_WIDTH_DEFAULT * Game.SCALE);
        public static final int MUSHROOM_HEIGHT = (int) (MUSHROOM_HEIGHT_DEFAULT * Game.SCALE);

        public static final int MUSHROOM_DRAW_OFFSET_X = (int) (27 * Game.SCALE);
        public static final int MUSHROOM_DRAW_OFFSET_Y = (int) (31 * Game.SCALE);

        public static final int SLIME_WIDTH_DEFAULT = 64;
        public static final int SLIME_HEIGHT_DEFAULT = 64;
        public static final int SLIME_WIDTH = (int) (SLIME_WIDTH_DEFAULT * Game.SCALE);
        public static final int SLIME_HEIGHT = (int) (SLIME_HEIGHT_DEFAULT * Game.SCALE);

        public static final int SLIME_DRAW_OFFSET_X = (int) (20 * Game.SCALE);
        public static final int SLIME_DRAW_OFFSET_Y = (int) (42 * Game.SCALE);

        public static final int BUSH_WIDTH_DEFAULT = 90;
        public static final int BUSH_HEIGHT_DEFAULT = 64;
        public static final int BUSH_WIDTH = (int) (BUSH_WIDTH_DEFAULT * Game.SCALE);
        public static final int BUSH_HEIGHT = (int) (BUSH_HEIGHT_DEFAULT * Game.SCALE);

        public static final int BUSH_DRAW_OFFSET_X = (int) (30 * Game.SCALE);
        public static final int BUSH_DRAW_OFFSET_Y = (int) (37 * Game.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            switch (enemy_state) {

                case IDLE: {
                    if (enemy_type == MUSHROOM)
                        return 7;
                    else if (enemy_type == BUSH)
                        return 8;
                    else
                        return 11;
                }
                case ATTACK: {
                    if (enemy_type == MUSHROOM)
                        return 10;
                    else if (enemy_type == BUSH)
                        return 18;
                    else if (enemy_type == SLIME)
                        return 12;
                }

                case DEAD: {
                    if (enemy_type == MUSHROOM)
                        return 15;
                    else if (enemy_type == BUSH)
                        return 13;
                    else if (enemy_type == SLIME)
                        return 10;
                }

                case HIT: {
                    if (enemy_type == MUSHROOM)
                        return 5;
                    else if (enemy_type == BUSH || enemy_type == SLIME)
                        return 4;
                }

                case RUNNING: {
                    if (enemy_type == MUSHROOM)
                        return 8;
                    else if (enemy_type == BUSH)
                        return 7;
                    else if (enemy_type == SLIME)
                        return 11;
                }
            }
            return 0;
        }

        public static int getMaxHealth(int enemy_type) {
            switch (enemy_type) {
                case SLIME:
//                    return 25;
                    return 15;
                case MUSHROOM:
//                    return 30;
                    return 20;
                case BUSH:
//                    return 40;
                    return 25;
                default:
                    return 1;
            }
        }

        public static int getEnemyDamage(int enemy_type) {
            switch (enemy_type) {
                case SLIME:
//                    return 10;
                    return 5;
                case MUSHROOM:
//                    return 15;
                    return 10;
                case BUSH:
//                    return 20;
                    return 15;
                default:
                    return 0;
            }
        }

    }
    public static class Environment{
        public static final int MOVING_TREE_WIDTH_DEFAULT = 620;
        public static final int MOVING_TREE_HEIGHT_DEFAULT = 360;
        public static final int MOVING_TREE_WIDTH = (int) (MOVING_TREE_WIDTH_DEFAULT * Game.SCALE);
        public static final int MOVING_TREE_HEIGHT = (int) (MOVING_TREE_HEIGHT_DEFAULT * Game.SCALE);


        public static final int MOVING_SMALL_TREE_WIDTH_DEFAULT = 620;
        public static final int MOVING_SMALL_TREE_HEIGHT_DEFAULT = 360;
        public static final int MOVING_SMALL_TREE_WIDTH = (int) (MOVING_SMALL_TREE_WIDTH_DEFAULT * Game.SCALE);
        public static final int MOVING_SMALL_TREE_HEIGHT = (int) (MOVING_SMALL_TREE_HEIGHT_DEFAULT * Game.SCALE);
    }

    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class UrmButtons{
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);
        }

        public static class VolumeButtons{
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT= (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
        }
    }

    public static class Directions{
        public static final int LEFT = 0;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int WALK = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int HIT = 4;
        public static final int DEATH = 5;
        public static final int ATTACK = 6;


        public static int getSpriteAmount(int player_action){
            switch (player_action){
                case DEATH:
                    return 8;
                case WALK:
                case ATTACK:
                    return 6;
                case JUMP:
                    return 5;
                case IDLE:
                case HIT:
                    return 4;
                case FALLING:
                    return 3;
            }
            return player_action;
        }
    }
}
