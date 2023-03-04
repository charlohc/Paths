package edu.ntnu.paths.GameDetails;


import edu.ntnu.paths.Goals.Goal;

import java.util.List;

//Add exceptions
//factory links og passage
//deep copy alt med lister

public class Game {
    private  Player player;
    private  Story story;
    private  List<Goal> goals;

    public Game(Player player, Story story, List<Goal> goals) {
        this.player = player;
        this.story = story;
        this.goals = goals;
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

    public Passage go(Link link) {
        return story.getPassage(link);
    }
}
