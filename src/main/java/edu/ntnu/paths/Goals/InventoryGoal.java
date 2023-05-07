package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;

import java.util.List;

public class InventoryGoal implements Goal{
    private List<String> mandatoryItems;

//can be empty?
    public void inventoryGoal(List<String> mandatoryItems) {
        this.mandatoryItems = mandatoryItems;
    }

    @Override
    public Boolean isFulfilled(Player player) {
        if (player == null) return false;
        return (player.getInventory().containsAll(mandatoryItems));
    }
}

 /* public boolean inventoryGoal(List<String> mandatoryItems) {
        if(mandatoryItems.isEmpty()) return false;
        this.mandatoryItems = mandatoryItems;
        return true;
    }*/
