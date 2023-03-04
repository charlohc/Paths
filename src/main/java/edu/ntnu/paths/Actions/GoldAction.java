package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;

public class GoldAction implements Action{
    private int gold;

    public void goldAction(int gold) {
        this.gold = gold;
    }
    @Override
    public void execute(Player player) {
        player.addGold(this.gold);
    }
}
