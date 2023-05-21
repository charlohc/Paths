package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;

/**
 * HealthGoal represents a goal based on achieving a certain amount of health.
 */
public class HealthGoal implements Goal {
    private int health;

    /**
     * Gets the required amount of health for the goal.
     *
     * @return The required amount of health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the required amount of health for the goal.
     *
     * @param minimumHealth The minimum amount of health required.
     * @return True if the minimum health amount is valid and set successfully, false otherwise.
     */
    public boolean healthGoal(int minimumHealth) {
        if (minimumHealth <= 0) {
            return false;
        }
        this.health = minimumHealth;
        return true;
    }

    /**
     * Checks if the health goal is fulfilled by the player.
     *
     * @param player The player to check against the health goal.
     * @return True if the player's health amount is equal to or greater than the required health amount, false otherwise.
     */
    @Override
    public Boolean isFulfilled(Player player) {
        if (player == null) {
            return false;
        }
        return player.getHealth() >= this.health;
    }
}
