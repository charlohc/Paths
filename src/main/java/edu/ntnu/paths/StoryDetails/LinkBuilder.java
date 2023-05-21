package edu.ntnu.paths.StoryDetails;

import edu.ntnu.paths.Actions.Action;
import java.util.ArrayList;
import java.util.List;

/**
 * The LinkBuilder class is a builder class used for constructing instances of the Link class.
 */
public class LinkBuilder {

    public String text;
    public String reference;
    public List<Action> actions = new ArrayList<>();

    /**
     * Creates a new instance of LinkBuilder.
     *
     * @return A new instance of LinkBuilder.
     */
    public static LinkBuilder newInstance() {
        return new LinkBuilder();
    }

    private LinkBuilder() {}

    /**
     * Sets the text of the link.
     *
     * @param text The text of the link.
     * @return The LinkBuilder instance.
     */
    public LinkBuilder setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Sets the reference of the link.
     *
     * @param reference The reference of the link.
     * @return The LinkBuilder instance.
     */
    public LinkBuilder setReference(String reference) {
        this.reference = reference;
        return this;
    }

    /**
     * Sets the actions associated with the link.
     *
     * @param actions The actions associated with the link.
     * @return The LinkBuilder instance.
     */
    public LinkBuilder setActions(List<Action> actions) {
        this.actions = actions;
        return this;
    }

    /**
     * Builds a Link instance using the current state of the LinkBuilder.
     *
     * @return A new instance of the Link class.
     */
    public Link build() {
        return new Link(this);
    }
}
