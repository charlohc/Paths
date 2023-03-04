package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;

public class ScoreAction implements Action {
    private int points;


    public boolean scoreAction(int points) {
        if (points <= 0) return false;
        this.points = points;
        return true;
    }
    @Override
    public boolean execute(Player player) {
        if (player == null) return false;
        player.addScore(this.points);
        return true;
    }
}
