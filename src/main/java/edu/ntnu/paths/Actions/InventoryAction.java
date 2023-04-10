package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;

public class InventoryAction implements Action{
    private String item;

    public boolean inventoryAction(String item) {
       if (item.isEmpty()) return false;
        this.item = item;
        return true;
    }
    @Override
    public boolean execute(Player player) {
        if (player == null) return false;
        player.addToInventory(this.item);
        return true;
    }

    @Override
    public String toString() {
        return "InventoryAction:" + item;
    }
}
