package edu.ntnu.paths.FileHandling;

import edu.ntnu.paths.Actions.*;
import edu.ntnu.paths.Exceptions.InvalidFileDataException;
import edu.ntnu.paths.StoryDetails.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Utility class for reading story files and creating Story objects.
 */
public class ReadFile {

    /**
     * Reads a story file from the specified file name and returns the corresponding Story object.
     *
     * @param fileName The name of the file to read.
     * @return The Story object created from the file, or null if an error occurred.
     */
    public Story readFileFromFileName(String fileName) {
        ReadFile readFile = new ReadFile();

        File storyFromFile = new File(System.getProperty("user.dir") + File.separator
                + "src" + File.separator + "main" + File.separator
                + "java" + File.separator + "edu" + File.separator +
                "ntnu" + File.separator + "paths" + File.separator  + "FileHandling"
                +  File.separator + "StoryFiles" + File.separator + fileName + ".paths");

        return readFile.readFile(storyFromFile);
    }

    /**
     * Reads a story file from the specified File object and returns the corresponding Story object.
     *
     * @param file The File object representing the story file.
     * @return The Story object created from the file, or null if an error occurred.
     */
    public Story readFile(File file) {
        ReadFile readFile = new ReadFile();

        if (file == null || file.length() == 0 || !file.getName().endsWith(".paths")) {
            return null;
        }

        Scanner myReader;
        try {
            myReader = new Scanner(file);
        } catch (FileNotFoundException e) {
            return null;
        }
        StringBuilder storyInfo = new StringBuilder();

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            storyInfo.append(data).append("\n");
        }
        myReader.close();

        try {
            return readFile.getStory(storyInfo.toString());
        } catch (InvalidFileDataException e) {
            return null;
        }
    }

    /**
     * Parses the story information and constructs a Story object.
     *
     * @param storyInfo The string containing the story information.
     * @return The Story object created from the story information.
     * @throws InvalidFileDataException If the file data is invalid or incomplete.
     */
    public Story getStory(String storyInfo) throws InvalidFileDataException {
        String[] storyContentArray = storyInfo.split("\n");

        try {
            Passage openingPassage = PassageBuilder.newInstance()
                    .setTitle(storyContentArray[2].replace(":", ""))
                    .setContent(storyContentArray[3])
                    .build();

            Story story = StoryBuilder.newInstance()
                    .setTitle(storyContentArray[0])
                    .setOpeningPassage(openingPassage)
                    .build();

            story.addPassage(openingPassage);

            int index = 4;

            index = addLink(storyContentArray, openingPassage, index);

            Passage passage = null;

            while (!(index == storyContentArray.length)) {
                if (storyContentArray[index].contains("::")) {
                    passage = PassageBuilder.newInstance()
                            .setTitle(storyContentArray[index].replaceAll("::", ""))
                            .setContent(storyContentArray[index + 1])
                            .build();

                    story.addPassage(passage);
                    index += 2;
                } else {
                    addLink(storyContentArray, passage, index);
                    index++;
                }
            }
            return story;
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Adds links to a passage based on the provided story content array.
     *
     * @param storyContentArray The array containing the story content.
     * @param passage           The Passage object to add the links to.
     * @param index             The current index in the story content array.
     * @return The next index after adding the links.
     */
    public static int addLink(String[] storyContentArray, Passage passage, int index) {
        while (!storyContentArray[index].isEmpty()) {
            String[] links = storyContentArray[index].split("[{}()]");
            String[] linksWithoutBlank = Arrays.stream(links).filter(x -> !x.isEmpty()).toArray(String[]::new);

            String linkTitle = linksWithoutBlank[0].replace("[", "").replace("]", "");
            String linkContent = linksWithoutBlank[1];

            boolean isDuplicateLink = passage.getLinks().stream()
                    .anyMatch(link -> link.getText().equals(linkTitle) && link.getReference().equals(linkContent));

            if (!isDuplicateLink) {
                Link linkFromNewPassage = LinkBuilder.newInstance()
                        .setText(linkTitle)
                        .setReference(linkContent)
                        .build();

                for (int j = 2; j < linksWithoutBlank.length; j++) {
                    linkFromNewPassage.addAction(setAction(linksWithoutBlank[j]));
                }

                passage.addLink(linkFromNewPassage);
            }

            if ((index + 1) == storyContentArray.length) {
                break;
            }
            index++;
        }

        return index + 1;
    }

    /**
     * Sets the action based on the provided action string.
     *
     * @param action The action string to set.
     * @return The Action object corresponding to the action string.
     */
    public static Action setAction(String action) {
        if (action.contains("GoldAction")) {
            GoldAction goldAction = new GoldAction();
            int valueGoldAction = Integer.parseInt(action.replaceAll("[^0-9]", ""));
            goldAction.goldAction(valueGoldAction);
            return goldAction;
        } else if (action.contains("HealthAction")) {
            HealthAction healthAction = new HealthAction();
            int valueHealthAction = Integer.parseInt(action.replaceAll("[^\\d-]", ""));
            healthAction.healthAction(valueHealthAction);
            return healthAction;
        } else if (action.contains("ScoreAction")) {
            ScoreAction scoreAction = new ScoreAction();
            int valueScoreAction = Integer.parseInt(action.replaceAll("[^0-9]", ""));
            scoreAction.scoreAction(valueScoreAction);
            return scoreAction;
        } else if (action.contains("InventoryAction")) {
            InventoryAction inventoryAction = new InventoryAction();
            String[] inventoryItems = action.split(":");
            for (int i = 1; i < inventoryItems.length; i++) {
                String inventoryItem = inventoryItems[i].trim();
                inventoryAction.inventoryAction(inventoryItem.toLowerCase());
            }
            return inventoryAction;
        }

        return null;
    }
}
