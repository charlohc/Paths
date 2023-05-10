package edu.ntnu.paths.Managers;

import edu.ntnu.paths.GameDetails.Player;

public class PlayerManager {
    private static PlayerManager instance;
    private Player player;

    private PlayerManager() {}

    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }

    public Player getPlayer() {
        return player;
    }

    public Boolean playerExist() {
        return player != null;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
