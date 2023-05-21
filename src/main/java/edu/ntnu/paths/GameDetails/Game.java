package edu.ntnu.paths.GameDetails;

import edu.ntnu.paths.StoryDetails.Link;
import edu.ntnu.paths.StoryDetails.Passage;
import edu.ntnu.paths.StoryDetails.Story;
import edu.ntnu.paths.Goals.Goal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a game instance.
 */
public class Game {
    private final Player player;
    private final Story story;
    private final List<Goal> goals;

    /**
     * Constructs a Game object using the GameBuilder.
     *
     * @param gameBuilder The GameBuilder object to construct the Game from.
     * @throws NullPointerException if the player or story is null.
     */
    public Game(GameBuilder gameBuilder) {
        if (gameBuilder.player == null) {
            throw new NullPointerException("Player cannot be null");
        } else if (gameBuilder.story == null) {
            throw new NullPointerException("Story cannot be null");
        } else {
            this.player = gameBuilder.player;
            this.story = gameBuilder.story;
            this.goals = gameBuilder.goals;
        }
    }

    /**
     * Constructs a copy of a Game object.
     *
     * @param copyGame The Game object to copy.
     */
    public Game(Game copyGame) {
        this.player = new Player(copyGame.getPlayer());
        this.story = new Story(copyGame.getStory());

        Collection<Goal> goals = copyGame.getGoals();
        if (goals != null) {
            this.goals = new ArrayList<>(goals);
        } else {
            this.goals = null;
        }
    }

    /**
     * Returns the player of the game.
     *
     * @return The player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the story of the game.
     *
     * @return The story object.
     */
    public Story getStory() {
        return story;
    }

    /**
     * Returns the list of goals in the game.
     *
     * @return The list of goals.
     */
    public List<Goal> getGoals() {
        return goals;
    }

    /**
     * Begins the game by returning the initial passage of the story.
     *
     * @return The initial passage of the story.
     */
    public Passage begin() {
        return story.getPassage();
    }

    /**
     * Continues the game by following the provided link and returning the next passage.
     *
     * @param link The link to follow.
     * @return The next passage based on the chosen link.
     */
    public Passage go(Link link) {
        return story.getPassage(link);
    }
}
