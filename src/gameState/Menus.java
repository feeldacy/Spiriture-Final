package gameState;

import Main.Game;
import UI.ButtonMenu;
import Utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menus extends State implements StateMethods {

    private ButtonMenu[] buttons = new ButtonMenu[4];
    private BufferedImage backgroundImg, homeBackground;
    private int menuX, menuY, menuWidth, menuHeight;


    public Menus(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        homeBackground = LoadSave.GetSpriteAtlas(LoadSave.HOME_BACKGROUND);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (25 * Game.SCALE);
    }

    private void loadButtons() {
        buttons[0] = new ButtonMenu(Game.GAME_WIDTH / 2, (int) (130 * Game.SCALE), 0, GameState.PLAYING);
        buttons[1] = new ButtonMenu(Game.GAME_WIDTH / 2, (int) (200 * Game.SCALE), 1, GameState.OPTIONS);
        buttons[2] = new ButtonMenu(Game.GAME_WIDTH / 2, (int) (270 * Game.SCALE), 3, GameState.CREDITS);
        buttons[3] = new ButtonMenu(Game.GAME_WIDTH / 2, (int) (340 * Game.SCALE), 2, GameState.QUIT);
    }

    @Override
    public void update() {
        for (ButtonMenu mb : buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(homeBackground, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        for (ButtonMenu mb : buttons)
            mb.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (ButtonMenu mb : buttons){
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (ButtonMenu mb : buttons) {
            if (isIn(e, mb)){
                if (mb.isMousePressed())
                    mb.applyGameState();
                if (mb.gameState() == GameState.PLAYING)
                    game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLvlIndex());
                break;
            }
        }

        resetButtons();

    }

    private void resetButtons() {
        for (ButtonMenu mb : buttons)
            mb.resetBools();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (ButtonMenu mb : buttons)
            mb.setMouseOver(false);

        for (ButtonMenu mb : buttons)
            if (isIn(e, mb)){
                mb.setMouseOver(true);
                break;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
}
