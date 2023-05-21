package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;

/**
 * Represents an action that adds or subtracts gold from a player.
 */
public class GoldAction implements Action {
    private int gold;

    /**
     * Returns the amount of gold involved in the action.
     *
     * @return The amount of gold.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Performs the gold action by setting the amount of gold.
     *
     * @param gold The amount of gold to be set.
     * @return True if the gold action was performed successfully, false otherwise.
     */
    public Boolean goldAction(int gold) {
        if (gold <= 0)
            return false;
        this.gold = gold;
        return true;
    }

    /**
     * Executes the gold action on the specified player.
     *
     * @param player The player on which the gold action will be executed.
     * @return True if the gold action was executed successfully, false otherwise.
     */
    @Override
    public boolean execute(Player player) {
        if (player == null)
            return false;
        player.addGold(this.gold);
        return true;
    }

    /**
     * Returns a string representation of the GoldAction object.
     *
     * @return The string representation of the GoldAction object.
     */
    @Override
    public String toString() {
        return "GoldAction:" + gold;
    }
}

