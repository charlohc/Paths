package edu.ntnu.paths.Goals;

import edu.ntnu.paths.gameDetails.Player;

public class HealthGoal implements Goal {
    private int minimumHealth;

    public void healthGoal(int minimumHealth) {
        this.minimumHealth = minimumHealth;
    }


    @Override
    public Boolean isFulfilled(Player player) {
        return (player.getHealth() >= this.minimumHealth);
    }
}
