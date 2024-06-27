package gameState;

import Main.Game;
import UI.ButtonMenu;
import audio.AudioPlayer;

import java.awt.event.MouseEvent;

public class State {

    protected Game game;
    public State(Game game){
        this.game = game;
    }

    public boolean isIn(MouseEvent e, ButtonMenu mb){
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame(){
        return game;
    }

    public void setGameState(GameState state){
        switch (state){
            case MENU -> game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
            case PLAYING -> game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLvlIndex());
        }

        GameState.state = state;

    }

}
