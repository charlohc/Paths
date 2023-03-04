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

    public Story(Story storyCopy) {
        this(storyCopy.getTittle(), storyCopy.getOpeningPassage());
    }

    public String getTittle() {
        return tittle;
    }

    public Passage getOpeningPassage() {
        return openingPassage;
    }


  public boolean addPassage(Passage passage) {
    if(passages.containsValue(passage)) return false;
    Link link = LinkBuilder.newInstance()
              .setText(this.tittle)
              .setReference(this.tittle)
              .build();

      passages.put(link, passage);
      return true;
    }

    public Passage getPassage(Link link) {
        return passages.get(link);
    }

    public Collection<Passage> getPassages() {
        return passages.values();
    }
}
