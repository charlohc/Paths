package edu.ntnu.paths.StoryDetails;

import java.util.*;

public final class Story {
    private final String title;
    private final HashMap<Link, Passage> passages;
    private final Passage passage;

    public Story(StoryBuilder storyBuilder) {
        if (storyBuilder.title.isEmpty()) {
            throw new NullPointerException("tittle cannot be null");
        } else if (storyBuilder.openingPassage == null) {
            throw new NullPointerException("Opening passage cannot be null");
        }
            this.title = storyBuilder.title;
            this.passage = storyBuilder.openingPassage;
            this.passages = (HashMap<Link, Passage>) storyBuilder.passages;

    }

    public Story (Story copyStory) {
        this.title = copyStory.getTittle();
        this.passage = copyStory.getPassage();
        this.passages = copyStory.passages;
    }


    public String getTittle() {
        return title;
    }

    public Passage getPassage() {
        return passage;
    }


  public boolean addPassage(Passage passage) {
    if(passages.containsValue(passage)) return false;
    Link link = LinkBuilder.newInstance()
              .setText(passage.getTittle())
              .setReference(passage.getTittle())
              .build();


      passages.put(link, passage);
      return true;
    }


    //TODO: exception if passages does not contain link
    public Passage getPassage(Link link) {
        for (Map.Entry<Link, Passage> entry : passages.entrySet()) {
           if(entry.getValue().getTittle().equals(link.getReference())) {
               return passages.get(entry.getKey());
           }
        }
        return null;
    }

    public Collection<Passage> getPassages() {
        return passages.values();
    }

    public Boolean removePassage(Link link) {

        if (getPassage(link) == null) return false;

        boolean passageHasLink = passages.values().stream().anyMatch(passage1 -> passage1.hasLink(link));
        if(passageHasLink) return false;

        passages.remove(getPassage(link));
        return true;

    }

    public List<Link> getBrokenLinks() {
        ArrayList<Link> brokenLinks = new ArrayList<>();

        passages.forEach(((link, passage1) -> {
            for (Link link1 : passage1.getLinks()) {
                if (getPassage(link1) == null) brokenLinks.add(link1);
            }
        }));

        return brokenLinks;

    }
}
