package edu.ntnu.paths.GameDetails;

import edu.ntnu.paths.Goals.Goal;
import edu.ntnu.paths.StoryDetails.Link;
import edu.ntnu.paths.StoryDetails.Passage;
import edu.ntnu.paths.StoryDetails.Story;

import java.util.List;

public class GameBuilder {

    public Player player;
    public Story story;
    public List<Goal> goals;

    public static GameBuilder newInstance() {
        return new GameBuilder();
    }

    private GameBuilder() {}


    public GameBuilder setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public GameBuilder setStory(Story story) {
        this.story = story;
        return this;
    }

    public GameBuilder setGoals(List<Goal> goals) {
        this.goals = goals;
        return this;
    }
    public Game build() {
        return new Game(this);
    }
}
