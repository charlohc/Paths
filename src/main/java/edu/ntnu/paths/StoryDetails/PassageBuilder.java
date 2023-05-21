package edu.ntnu.paths.StoryDetails;


import java.util.ArrayList;
import java.util.List;

/**
 * The PassageBuilder class is responsible for building Passage objects.
 */
public class PassageBuilder {
    public String title;
    public String content;
    public List<Link> links = new ArrayList<>();

    /**
     * Creates a new instance of PassageBuilder.
     *
     * @return The new PassageBuilder instance.
     */
    public static PassageBuilder newInstance() {
        return new PassageBuilder();
    }

    private PassageBuilder() {}

    /**
     * Sets the title of the passage.
     *
     * @param title The title of the passage.
     * @return The PassageBuilder instance.
     */
    public PassageBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the content of the passage.
     *
     * @param content The content of the passage.
     * @return The PassageBuilder instance.
     */
    public PassageBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * Sets the links of the passage.
     *
     * @param links The links of the passage.
     * @return The PassageBuilder instance.
     */
    public PassageBuilder setLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    /**
     * Builds a Passage instance using the current state of the builder.
     *
     * @return The built Passage instance.
     */
    public Passage build() {
        return new Passage(this);
    }
}
