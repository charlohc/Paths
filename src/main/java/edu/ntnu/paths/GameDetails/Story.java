package edu.ntnu.paths.GameDetails;

import java.util.Collection;
import java.util.Map;

public class Story {
    private final String tittle;
    private Map<Link, Passage> passages;
    private final Passage openingPassage;

    public Story(String tittle, Passage openingPassage) {
        this.tittle = tittle;
        this.openingPassage = openingPassage;
    }

    public String getTittle() {
        return tittle;
    }

    public Passage getOpeningPassage() {
        return openingPassage;
    }

    /**
     * create a new constructor for Link, or initialize an empty list
     * @param
     * @return
     */
  public boolean addPassage(Passage passage) {
    if(passages.containsValue(passage)) return false;
    passages.put(new Link(this.tittle, this.tittle), passage);
    return true;
    }

    public Passage getPassage(Link link) {
        return passages.get(link);
    }

    public Collection<Passage> getPassages() {
        return passages.values();
    }
}
