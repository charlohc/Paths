package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;

public class ScoreGoal implements Goal{
    private int minimumPoints;

    public void scoreGoal(int minimumPoints) {
        this.minimumPoints = minimumPoints;
    }

    @Override
    public Boolean isFulfilled(Player player) {
        return (player.getScore() >= this.minimumPoints);
    }
}
