package edu.ntnu.paths.StoryDetails;

import java.util.ArrayList;
import java.util.List;

public class PassageBuilder {
    public String title;
    public String content;
    public List<Link> links = new ArrayList<>();

    public static PassageBuilder newInstance() {
        return new PassageBuilder();
    }

    private PassageBuilder() {}

    // Setter methods
    public PassageBuilder setTitle(String title)
    {
        this.title = title;
        return this;
    }
    public PassageBuilder setContent(String content)
    {
        this.content = content;
        return this;
    }
    public PassageBuilder setLinks(List<Link> links)
    {
        this.links = links;
        return this;
    }

    // build method to deal with outer class
    // to return outer instance
    public Passage build()
    {
        return new Passage(this);
    }
}
