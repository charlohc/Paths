package edu.ntnu.paths.GameDetails;


import edu.ntnu.paths.StoryDetails.Link;
import edu.ntnu.paths.StoryDetails.Passage;
import edu.ntnu.paths.StoryDetails.Story;
import edu.ntnu.paths.Goals.Goal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Game {
    private final Player player;
    private final Story story;
    private final List<Goal> goals;

    public Game(GameBuilder gameBuilder) {
        if (gameBuilder.player == null) { throw new NullPointerException("Player cannot be null");
        }
        else if (gameBuilder.story == null) { throw new NullPointerException("Story cannot be null");
        }
        else {
            this.player = gameBuilder.player;
            this.story = gameBuilder.story;
            this.goals = gameBuilder.goals;
        }
    }

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


    public Player getPlayer() {
        return player;
    }

    public Story getStory() {
        return story;
    }

    public List<Goal> getGoals() {
        return goals;
    }



    public Passage begin() {
        return story.getPassage();
    }

    public Passage go(Link link) {
        return story.getPassage(link);

    }

}
