package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;

public class ScoreAction implements Action {
    private int points;


    public void scoreAction(int points) {
        this.points = points;
    }
    @Override
    public void execute(Player player) {
        player.addScore(this.points);
    }
}
