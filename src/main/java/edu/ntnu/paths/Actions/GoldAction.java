package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;

public class GoldAction implements Action{
    private int gold;

    public Boolean goldAction(int gold) {
        if (gold <= 0) return false;
        this.gold = gold;
        return true;
    }
    @Override
    public boolean execute(Player player) {
        if (player == null) return false;
        player.addGold(this.gold);
        return true;
    }

    @Override
    public String toString() {
        return "{GoldAction: " + gold + '}';
    }
}
