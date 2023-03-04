package edu.ntnu.paths.Goals;

import edu.ntnu.paths.gameDetails.Player;

import java.util.List;

public class InventoryGoal implements Goal{
    private List<String> mandatoryItems;

    public void inventoryGoal(List<String> mandatoryItems) {
        this.mandatoryItems = mandatoryItems;
    }
    @Override
    public Boolean isFulfilled(Player player) {
        return (player.getInventory().containsAll(mandatoryItems));
    }
}
