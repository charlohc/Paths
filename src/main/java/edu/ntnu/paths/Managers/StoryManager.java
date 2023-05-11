package edu.ntnu.paths.Managers;

import edu.ntnu.paths.StoryDetails.Story;


    public class StoryManager {
        private static StoryManager instance;
        private Story story;

        private StoryManager() {}

        public static StoryManager getInstance() {
            if (instance == null) {
                instance = new StoryManager();
            }
            return instance;
        }

        public Story getStory() {
            return story;
        }


        public void setStory(Story story) {
            this.story = story;
        }
    }

