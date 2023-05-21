package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;

/**
 * ScoreGoal represents a goal based on achieving a minimum score.
 */
public class ScoreGoal implements Goal {
    private int score;

    /**
     * Gets the minimum score required for the goal.
     *
     * @return The minimum score required.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the minimum score required for the goal.
     *
     * @param minimumPoints The minimum score required.
     * @return True if the minimum score is valid and set successfully, false otherwise.
     */
    public boolean scoreGoal(int minimumPoints) {
        if (minimumPoints < 0) {
            return false;
        }
        this.score = minimumPoints;
        return true;
    }

    /**
     * Checks if the score goal is fulfilled by the player.
     *
     * @param player The player to check against the score goal.
     * @return True if the player's score is greater than or equal to the minimum score, false otherwise.
     */
    @Override
    public Boolean isFulfilled(Player player) {
        if (player == null) {
            return false;
        }
        return player.getScore() >= this.score;
    }
}
