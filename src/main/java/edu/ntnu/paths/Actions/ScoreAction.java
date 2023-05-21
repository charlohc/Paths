package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;

/**
 * Represents an action that modifies a player's score.
 */
public class ScoreAction implements Action {
    private int points;

    /**
     * Returns the points involved in the action.
     *
     * @return The points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the points for the action.
     *
     * @param points The points to be set.
     * @return True if the points were set successfully, false otherwise.
     */
    public boolean scoreAction(int points) {
        if (points <= 0)
            return false;
        this.points = points;
        return true;
    }

    /**
     * Executes the score action on the specified player.
     *
     * @param player The player on which the score action will be executed.
     * @return True if the score action was executed successfully, false otherwise.
     */
    @Override
    public boolean execute(Player player) {
        if (player == null)
            return false;
        player.addScore(this.points);
        return true;
    }

    /**
     * Returns a string representation of the ScoreAction object.
     *
     * @return The string representation of the ScoreAction object.
     */
    @Override
    public String toString() {
        return "ScoreAction:" + points;
    }
}
