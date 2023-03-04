package edu.ntnu.paths.GameDetails;


import edu.ntnu.paths.StoryDetails.LinkBuilder;
import edu.ntnu.paths.StoryDetails.Passage;
import edu.ntnu.paths.StoryDetails.Story;
import edu.ntnu.paths.Goals.Goal;

import java.util.List;

//Add exceptions
//factory links og passage
//deep copy alt med lister

public class Game {
    private final Player player;
    private final Story story;
    private final List<Goal> goals;

    protected Game(Player player, Story story, List<Goal> goals) {
        if (player == null) { throw new NullPointerException("Player cannot be null");
        }
        else if (story == null) { throw new NullPointerException("Story cannot be null");
        }
        else {
            this.player = player;
            this.story = story;
            this.goals = goals;
        }
    }

    public Game(Game copy) {
        this(copy.player, copy.story,copy.goals);
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
        return story.getOpeningPassage();
    }

    public Passage go(LinkBuilder link) {
        return story.getPassage(link.build());
    }
}
