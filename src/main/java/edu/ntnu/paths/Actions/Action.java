package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;

/**
 * Action Interface class
 * Represents an action happening when following a link to a passage
 */
public interface Action {
   public boolean execute(Player player);

}