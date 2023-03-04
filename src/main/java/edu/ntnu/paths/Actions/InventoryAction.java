package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;

public class InventoryAction implements Action{
    private String item;

    public void inventoryAction(String item) {
       this.item = item;
    }
    @Override
    public void execute(Player player) {
        player.addToInventory(this.item);
    }
}
