package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;

import java.util.ArrayList;
import java.util.List;
public class InventoryGoal implements Goal{
    private List<String> inventory;

    public List<String> getInventory() {
        if (inventory == null) {
            return new ArrayList<>();
        }
        return inventory;
    }
    public void inventoryGoal(List<String> mandatoryItems) {
        this.inventory = mandatoryItems;
    }

    @Override
    public Boolean isFulfilled(Player player) {
        if (player == null) return false;
        return (player.getInventory().containsAll(inventory));
    }
}

