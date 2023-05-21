package edu.ntnu.paths.Managers;

import edu.ntnu.paths.GameDetails.Game;

/**
 * The GameManager class is responsible for managing the game instance.
 */
public class GameManager {
    private static GameManager instance;
    private Game game;

    private GameManager() {}

    /**
     * Retrieves the instance of GameManager.
     *
     * @return The GameManager instance.
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Retrieves the current game.
     *
     * @return The current game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the current game.
     *
     * @param game The game to be set as the current game.
     */
    public void setGame(Game game) {
        this.game = game;
    }

}
