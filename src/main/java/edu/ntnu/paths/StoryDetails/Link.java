package edu.ntnu.paths.StoryDetails;

import edu.ntnu.paths.Actions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public final class Link {
    private final String text;
    private final String reference;
    private final List<Action> actions;

    public Link(LinkBuilder linkBuilder) {
        Pattern p = Pattern.compile("[@#$%&*:()_+=|<>{}\\[\\]\n~]", Pattern.CASE_INSENSITIVE);

        if (linkBuilder.text.isEmpty()) { throw new NullPointerException("Text cannot be null");
        } else if (p.matcher(linkBuilder.text).find()) {
            throw new IllegalArgumentException("link text can not contain special characters!");
        } else if (linkBuilder.reference.isEmpty()) { throw new NullPointerException("Reference cannot be null");
        } else if (p.matcher(linkBuilder.reference).find()) {
            throw new IllegalArgumentException("link reference can not contain special characters!");
        }

            this.text = linkBuilder.text.trim();
            this.reference = linkBuilder.reference.trim();
            this.actions = linkBuilder.actions;

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

    public int getGoldActionsValue() {
        int numberOfGold = 0;
        for (Action action : actions) {
            if (action instanceof GoldAction) {
                numberOfGold += ((GoldAction) action).getGold();
            }
        }
        return numberOfGold;
    }

    public int getHealthActionsValue() {
        int healthValue = 0;
        for (Action action : actions) {
            if (action instanceof HealthAction) {
                healthValue += ((HealthAction) action).getHealth();
            }
        }
        return healthValue;
    }

    public int getScoreActionsValue() {
        int scoreValue = 0;
        for (Action action : actions) {
            if (action instanceof ScoreAction) {
                scoreValue += ((ScoreAction) action).getPoints();
            }
        }
        return scoreValue;
    }

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
     * change to void, and return true or false depending on if already in list or not
     * @param action
     */
    public boolean addAction(Action action) {
        if(actions.contains(action)) return false;
        if(actions.stream().anyMatch(action1 -> action1.getClass().getSimpleName().equals(action.getClass().getSimpleName()))) return false;
        actions.add(action);
        return true;
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



