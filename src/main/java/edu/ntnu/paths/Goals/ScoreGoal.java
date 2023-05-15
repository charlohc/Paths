package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;

public class ScoreGoal implements Goal{
    private int minimumPoints;

    public int getMinimumPoints(){
        return minimumPoints;
    }
    public boolean scoreGoal(int minimumPoints) {
        if (minimumPoints < 0) return false;
        this.minimumPoints = minimumPoints;
        return true;
    }

    @Override
    public Boolean isFulfilled(Player player) {
        if (player == null) return false;
        return (player.getScore() >= this.minimumPoints);
    }
}
