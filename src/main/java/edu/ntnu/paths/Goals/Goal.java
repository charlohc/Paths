package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;

/**
 * Goal interface class
 * represents a goal the player has for the game
 */
public interface Goal {
    public Boolean isFulfilled(Player player);
}
