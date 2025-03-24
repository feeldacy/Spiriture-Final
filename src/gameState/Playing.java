package gameState;

import Entities.EnemyManager;
import Entities.Player;
import Items.ItemManager;
import Levels.LevelManager;
import Main.Game;
import UI.GameCompletedOverlay;
import UI.GameOverOverlay;
import UI.LevelCompletedOverlay;
import UI.PausedOverlay;
import Utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import static Utilz.Constants.Environment.*;

import java.security.SecureRandom;

public class Playing extends State implements StateMethods{
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ItemManager itemManager;
    private PausedOverlay pausedOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private GameCompletedOverlay gameCompletedOverlay;

    private boolean paused = false;

    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int maxLevelOffsetX;

    private BufferedImage backgroundImg, movingTree, movingSmallTree;
    private int[] movingSmallTreePos;
    private SecureRandom rnd = new SecureRandom();

    private boolean gameOver;
    private boolean lvlCompleted;
    private boolean playerDying;
    private boolean gameCompleted;


    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_MAIN);
        movingTree = LoadSave.GetSpriteAtlas(LoadSave.MOVING_BACKGROUND);
        movingSmallTree = LoadSave.GetSpriteAtlas(LoadSave.MOVING_BACKGROUND_2);
        movingSmallTreePos = new int[8];
        for (int i = 0; i < movingSmallTreePos.length; i++)
            movingSmallTreePos[i] = (int) (90 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));

        calcLvlOffset();
        loadStartLevel();
    }

    public void loadNextLevel() {
        levelManager.setLevelIndex(levelManager.getLvlIndex() + 1);
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        resetAll();
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        itemManager.loadObject(levelManager.getCurrentLevel());
    }

    private void calcLvlOffset() {
        maxLevelOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        itemManager = new ItemManager(this);

        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        pausedOverlay = new PausedOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
        gameCompletedOverlay = new GameCompletedOverlay(this);

    }

    @Override
    public void update() {
        if (paused) {
            pausedOverlay.update();
        } else if (lvlCompleted){
            levelCompletedOverlay.update();
        } else if (gameCompleted) {
            gameCompletedOverlay.update();
        }else if (gameOver) {
            gameOverOverlay.update();
        }else if (playerDying) {
            player.update();
        }else {
            levelManager.update();
            itemManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLvlData());
            checkCloseToBorder();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitBox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset > maxLevelOffsetX)
            xLvlOffset = maxLevelOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        drawTree(g);

        levelManager.draw(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        itemManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pausedOverlay.draw(g);
        } else if (gameOver)
            gameOverOverlay.draw(g);
        else if (lvlCompleted)
            levelCompletedOverlay.draw(g);
        else if (gameCompleted)
            gameCompletedOverlay.draw(g);
    }
// emang beda sama tutornya
    private void drawTree(Graphics g){
        for (int i = 0; i < 8; i++)
            g.drawImage(movingTree, (i * MOVING_TREE_WIDTH) - (int) (xLvlOffset * 0.7), 0, MOVING_TREE_WIDTH, MOVING_TREE_HEIGHT, null);

        for (int i = 0; i < 8; i++)
            g.drawImage(movingSmallTree, (i * MOVING_SMALL_TREE_WIDTH) - (int) (xLvlOffset * 0.3), 0, MOVING_SMALL_TREE_WIDTH, MOVING_SMALL_TREE_HEIGHT, null);

    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        itemManager.resetAllItems();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkItemHit(Rectangle2D.Float attackBox) {
        itemManager.checkItemHit(attackBox);
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void checkPotionTouched(Rectangle2D.Float hitBox) {
        itemManager.checkObjekTouched(hitBox);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1)
                player.setAttacking(true);
            else if (e.getButton() == MouseEvent.BUTTON3)
                player.powerAttack();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:

                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
            }
    }

        @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted)
            if (paused)
                pausedOverlay.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gameOver)
            gameOverOverlay.mousePressed(e);
        else if (paused)
            pausedOverlay.mousePressed(e);
        else if (lvlCompleted)
            levelCompletedOverlay.mousePressed(e);
        else if (gameCompleted)
            gameCompletedOverlay.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (gameOver)
            gameOverOverlay.mouseReleased(e);
        else if (paused)
            pausedOverlay.mouseReleased(e);
        else if (lvlCompleted)
            levelCompletedOverlay.mouseReleased(e);
        else if (gameCompleted)
            gameCompletedOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (gameOver)
            gameOverOverlay.mouseMoved(e);
        else if (paused)
            pausedOverlay.mouseMoved(e);
        else if (lvlCompleted)
            levelCompletedOverlay.mouseMoved(e);
        else if (gameCompleted)
            gameCompletedOverlay.mouseMoved(e);
    }

    public void setLevelCompleted(boolean levelCompleted) {
        game.getAudioPlayer().lvlCompleted();
        if (levelManager.getLvlIndex() + 1 >= levelManager.getAmountOfLevels()) {
            gameCompleted = true;
            levelManager.setLevelIndex(0);
            levelManager.loadNextLevel();
            resetAll();
            return;
        }
        this.lvlCompleted = levelCompleted;
    }


    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLevelOffsetX = lvlOffset;
    }

    public void unpauseGame(){
        paused = false;
    }

    public void resetGameCompleted() {
        gameCompleted = false;
    }

    public Player getPlayer(){
        return player;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public ItemManager getItemManager(){
        return  itemManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }
}
