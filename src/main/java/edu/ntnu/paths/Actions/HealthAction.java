package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;

//tester, exception og deep copy
//health action can give negative effect health, but cannot make health negative only 0
public class HealthAction implements Action{
    private int health;

    public int getHealth() {
        return health;
    }

    public boolean healthAction(int health) {
        if (health <= 0) return false;
        this.health = health;
        return true;
    }
    @Override
    public boolean execute(Player player) {
        if (player == null) return false;
        player.removeHealth(this.health);
        return true;
    }

    @Override
    public String toString() {
        return "HealthAction:" + health;
    }
}
