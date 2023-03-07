package edu.ntnu.paths.StoryDetails;

import java.util.HashMap;
import java.util.Map;

public class StoryBuilder {

    public String title;
    public Passage openingPassage;
    public Map<Link, Passage> passages = new HashMap<>();

    public static StoryBuilder newInstance() {
        return new StoryBuilder();
    }

    private StoryBuilder() {}

    // Setter methods
    public StoryBuilder setTitle(String title)
    {
        this.title = title;
        return this;
    }
    public StoryBuilder setOpeningPassage(Passage openingPassage)
    {
        this.openingPassage = openingPassage;
        return this;
    }
    public StoryBuilder setPassages(HashMap<Link,Passage> passages)
    {
        this.passages = passages;
        return this;
    }

    // build method to deal with outer class
    // to return outer instance
    public Story build()
    {
        return new Story(this);
    }
}
