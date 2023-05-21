package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * InventoryGoal represents a goal based on having specific items in the player's inventory.
 */
public class InventoryGoal implements Goal {
    private List<String> inventory;

    /**
     * Gets the list of mandatory items for the goal.
     *
     * @return The list of mandatory items.
     */
    public List<String> getInventory() {
        if (inventory == null) {
            return new ArrayList<>();
        }
        return inventory;
    }

    /**
     * Sets the list of mandatory items for the goal.
     *
     * @param mandatoryItems The list of mandatory items.
     */
    public void inventoryGoal(List<String> mandatoryItems) {
        this.inventory = mandatoryItems;
    }

    /**
     * Checks if the inventory goal is fulfilled by the player.
     *
     * @param player The player to check against the inventory goal.
     * @return True if the player's inventory contains all the mandatory items, false otherwise.
     */
    @Override
    public Boolean isFulfilled(Player player) {
        if (player == null) {
            return false;
        }
        return player.getInventory().containsAll(inventory);
    }
}
