package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;

public class HealthGoal implements Goal {
    private int health;

    public int getHealth(){
        return health;
    }
    public boolean healthGoal(int minimumHealth) {
        if (minimumHealth <= 0) return false;
        this.health = minimumHealth;
        return true;
    }


    @Override
    public Boolean isFulfilled(Player player) {
        if (player == null) return false;
        return (player.getHealth() >= this.health);
    }
}
