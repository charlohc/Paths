package edu.ntnu.paths.Managers;

import edu.ntnu.paths.StoryDetails.Story;

/**
 *  The StoryManager class is responsible for managing the story instance.
 */
    public class StoryManager {
        private static StoryManager instance;
        private Story story;

        private StoryManager() {}

    /**
     * Retrieves the instance of StoryManager.
     *
     * @return The StoryManager instance.
     */
        public static StoryManager getInstance() {
            if (instance == null) {
                instance = new StoryManager();
            }
            return instance;
        }

    /**
     * Retrieves the current story.
     *
     * @return The current story.
     */
        public Story getStory() {
            return story;
        }

    /**
     * Sets the current story.
     *
     * @param story The story to be set as the current story.
     */
        public void setStory(Story story) {
            this.story = story;
        }
    }

