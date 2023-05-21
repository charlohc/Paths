package edu.ntnu.paths.Managers;

import edu.ntnu.paths.GameDetails.Player;

/**
 * The PlayerManager class is responsible for managing the player instance.
 */
public class PlayerManager {
    private static PlayerManager instance;
    private Player player;

    private PlayerManager() {}

    /**
     * Retrieves the instance of PlayerManager.
     *
     * @return The PlayerManager instance.
     */
    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }

    /**
     * Retrieves the current player.
     *
     * @return The current player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the current player.
     *
     * @param player The player to be set as the current player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
