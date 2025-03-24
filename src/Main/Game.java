package Main;

import UI.AudioOptions;
import audio.AudioPlayer;
import gameState.GameOptions;
import gameState.GameState;
import gameState.Menus;
import gameState.Playing;
import gameState.Credits;


import java.awt.*;

public class Game implements Runnable{

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menus menus;
    private GameOptions gameOptions;
    private Credits credits;

    private AudioOptions audioOptions;
    private AudioPlayer audioPlayer;


    public final static int TILE_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILE_INWIDTH = 26;
    public final static int TILE_INHEIGHT = 14;
    public final static int TILE_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILE_SIZE * TILE_INWIDTH;
    public final static int GAME_HEIGHT = TILE_SIZE * TILE_INHEIGHT;
    private boolean running = true;


    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        startGameLoop();
    }

    private void initClasses() {
        audioOptions = new AudioOptions(this);
        audioPlayer = new AudioPlayer();
        menus = new Menus(this);
        playing = new Playing(this);
        credits = new Credits(this);
        gameOptions = new GameOptions(this);

    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (GameState.state) {
            case MENU -> menus.update();
            case PLAYING -> playing.update();
            case OPTIONS -> gameOptions.update();
            case CREDITS -> credits.update();
            case QUIT -> System.exit(0);
        }
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case MENU -> menus.draw(g);
            case PLAYING -> playing.draw(g);
            case OPTIONS -> gameOptions.draw(g);
            case CREDITS -> credits.draw(g);
        }
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (running) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                deltaF--;
                frames++;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        if (GameState.state == GameState.PLAYING) {
            playing.getPlayer().resetDirBooleans();
        }
    }

    public Menus getMenus() {
        return menus;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Credits getCredits() {
        return credits;
    }

    public GameOptions getGameOptions() {
        return gameOptions;
    }

    public AudioOptions getAudioOptions() {
        return audioOptions;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }
}
