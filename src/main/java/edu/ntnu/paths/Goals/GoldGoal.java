package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;

/**
 * GoldGoal represents a goal based on achieving a certain amount of gold.
 */
public class GoldGoal implements Goal {
    private int gold;

    /**
     * Gets the required amount of gold for the goal.
     *
     * @return The required amount of gold.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Sets the required amount of gold for the goal.
     *
     * @param minimumGold The minimum amount of gold required.
     * @return True if the minimum gold amount is valid and set successfully, false otherwise.
     */
    public boolean goldGoal(int minimumGold) {
        if (minimumGold < 0) {
            return false;
        }
        this.gold = minimumGold;
        return true;
    }

    /**
     * Checks if the gold goal is fulfilled by the player.
     *
     * @param player The player to check against the gold goal.
     * @return True if the player's gold amount is equal to or greater than the required gold amount, false otherwise.
     */
    @Override
    public Boolean isFulfilled(Player player) {
        if (player == null) {
            return false;
        }
        return player.getGold() >= this.gold;
    }
}
