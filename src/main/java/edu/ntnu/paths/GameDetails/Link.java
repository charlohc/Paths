package edu.ntnu.paths.GameDetails;

import edu.ntnu.paths.Actions.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Link {
    private final String text;
    private final String reference;
    private final List<Action> actions;

    public Link(String text, String reference, List<Action> actions) {
        this.text = text;
        this.reference = reference;
        this.actions = actions;
    }

    public Link(String text, String reference) {
        this.text = text;
        this.reference = reference;
        this.actions = new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public String getReference() {
        return reference;
    }

    public List<Action> getActions() {
        return actions;
    }

    /**
     * change to void, and return true or false depending on if already in list or not
     * @param action
     */
    public void addAction(Action action) {
        actions.add(action);
    }

    @Override
    public String toString() {
        return "Link{" +
                "text='" + text + '\'' +
                ", reference='" + reference + '\'' +
                ", actions=" + actions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(text, link.text) && Objects.equals(reference, link.reference) && Objects.equals(actions, link.actions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, reference, actions);
    }
}
