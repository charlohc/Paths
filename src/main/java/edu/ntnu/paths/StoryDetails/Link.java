package edu.ntnu.paths.StoryDetails;

import edu.ntnu.paths.Actions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The Link class represents a link within a story, containing text, a reference,
 * and a list of associated actions.
 */
public final class Link {
    private final String text;
    private final String reference;
    private final List<Action> actions;

    /**
     * Constructs a new Link instance using a LinkBuilder.
     *
     * @param linkBuilder The LinkBuilder to build the Link instance.
     * @throws NullPointerException     If the text or reference is null.
     * @throws IllegalArgumentException If the text or reference contains special characters.
     */
    public Link(LinkBuilder linkBuilder) {
        Pattern p = Pattern.compile("[@#$%&*:()_+=|<>{}\\[\\]\n~]", Pattern.CASE_INSENSITIVE);

        if (linkBuilder.text.isEmpty()) {
            throw new NullPointerException("Text cannot be null");
        } else if (p.matcher(linkBuilder.text).find()) {
            throw new IllegalArgumentException("Link text cannot contain special characters!");
        } else if (linkBuilder.reference.isEmpty()) {
            throw new NullPointerException("Reference cannot be null");
        } else if (p.matcher(linkBuilder.reference).find()) {
            throw new IllegalArgumentException("Link reference cannot contain special characters!");
        }

        this.text = linkBuilder.text.trim();
        this.reference = linkBuilder.reference.trim();
        this.actions = linkBuilder.actions;
    }

    /**
     * Retrieves the text of the link.
     *
     * @return The link text.
     */
    public String getText() {
        return text;
    }

    /**
     * Retrieves the reference of the link.
     *
     * @return The link reference.
     */
    public String getReference() {
        return reference;
    }

    /**
     * Retrieves the list of actions associated with the link.
     *
     * @return The list of actions.
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * Calculates the total value of gold actions associated with the link.
     *
     * @return The total value of gold actions.
     */
    public int getGoldActionsValue() {
        int numberOfGold = 0;
        for (Action action : actions) {
            if (action instanceof GoldAction) {
                numberOfGold += ((GoldAction) action).getGold();
            }
        }
        return numberOfGold;
    }

    /**
     * Calculates the total value of health actions associated with the link.
     *
     * @return The total value of health actions.
     */
    public int getHealthActionsValue() {
        int healthValue = 0;
        for (Action action : actions) {
            if (action instanceof HealthAction) {
                healthValue += ((HealthAction) action).getHealth();
            }
        }
        return healthValue;
    }

    /**
     * Calculates the total value of score actions associated with the link.
     *
     * @return The total value of score actions.
     */
    public int getScoreActionsValue() {
        int scoreValue = 0;
        for (Action action : actions) {
            if (action instanceof ScoreAction) {
                scoreValue += ((ScoreAction) action).getPoints();
            }
        }
        return scoreValue;
    }

    /**
     * Retrieves the list of items in the inventory actions associated with the link.
     *
     * @return The list of inventory items.
     */
    public ArrayList<String> getInventory() {
        ArrayList<String> inventoryList = new ArrayList<>();
        for (Action action : actions) {
            if (action instanceof InventoryAction) {
                inventoryList.add(((InventoryAction) action).getItem());
            }
        }
        return inventoryList;
    }

    /**
     * Adds an action to the list of actions associated with the link.
     *
     * @param action The action to be added.
     * @return true if the action is successfully added, false otherwise.
     */
    public boolean addAction(Action action) {
        if (actions.contains(action)) {
            return false;
        }
        if (actions.stream().anyMatch(action1 -> action1.getClass().getSimpleName().equals(action.getClass().getSimpleName()))) {
            return false;
        }
        actions.add(action);
        return true;
    }

    /**
     * Returns a string representation of the Link object.
     *
     * @return A string representation of the Link object.
     */
    @Override
    public String toString() {
        return "Link{" +
                "text='" + text + '\'' +
                ", reference='" + reference + '\'' +
                ", actions=" + actions +
                '}';
    }

    /**
     * Checks if the Link object is equal to another object.
     *
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(text, link.text) && Objects.equals(reference, link.reference) && Objects.equals(actions, link.actions);
    }

    /**
     * Returns the hash code value for the Link object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(text, reference, actions);
    }
}
