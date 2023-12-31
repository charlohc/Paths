package edu.ntnu.paths.StoryDetails;

import edu.ntnu.paths.Actions.GoldAction;
import edu.ntnu.paths.Actions.HealthAction;
import edu.ntnu.paths.Actions.InventoryAction;
import edu.ntnu.paths.Actions.ScoreAction;
import edu.ntnu.paths.Exceptions.StoryExistException;
import edu.ntnu.paths.FileHandling.WriteFile;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoryTest {
    Story story, copyStory;

    GoldAction goldActionIncrease10 = new GoldAction();

    GoldAction goldActionIncrease20 = new GoldAction();

    ScoreAction scoreActionIncrease10 = new ScoreAction();
    ScoreAction scoreActionIncrease40 = new ScoreAction();


    InventoryAction inventoryActionSword = new InventoryAction();
    HealthAction healthActionIncrease10 = new HealthAction();
    Passage openingPassage, porchPassage, kitchenPassage;

    Link linkToPorchPassage, linkToKitchenPassage;

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

        kitchenPassage = PassageBuilder.newInstance()
                .setTitle("The Kitchen")
                .setContent("The kitchen has a wierd smell, and on the counter lies a letter")
                .build();

        story = StoryBuilder.newInstance()
                .setTitle("The last person on earth")
                .setOpeningPassage(openingPassage)
                .build();

        story.addPassage(openingPassage);

        story.addPassage(porchPassage);

        story.addPassage(kitchenPassage);


        linkToPorchPassage = LinkBuilder.newInstance()
                        .setText("Go outside")
                        .setReference("The porch")
                        .build();

        linkToKitchenPassage = LinkBuilder.newInstance()
                        .setText("Go to the kitchen")
                        .setReference("The kitchen")
                        .build();

        openingPassage.addLink(linkToPorchPassage);
        openingPassage.addLink(linkToKitchenPassage);

        porchPassage.addLink(linkToKitchenPassage);

        goldActionIncrease10.goldAction(10);
        healthActionIncrease10.healthAction(10);
        scoreActionIncrease10.scoreAction(10);
        inventoryActionSword.inventoryAction("Sword");

        goldActionIncrease20.goldAction(20);
        scoreActionIncrease40.scoreAction(40);

        linkToPorchPassage.addAction(goldActionIncrease10);
        linkToPorchPassage.addAction(healthActionIncrease10);
        linkToPorchPassage.addAction(scoreActionIncrease10);
        linkToPorchPassage.addAction(inventoryActionSword);
        linkToPorchPassage.addAction(goldActionIncrease10);

        linkToKitchenPassage.addAction(goldActionIncrease20);
        linkToKitchenPassage.addAction(scoreActionIncrease40);

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
            assertNotEquals(copyStory,story);
        }

        @Test
        @DisplayName("Test that the original player object and copy object contains the same data, for instance tittle")
        void differentObjectSameData() {
            assertEquals(copyStory.getTittle(), story.getTittle());
        }
    }

    @Nested
    @DisplayName("Testing the get methods of the story object")
    class validGetMethods {
        @Test
        void getTittle() {
            assertEquals("The last person on earth", story.getTittle());
        }

        @Test
        void getPassage() {
            assertEquals(openingPassage, story.getPassage());
        }

        @Test
        void getPassageLink() {
            assertEquals(porchPassage,story.getPassage(linkToPorchPassage));

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
            listOfPassages.add(kitchenPassage);
            assertEquals(listOfPassages.toString(),story.getPassages().toString());

        }
    }

    @Test
    void addPassage() {
        Passage addedPassage = PassageBuilder.newInstance()
                        .setTitle("title")
                        .setContent("content")
                        .build();

        story.addPassage(addedPassage);
        assertTrue(story.getPassages().contains(addedPassage));
    }

    @Nested
    @DisplayName("Testing the removePassage function with valid and invalid data")
    class removePassageMethod {
        @Test
        void removePassageWithLinksToPassage() {
            assertFalse(story.removePassage(linkToPorchPassage));
        }


        @Test
        void removePassageThatDoesNotExist() {
            Link linkToAPassageThatDoesNotExist = LinkBuilder.newInstance()
                    .setText("text")
                    .setReference("reference")
                    .build();

            openingPassage.addLink(linkToAPassageThatDoesNotExist);

            assertFalse(story.removePassage(linkToAPassageThatDoesNotExist));
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

            assertTrue(story.removePassage(linkToTheFunctioningPassageWithLink));

        }
    }

    @Nested
    @DisplayName("Testing the brokenLinks method")
    class brokenLinksMethod {

        @Test
        void brokenLinkTestWithOnlyLinksConnectedToPassages(){
            assertEquals(0,story.getBrokenLinks().size());
        }

        @Test
        void brokenLinkTestWithOneLinkNotConnectedToAPassage() {
            Link linkToAPassageThatDoesNotExist = LinkBuilder.newInstance()
                    .setText("text")
                    .setReference("reference to a passage that does not exist")
                    .build();

            story.getPassage().addLink(linkToAPassageThatDoesNotExist);

            assertEquals(1, story.getBrokenLinks().size());
        }

    }

    @Nested
    @DisplayName("Testing the get total actions methods")
    class getTotalActions {

        @Test
        void getTotalGoldBestPath() {
           Assertions.assertEquals(30, story.findMaxGold());
        }

        @Test
        void getTotalScoreBestPath() {
            Assertions.assertEquals(50, story.findMaxScore());
        }

        @Test
        void getAllInventoryItems() {
           Assertions.assertEquals("Sword", story.getAllInventoryItems().toString().replace("[","").replace("]",""));
        }

    }
}