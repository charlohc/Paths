package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;

//tester, exception og deep copy 
public class HealthAction implements Action{
    private int health;

    public void healthAction(int health) {
        this.health = health;
    }
    @Override
    public void execute(Player player) {
        player.addHealth(this.health);
    }
}
