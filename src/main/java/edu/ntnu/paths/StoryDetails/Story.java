package edu.ntnu.paths.StoryDetails;

import java.util.*;
import java.util.function.Function;

/**
 * The Story class represents a story with passages and links between them.
 */
public final class Story {
    private final String title;
    private final HashMap<Link, Passage> passages;
    private final Passage passage;

    /**
     * Constructs a Story object using the provided StoryBuilder.
     *
     * @param storyBuilder The StoryBuilder instance used to build the Story object.
     */
    public Story(StoryBuilder storyBuilder) {
        if (storyBuilder.title.isEmpty()) {
            throw new NullPointerException("title cannot be null");
        } else if (storyBuilder.openingPassage == null) {
            throw new NullPointerException("Opening passage cannot be null");
        }
        this.title = storyBuilder.title.trim();
        this.passage = storyBuilder.openingPassage;
        this.passages = (HashMap<Link, Passage>) storyBuilder.passages;
    }

    /**
     * Constructs a copy of a Story object.
     *
     * @param copyStory The Story object to be copied.
     */
    public Story(Story copyStory) {
        this.title = copyStory.getTittle();
        this.passage = copyStory.getPassage();
        this.passages = copyStory.passages;
    }

    /**
     * Retrieves the title of the story.
     *
     * @return The title of the story.
     */
    public String getTittle() {
        return title;
    }

    /**
     * Retrieves the opening passage of the story.
     *
     * @return The opening passage of the story.
     */
    public Passage getPassage() {
        return passage;
    }

    /**
     * Adds a new passage to the story.
     *
     * @param passage The passage to be added.
     * @return True if the passage is added successfully, false otherwise.
     */
    public boolean addPassage(Passage passage) {
        if (passages.containsValue(passage)) return false;
        Link link = LinkBuilder.newInstance()
                .setText(passage.getTittle())
                .setReference(passage.getTittle())
                .build();

        passages.put(link, passage);
        return true;
    }

    /**
     * Retrieves the passage associated with the given link.
     *
     * @param link The link to retrieve the passage for.
     * @return The passage associated with the link, or null if not found.
     */
    public Passage getPassage(Link link) {
        for (Map.Entry<Link, Passage> entry : passages.entrySet()) {
            if (entry.getValue().getTittle().equalsIgnoreCase(link.getReference())) {
                return passages.get(entry.getKey());
            }
        }
        return null;
    }

    /**
     * Retrieves all passages in the story.
     *
     * @return A collection of all passages in the story.
     */
    public Collection<Passage> getPassages() {
        return passages.values();
    }

    /**
     * Removes a passage from the story based on the given link.
     *
     * @param link The link associated with the passage to be removed.
     * @return True if the passage is removed successfully, false otherwise.
     */
    public Boolean removePassage(Link link) {
        if (getPassage(link) == null) return false;

        boolean passageHasLink = passages.values().stream().anyMatch(passage1 -> passage1.hasLink(link));
        if (passageHasLink) return false;

        passages.remove(getPassage(link));
        return true;
    }

    /**
     * Retrieves a list of broken links in the story. Broken links are links that refer to passages that does not exist.
     *
     * @return A list of broken links in the story.
     */
    public List<Link> getBrokenLinks() {
        ArrayList<Link> brokenLinks = new ArrayList<>();

        passages.forEach(((link, passage1) -> {
            for (Link link1 : passage1.getLinks()) {
                if (getPassage(link1) == null) brokenLinks.add(link1);
            }
        }));

        return brokenLinks;
    }

    /**
     * Retrieves a list of all inventory items mentioned in the story.
     *
     * @return A list of all inventory items mentioned in the story.
     */
    public ArrayList<String> getAllInventoryItems() {
        ArrayList<String> allInventoryItemsList = new ArrayList<>();
        for (Passage passage : getPassages()) {
            for (Link linkInPassage : passage.getLinks()) {
                for (String inventory : linkInPassage.getInventory()) {
                    if (!allInventoryItemsList.contains(inventory)) {
                        allInventoryItemsList.add(inventory);
                    }
                }
            }
        }
        return allInventoryItemsList;
    }

    /**
     * Finds the maximum gold value among all links in the story.
     *
     * @return The maximum gold value.
     */
    public int findMaxGold() {
        return findMaxValue(Link::getGoldActionsValue);
    }

    /**
     * Finds the maximum score value among all links in the story.
     *
     * @return The maximum score value.
     */
    public int findMaxScore() {
        return findMaxValue(Link::getScoreActionsValue);
    }

    /**
     * Finds the maximum value based on a specified value function. Based on the Bellman-Ford algorithm.
     *
     * @param valueFunction The value function used to calculate the value for each link.
     * @return The maximum value based on the specified value function.
     */
    public int findMaxValue(Function<Link, Integer> valueFunction) {
        Map<Passage, Integer> distances = new HashMap<>();
        Passage startingPassage = this.getPassage();

        for (Passage passage : passages.values()) {
            distances.put(passage, Integer.MIN_VALUE);
        }
        distances.put(startingPassage, 0);

        boolean updated;
        for (int i = 0; i < passages.size(); i++) {
            updated = false;
            for (Passage passage : passages.values()) {
                int currDist = distances.get(passage);
                if (currDist == Integer.MIN_VALUE) continue;
                for (Link link : passage.getLinks()) {
                    Passage next = getPassage(link);
                    int value = valueFunction.apply(link);
                    if (next == null) continue;
                    int nextDist = distances.get(next);
                    if (currDist + value > nextDist) {
                        distances.put(next, currDist + value);
                        updated = true;
                    }
                }
            }
            if (!updated) break;
        }

        // Find the highest amount of value
        int maxValue = Integer.MIN_VALUE;
        for (Passage passage : passages.values()) {
            if (distances.get(passage) > maxValue) {
                maxValue = distances.get(passage);
            }
        }
        return maxValue;
    }

    /**
     * Retrieves the content of all passages in the story.
     *
     * @return The content of all passages in the story.
     */
    public String passagesContent() {
        StringBuilder passageContent = new StringBuilder();

        passages.values().forEach(passage1 -> {
            if (!passage1.equals(passage)) {
                passageContent.append("::").append(passage1.getTittle()).append("\n")
                        .append(passage1.getContent()).append("\n");

                passage1.getLinks().forEach(link -> {
                    passageContent.append("[").append(link.getText()).append("]")
                            .append("(").append(link.getReference()).append(")");

                    link.getActions().forEach(action -> {
                        passageContent.append("{").append(action.toString()).append("}");
                    });
                });
                passageContent.append("\n\n");
            }
        });

        return passageContent.toString();
    }

    /**
     * Retrieves the links in the opening passage of the story.
     *
     * @return The links in the opening passage.
     */
    public String openingPassageLinks() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < passage.getLinks().size(); i++) {
            sb.append("[").append(passage.getLinks().get(i).getText()).append("]")
                    .append("(").append(passage.getLinks().get(i).getReference()).append(")");

            for (int j = 0; j < passage.getLinks().get(i).getActions().size(); j++) {
                sb.append("{").append(passage.getLinks().get(i).getActions().get(j)).append("}");
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return title + "\n" +
                "\n" + "::" + passage.getTittle() +
                "\n" + passage.getContent() + "\n" +
                openingPassageLinks() + "\n" +
                passagesContent();
    }
}
