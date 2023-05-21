package edu.ntnu.paths.GameDetails;

import edu.ntnu.paths.Goals.Goal;
import edu.ntnu.paths.StoryDetails.Link;
import edu.ntnu.paths.StoryDetails.Passage;
import edu.ntnu.paths.StoryDetails.Story;

import java.util.List;

/**
 * Builder class for constructing a Game object.
 */
public class GameBuilder {

    public Player player;
    public Story story;
    public List<Goal> goals;

    /**
     * Creates a new instance of GameBuilder.
     *
     * @return The GameBuilder instance.
     */
    public static GameBuilder newInstance() {
        return new GameBuilder();
    }

    private GameBuilder() {}

    /**
     * Sets the player for the game.
     *
     * @param player The player object.
     * @return The GameBuilder instance.
     */
    public GameBuilder setPlayer(Player player) {
        this.player = player;
        return this;
    }

    /**
     * Sets the story for the game.
     *
     * @param story The story object.
     * @return The GameBuilder instance.
     */
    public GameBuilder setStory(Story story) {
        this.story = story;
        return this;
    }

    /**
     * Sets the goals for the game.
     *
     * @param goals The list of goals.
     * @return The GameBuilder instance.
     */
    public GameBuilder setGoals(List<Goal> goals) {
        this.goals = goals;
        return this;
    }

    /**
     * Builds a new Game object using the provided configurations.
     *
     * @return The constructed Game object.
     */
    public Game build() {
        return new Game(this);
    }
}
