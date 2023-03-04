package edu.ntnu.paths.StoryDetails;

import java.util.List;
import java.util.Objects;

public class Passage {
    private final String tittle;
    private final String content;
    private List<Link> links;

    public Passage(String tittle, String content) {
        if (tittle.isEmpty()) {
            throw new NullPointerException("Tittle cannot be null");
        }
         else if ( content.isEmpty()) {
        throw new NullPointerException("Content cannot be null");
    } else {
            this.tittle = tittle;
            this.content = content;
        }
    }

    public Passage(Passage passageCopy) {
        this(passageCopy.getTittle(), passageCopy.getContent());
    }

    public String getTittle() {
        return tittle;
    }

    public String getContent() {
        return content;
    }

    public List<Link> getLinks() {
        return links;
    }

    /**
     * good practice to use other function?
     * @param link
     * @return
     */
    public boolean addLink(Link link) {
        if (links.contains(link)) return false;
        links.add(link);
        return true;
    }

    public boolean hasLink(Link link) {
        return !links.contains(link);
    }

    @Override
    public String toString() {
        return "Passage{" +
                "tittle='" + tittle + '\'' +
                ", content='" + content + '\'' +
                ", links=" + links +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passage passage = (Passage) o;
        return Objects.equals(tittle, passage.tittle) && Objects.equals(content, passage.content) && Objects.equals(links, passage.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tittle, content, links);
    }
}
