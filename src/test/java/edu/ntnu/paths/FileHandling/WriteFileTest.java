package edu.ntnu.paths.FileHandling;

import edu.ntnu.paths.Actions.*;
import edu.ntnu.paths.Exceptions.InvalidFileDataException;
import edu.ntnu.paths.Exceptions.StoryExistException;
import edu.ntnu.paths.StoryDetails.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertThrows;

class WriteFileTest {

    Story story, copyStoryWriteToFile;

    GoldAction goldActionIncrease10 = new GoldAction();

    ScoreAction scoreActionIncrease10 = new ScoreAction();

    InventoryAction inventoryActionSword = new InventoryAction();
    HealthAction healthActionIncrease10 = new HealthAction();
    Passage openingPassage, porchPassage, kitchenPassage;

    Link linkToPorchPassage, linkToKitchenPassage;

    WriteFile writeFile = new WriteFile();

    File storyFromFile;

    String[] storyContentArray;

    String storyInfoString = "";

    String filePath = "";

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


        linkToPorchPassage.addAction(goldActionIncrease10);
        linkToPorchPassage.addAction(healthActionIncrease10);
        linkToPorchPassage.addAction(scoreActionIncrease10);
        linkToPorchPassage.addAction(inventoryActionSword);
        linkToPorchPassage.addAction(goldActionIncrease10);

        linkToKitchenPassage.addAction(scoreActionIncrease10);

        copyStoryWriteToFile = new Story(story);

        try {
            writeFile.writeStoryToFile(copyStoryWriteToFile);
        } catch (StoryExistException e) {
            throw new RuntimeException(e);
        }

        String fileName = story.getTittle();

