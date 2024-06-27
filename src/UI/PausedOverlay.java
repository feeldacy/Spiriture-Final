package UI;

import Main.Game;
import Utilz.LoadSave;
import gameState.GameState;
import gameState.Playing;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static Utilz.Constants.UI.UrmButtons.*;


public class PausedOverlay {
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgWidth, bgHeight;
    private AudioOptions audioOptions;
    private UrmButtons menuB, replayB, unpauseB;

    public PausedOverlay(Playing playing){
        this.playing = playing;
        loadBackground();
        audioOptions = playing.getGame().getAudioOptions();

        createUrmButtons();
    }

    private void createUrmButtons() {
        int menuX = (int)(313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (462 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);

        menuB = new UrmButtons(menuX, bY, URM_SIZE, URM_SIZE, 2);
        replayB = new UrmButtons(replayX, bY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new UrmButtons(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgWidth = (int)(backgroundImg.getWidth() * Game.SCALE);
        bgHeight = (int)(backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgWidth / 2;
        bgY = (int)(25 * Game.SCALE);
    }

    public void update(){
        menuB.update();
        replayB.update();
        unpauseB.update();

        audioOptions.update();
    }

    public void draw(Graphics g){
        // Bacground
        g.drawImage(backgroundImg, bgX, bgY, bgWidth, bgHeight, null);

        // Urm button
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);

        audioOptions.draw(g);
    }

    public void mouseDragged(MouseEvent e){
        audioOptions.mouseDragged(e);
    }

    public void mousePressed(MouseEvent e) {
       if (isIn(e, menuB))
            menuB.setMousePressed(true);
       else if (isIn(e, replayB))
            replayB.setMousePressed(true);
       else if (isIn(e, unpauseB))
            unpauseB.setMousePressed(true);
       else
            audioOptions.mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
         if (isIn(e, menuB)){
            if (menuB.isMousePressed()){
                playing.resetAll();
                playing.setGameState(GameState.MENU);
                playing.unpauseGame();
            }
        } else if (isIn(e, replayB)) {
            if (replayB.isMousePressed()) {
                playing.resetAll();
                playing.unpauseGame();
            }
        } else if (isIn(e, unpauseB)){
            if (unpauseB.isMousePressed()){
                playing.unpauseGame();
            }
        }
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);

        if (isIn(e, menuB)){
            menuB.setMouseOver(true);
        } else if (isIn(e, replayB)){
            replayB.setMouseOver(true);
        } else if (isIn(e, unpauseB)){
            unpauseB.setMouseOver(true);
        } else
            audioOptions.mouseMoved(e);
    }

    private boolean isIn(MouseEvent e, PausedButton b){
        return  (b.getBounds().contains(e.getX(), e.getY()));
    }
}
