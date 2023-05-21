package edu.ntnu.paths.StoryDetails;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder class for constructing instances of the Story class.
 */
public class StoryBuilder {

    public String title;
    public Passage openingPassage;
    public Map<Link, Passage> passages = new HashMap<>();

    /**
     * Creates a new instance of StoryBuilder.
     *
     * @return the new instance of StoryBuilder
     */
    public static StoryBuilder newInstance() {
        return new StoryBuilder();
    }

    private StoryBuilder() {}

    /**
     * Sets the title of the story.
     *
     * @param title the title of the story
     * @return the StoryBuilder instance
     */
    public StoryBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the opening passage of the story.
     *
     * @param openingPassage the opening passage of the story
     * @return the StoryBuilder instance
     */
    public StoryBuilder setOpeningPassage(Passage openingPassage) {
        this.openingPassage = openingPassage;
        return this;
    }

    /**
     * Sets the passages of the story.
     *
     * @param passages the passages of the story
     * @return the StoryBuilder instance
     */
    public StoryBuilder setPassages(Map<Link, Passage> passages) {
        this.passages = passages;
        return this;
    }

    /**
     * Builds and returns an instance of the Story class based on the set properties.
     *
     * @return an instance of the Story class
     */
    public Story build() {
        return new Story(this);
    }
}