         filePath = System.getProperty("user.dir") + System.getProperty("file.separator")
                + "src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator")
                + "java" + System.getProperty("file.separator")  + "edu" + System.getProperty("file.separator") +
                "ntnu" + System.getProperty("file.separator") + "paths" + System.getProperty("file.separator")  + "FileHandling"
                +  System.getProperty("file.separator") + "StoryFiles" + System.getProperty("file.separator") + fileName + ".paths";

         storyFromFile = new File(filePath);


        try {
            Scanner myReader = new Scanner(storyFromFile);
            StringBuilder storyInfoFromFile = new StringBuilder();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                storyInfoFromFile.append(data).append("\n");
                 storyInfoString = storyInfoFromFile.toString();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        storyContentArray = storyInfoString.split("\n");

}

    @AfterEach
    void tearDown() {
      storyFromFile.delete();
    }
    @Nested
    @DisplayName("Tests file properties")
    class testFileProperties {

        @Test
        void fileExist() {
            Assertions.assertTrue(storyFromFile.exists());
        }

        @Test
        void fileNotEmpty() {
            Assertions.assertNotEquals(0, storyFromFile.length());
        }

        @Test
        void fileAtCorrectPlace() {
            Assertions.assertEquals(filePath, storyFromFile.getAbsolutePath());
        }

        @Test
        void correctFileName() {
            Assertions.assertEquals(story.getTittle() + ".paths", storyFromFile.getName());
        }
    }

    @Nested
    @DisplayName("Test for exception handling when faults in story object or setup")
    class testExceptions {
        @Test
        void writeSameStoryTwice() {
            assertThrows(StoryExistException.class, () -> {
                writeFile.writeStoryToFile(copyStoryWriteToFile);
            });
        }
    }

    @Test
    void correctNameOfStory() {
        Assertions.assertEquals(story.getTittle(), storyContentArray[0]);
    }

    @Nested
    @DisplayName("Tests that the opening passages gets correctly written to file")
    class testOpeningPassage {
        @Test
        void firstPassageIsOpeningPassage() {
            Assertions.assertEquals("::Beginning", storyContentArray[2]);
        }

        @Test
        void correctNameOfOpeningPassage() {
            Assertions.assertEquals(story.getPassage().getTittle(), storyContentArray[2].replace("::", ""));
        }

        @Test
        void correctContentOpeningPassage() {
            Assertions.assertEquals(story.getPassage().getContent(), storyContentArray[3]);
        }

        @Test
        void correctTextFirstLinkOpeningPassage() {
            String[] linkOpeningPassage = storyContentArray[4].split("[{}()]");
            Assertions.assertEquals(story.getPassage().getLinks().get(0).getText(), linkOpeningPassage[0].replace("[", "").replace("]", ""));
        }

        @Test
        void correctReferenceFirstLinkOpeningPassage() {
            String[] linkOpeningPassage = storyContentArray[4].split("[{}()]");
            Assertions.assertEquals(story.getPassage().getLinks().get(0).getReference(), linkOpeningPassage[1].replace("(", "").replace(")", ""));
        }

        @Test
        void correctActionsFirstLinkOpeningPassage() {
            String[] linkOpeningPassage = storyContentArray[4].split("[{}()]");
            String[] linkOpeningPassageWithoutBlank = Arrays.stream(linkOpeningPassage).filter(x -> !x.isEmpty()).toArray(String[]::new);
            ArrayList<Action> actionsFirstLinkOpeningPassage = (ArrayList<Action>) story.getPassage().getLinks().get(0).getActions();


            Assertions.assertTrue(actionsFirstLinkOpeningPassage.get(0).toString().equals(linkOpeningPassageWithoutBlank[2]) &&
                    actionsFirstLinkOpeningPassage.get(1).toString().equals(linkOpeningPassageWithoutBlank[3]) &&
                    actionsFirstLinkOpeningPassage.get(2).toString().equals(linkOpeningPassageWithoutBlank[4]));
        }

        @Test
        void correctTextSecondLinkOpeningPassage() {
            String[] linkOpeningPassage = storyContentArray[5].split("[{}()]");
            Assertions.assertEquals(story.getPassage().getLinks().get(1).getText(), linkOpeningPassage[0].replace("[", "").replace("]", ""));
        }

        @Test
        void correctReferenceSecondLinkOpeningPassage() {
            String[] linkOpeningPassage = storyContentArray[5].split("[{}()]");
            Assertions.assertEquals(story.getPassage().getLinks().get(1).getReference(), linkOpeningPassage[1].replace("(", "").replace(")", ""));
        }

        @Test
        void correctActionsSecondLinkOpeningPassage() {
            String[] linkOpeningPassage = storyContentArray[5].split("[{}()]");
            String[] linkOpeningPassageWithoutBlank = Arrays.stream(linkOpeningPassage).filter(x -> !x.isEmpty()).toArray(String[]::new);

            Assertions.assertEquals(story.getPassage().getLinks().get(1).getActions().get(0).toString(), linkOpeningPassageWithoutBlank[2].replace("{", "").replace("}", ""));
        }

    }

    @Nested
    @DisplayName("Test that passage name, link reference and text, action get special characters")
    class testSpecialCharacters {
        @Test
        void nameOfPassagesContainsColon() {
            Assertions.assertTrue(storyContentArray[2].contains("::") && storyContentArray[7].contains("::") &&
                    storyContentArray[11].contains("::"));
        }

        @Test
        void linkTextContainsBrackets() {
            Assertions.assertTrue((storyContentArray[4].charAt(0) == '[' && storyContentArray[4].charAt(11) == ']') &&
                    (storyContentArray[5].charAt(0) == '[' && storyContentArray[5].charAt(18) == ']') &&
                    (storyContentArray[9].charAt(0) == '[' && storyContentArray[9].charAt(18) == ']'));
        }

        @Test
        void linkReferenceContainsParentheses() {
            Assertions.assertTrue((storyContentArray[4].charAt(12) == '(' && storyContentArray[4].charAt(22) == ')') &&
                    (storyContentArray[5].charAt(19) == '(' && storyContentArray[5].charAt(31) == ')') &&
                    (storyContentArray[9].charAt(19) == '(' && storyContentArray[9].charAt(31) == ')'));
        }

        @Test
        void linkActionContainsCurlyBracket() {

            Assertions.assertTrue((storyContentArray[4].charAt(23) == '{' && storyContentArray[4].charAt(37) == '}') &&
                    (storyContentArray[4].charAt(38) == '{' && storyContentArray[4].charAt(54) == '}') &&
                    (storyContentArray[4].charAt(55) == '{' && storyContentArray[4].charAt(70) == '}') &&
                    (storyContentArray[4].charAt(71) == '{' && storyContentArray[4].charAt(93) == '}') &&
                    (storyContentArray[5].charAt(32) == '{' && storyContentArray[5].charAt(47) == '}') &&
                    (storyContentArray[9].charAt(32) == '{' && storyContentArray[9].charAt(47) == '}'));
        }
    }

    @Test
    void afterEveryPassageNewLineIfNotLastPassage() {
        Assertions.assertTrue(storyContentArray[6].isEmpty() && storyContentArray[10].isEmpty());
    }

    @Test
    void storyObjectIsTheSameInFile() throws InvalidFileDataException {
        ReadFile readFile = new ReadFile();
        Assertions.assertEquals(story.toString(), readFile.getStory(storyInfoString).toString());
    }


}