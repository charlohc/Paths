package edu.ntnu.paths.StoryDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public final class Passage {
    private final String tittle;
    private final String content;
    private List<Link> links;

    public Passage(PassageBuilder passageBuilder) {
        Pattern p = Pattern.compile("[@#$%&*:()_+=|<>{}\\[\\]\n~]", Pattern.CASE_INSENSITIVE);


        if (passageBuilder.title.isEmpty()) {
            throw new NullPointerException("Tittle cannot be null");
        } else if (p.matcher(passageBuilder.title).find()) {
            throw new IllegalArgumentException("Passage tittle can not contain special characters");
        } else if (passageBuilder.content.isEmpty()) {
        throw new NullPointerException("Content cannot be null");
        } else if (p.matcher(passageBuilder.content).find()) {
            throw new IllegalArgumentException("Passage content can not contain special characters");
        }else {
            this.tittle = passageBuilder.title;
            this.content = passageBuilder.content;
            this.links = passageBuilder.links;
        }
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
        return links.contains(link);
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
