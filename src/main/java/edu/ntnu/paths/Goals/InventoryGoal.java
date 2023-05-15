package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;

import java.util.ArrayList;
import java.util.List;

public class InventoryGoal implements Goal{
    private List<String> mandatoryItems;

    public List<String> getMandatoryItems() {
        return mandatoryItems;
    }
    public void inventoryGoal(List<String> mandatoryItems) {
        this.mandatoryItems = mandatoryItems;
    }

    @Override
    public Boolean isFulfilled(Player player) {
        if (player == null) return false;
        return (player.getInventory().containsAll(mandatoryItems));
    }
}

