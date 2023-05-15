package edu.ntnu.paths.Managers;

import edu.ntnu.paths.GameDetails.Game;

public class GameManager {
    private static GameManager instance;
    private Game game;

    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public Game getGame() {
        return game;
    }


    public void setGame(Game game) {
        this.game = game;
    }

}
