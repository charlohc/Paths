package edu.ntnu.paths.StoryDetails;

import edu.ntnu.paths.Actions.Action;

import java.util.List;

//Should these parameters be public? or else cant reach in Link class
public class LinkBuilder {

    public String text;
    public String reference;
    public List<Action> actions;

    public static LinkBuilder newInstance() {
        return new LinkBuilder();
    }

    private LinkBuilder() {}

    // Setter methods
    public LinkBuilder setText(String text)
    {
        this.text = text;
        return this;
    }
    public LinkBuilder setReference(String reference)
    {
        this.reference = reference;
        return this;
    }
    public LinkBuilder setAddress(List<Action> actions)
    {
        this.actions = actions;
        return this;
    }

    // build method to deal with outer class
    // to return outer instance
    public Link build()
    {
        return new Link(this);
    }
}
