package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;

public class GoldGoal implements Goal{
    private int minimumGold;

    public boolean goldGoal(int minimumGold) {
        if (minimumGold < 0) return false;
        this.minimumGold = minimumGold;
        return true;
    }


    @Override
    public Boolean isFulfilled(Player player) {
        if (player == null) return false;
        return (player.getGold() >= this.minimumGold);
    }
}
