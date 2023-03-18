package edu.ntnu.paths.StoryDetails;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class StoryTest {
    Story story, copyStory;
    Passage openingPassage, porchPassage;

    Link linkToPorchPassage;

    @BeforeEach
    void setUp() {

        openingPassage = PassageBuilder.newInstance()
                .setTitle("Beginning")
                .setContent("You woke up and felt at once all alone")
                .build();

        porchPassage = PassageBuilder.newInstance()
                .setTitle("The porch")
                .setContent("There are no people outside, and its all so quite")
                .build();

        story = StoryBuilder.newInstance()
                .setTitle("The last person on earth")
                .setOpeningPassage(openingPassage)
                .build();

        story.addPassage(openingPassage);
        story.addPassage(porchPassage);

        linkToPorchPassage = LinkBuilder.newInstance()
                .setText("Go outside")
                .setReference("The porch")
                .build();

        openingPassage.addLink(linkToPorchPassage);

        copyStory = new Story(story);

    }

    @Nested
    @DisplayName("Testing story object constructor with invalid fields")
    class creatingInvalidStoryObject {

        @Test
        void storyWithoutTitle() {
            assertThrows(NullPointerException.class, () -> {
                Story storyWithoutTittle = StoryBuilder.newInstance()
                        .setTitle("")
                        .setOpeningPassage(openingPassage)
                        .build();
            });
        }

        @Test
        void playerWithoutOpeningPassage() {
            assertThrows(NullPointerException.class, () -> {
                Story storyWithoutTittle = StoryBuilder.newInstance()
                        .setTitle("The last person on earth")
                        .setOpeningPassage(null)
                        .build();
            });
        }
    }

    @Nested
    @DisplayName("Testing the security of the story object")
    class securePlayerObject {

        @Test
        @DisplayName("Test that the original player object and copy object get different addresses")
        void differentObjectDifferentAddress() {
            Assertions.assertNotEquals(copyStory,story);
        }

        @Test
        @DisplayName("Test that the original player object and copy object contains the same data, for instance tittle")
        void differentObjectSameData() {
            Assertions.assertEquals(copyStory.getTittle(), story.getTittle());
        }
    }

    @Nested
    @DisplayName("Testing the get methods of the story object")
    class validGetMethods {
        @Test
        void getTittle() {
            Assertions.assertEquals("The last person on earth", story.getTittle());
        }

        @Test
        void getPassage() {
            Assertions.assertEquals(openingPassage, story.getPassage());
        }

        @Test
        void getPassageLink() {
            Assertions.assertEquals(porchPassage,story.getPassage(linkToPorchPassage));

        }

        @Test
        void getPassageFalse() {
           Link notAConnectedLink = LinkBuilder.newInstance()
                   .setReference("reference")
                   .setText("text")
                   .build();

           Assertions.assertNull(story.getPassage(notAConnectedLink));
        }

        @Test
        void getPassages() {
            Collection<Passage> listOfPassages = new ArrayList<>();
            listOfPassages.add(porchPassage);
            listOfPassages.add(openingPassage);
            Assertions.assertEquals(listOfPassages.toString(),story.getPassages().toString());

        }
    }

    @Test
    void addPassage() {
        Passage addedPassage = PassageBuilder.newInstance()
                        .setTitle("title")
                        .setContent("content")
                        .build();

        story.addPassage(addedPassage);
        Assertions.assertTrue(story.getPassages().contains(addedPassage));
    }

    //fjerner passasjen hvis det ikke er noen linker til den og den eksisterer

    @Nested
    @DisplayName("Testing the removePassage function with valid and invalid data")
    class removePassageMethod {
        @Test
        void removePassageWithLinksToPassage() {
            Assertions.assertFalse(story.removePassage(linkToPorchPassage));
        }


        @Test
        void removePassageThatDoesNotExist() {
            Link linkToAPassageThatDoesNotExist = LinkBuilder.newInstance()
                    .setText("text")
                    .setReference("reference")
                    .build();

            openingPassage.addLink(linkToAPassageThatDoesNotExist);

            Assertions.assertFalse(story.removePassage(linkToAPassageThatDoesNotExist));
        }

        @Test
        @DisplayName("Removes the passage, which has a link that refers to it, but no passage owns the link")
        void removePassageValid() {
            Passage functioningPassageWithLink = PassageBuilder.newInstance()
                    .setTitle("title of the functioning passage")
                    .setContent("content")
                    .build();

            story.addPassage(functioningPassageWithLink);

            Link linkToTheFunctioningPassageWithLink = LinkBuilder.newInstance()
                    .setText("text about link")
                    .setReference("title of the functioning passage")
                    .build();

            Assertions.assertTrue(story.removePassage(linkToTheFunctioningPassageWithLink));

        }
    }

    @Nested
    @DisplayName("Testing the brokenLinks method")
    class brokenLinksMethod {

        @Test
        void brokenLinkTestWithOnlyLinksConnectedToPassages(){
            Assertions.assertEquals(0,story.getBrokenLinks().size());
        }

        @Test
        void brokenLinkTestWithOneLinkNotConnectedToAPassage() {
            Link linkToAPassageThatDoesNotExist = LinkBuilder.newInstance()
                    .setText("text")
                    .setReference("reference to a passage that does not exist")
                    .build();

            story.getPassage().addLink(linkToAPassageThatDoesNotExist);

            Assertions.assertEquals(1, story.getBrokenLinks().size());
        }


    }
}