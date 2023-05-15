package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;

public class HealthGoal implements Goal {
    private int minimumHealth;

    public int getMinimumHealth(){
        return minimumHealth;
    }
    public boolean healthGoal(int minimumHealth) {
        if (minimumHealth <= 0) return false;
        this.minimumHealth = minimumHealth;
        return true;
    }


    @Override
    public Boolean isFulfilled(Player player) {
        if (player == null) return false;
        return (player.getHealth() >= this.minimumHealth);
    }
}
