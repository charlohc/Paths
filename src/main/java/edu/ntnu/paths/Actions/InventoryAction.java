package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;

/**
 * Represents an action that modifies a player's inventory.
 */
public class InventoryAction implements Action {
    private String item;

    /**
     * Returns the item involved in the action.
     *
     * @return The item.
     */
    public String getItem() {
        return item;
    }

    /**
     * Sets the item for the action.
     *
     * @param item The item to be set.
     * @return True if the item was set successfully, false otherwise.
     */
    public boolean inventoryAction(String item) {
        if (item.isEmpty())
            return false;
        this.item = item;
        return true;
    }

    /**
     * Executes the inventory action on the specified player.
     *
     * @param player The player on which the inventory action will be executed.
     * @return True if the inventory action was executed successfully, false otherwise.
     */
    @Override
    public boolean execute(Player player) {
        if (player == null)
            return false;
        player.addToInventory(this.item);
        return true;
    }

    /**
     * Returns a string representation of the InventoryAction object.
     *
     * @return The string representation of the InventoryAction object.
     */
    @Override
    public String toString() {
        return "InventoryAction:" + item;
    }
}
