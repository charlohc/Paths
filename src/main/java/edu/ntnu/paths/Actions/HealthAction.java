package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;

/**
 * Represents an action that modifies a player's health.
 */
public class HealthAction implements Action {
    private int health;

    /**
     * Returns the amount of health involved in the action.
     *
     * @return The amount of health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the amount of health for the action.
     *
     * @param health The amount of health to be set.
     */
    public void healthAction(int health) {
        this.health = health;
    }

    /**
     * Executes the health action on the specified player.
     *
     * @param player The player on which the health action will be executed.
     * @return True if the health action was executed successfully, false otherwise.
     */
    @Override
    public boolean execute(Player player) {
        if (player == null)
            return false;
        player.changeHealth(this.health);
        return true;
    }

    /**
     * Returns a string representation of the HealthAction object.
     *
     * @return The string representation of the HealthAction object.
     */
    @Override
    public String toString() {
        return "HealthAction:" + health;
    }
}
