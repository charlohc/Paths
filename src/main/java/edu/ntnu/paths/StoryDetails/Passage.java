package edu.ntnu.paths.StoryDetails;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The Passage class represents a passage in a story with a title, content, and associated links.
 */
public final class Passage {
    private final String title;
    private final String content;
    private List<Link> links;

    /**
     * Constructs a Passage instance using the provided PassageBuilder.
     *
     * @param passageBuilder The PassageBuilder used to construct the Passage instance.
     */
    public Passage(PassageBuilder passageBuilder) {
        Pattern p = Pattern.compile("[@#$%&*:()_+=|<>{}\\[\\]\n~]", Pattern.CASE_INSENSITIVE);

        if (passageBuilder.title.isEmpty()) {
            throw new NullPointerException("Title cannot be null");
        } else if (p.matcher(passageBuilder.title).find()) {
            throw new IllegalArgumentException("Passage title cannot contain special characters");
        } else if (passageBuilder.content.isEmpty()) {
            throw new NullPointerException("Content cannot be null");
        } else if (p.matcher(passageBuilder.content).find()) {
            throw new IllegalArgumentException("Passage content cannot contain special characters");
        }

        this.title = passageBuilder.title.trim();
        this.content = passageBuilder.content.trim();
        this.links = passageBuilder.links;
    }

    /**
     * Gets the title of the passage.
     *
     * @return The title of the passage.
     */
    public String getTittle() {
        return title;
    }

    /**
     * Gets the content of the passage.
     *
     * @return The content of the passage.
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the list of links associated with the passage.
     *
     * @return The list of links associated with the passage.
     */
    public List<Link> getLinks() {
        return links;
    }

    /**
     * Adds a link to the passage.
     *
     * @param link The link to be added.
     * @return True if the link was added successfully, false otherwise.
     */
    public boolean addLink(Link link) {
        if (links.contains(link)) {
            return false;
        }
        links.add(link);
        return true;
    }

    /**
     * Checks if the passage has a specific link.
     *
     * @param link The link to check.
     * @return True if the passage has the link, false otherwise.
     */
    public boolean hasLink(Link link) {
        return links.contains(link);
    }

    @Override
    public String toString() {
        return "Passage{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", links=" + links +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passage passage = (Passage) o;
        return Objects.equals(title, passage.title) && Objects.equals(content, passage.content) && Objects.equals(links, passage.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, links);
    }
}
