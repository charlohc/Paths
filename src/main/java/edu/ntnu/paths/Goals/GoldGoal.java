package edu.ntnu.paths.Goals;

import edu.ntnu.paths.gameDetails.Player;

public class GoldGoal implements Goal{
    private int minimumGold;

    public void goldGoal(int minimumGold) {
        this.minimumGold = minimumGold;
    }


    @Override
    public Boolean isFulfilled(Player player) {
        return (player.getGold() >= this.minimumGold);
    }
}
