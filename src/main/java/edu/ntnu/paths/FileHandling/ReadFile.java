package edu.ntnu.paths.FileHandling;


import edu.ntnu.paths.Actions.*;
import edu.ntnu.paths.Exceptions.EmptyFileException;
import edu.ntnu.paths.Exceptions.InvalidFileDataException;
import edu.ntnu.paths.StoryDetails.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ReadFile {

    public static void main(String[] args) {

    }

    public void readFileFromPath(String fileName) throws FileNotFoundException, EmptyFileException, InvalidFileDataException {
        ReadFile readFile = new ReadFile();


            File storyFromFile = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
                    + "src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator")
                    + "java" + System.getProperty("file.separator")  + "edu" + System.getProperty("file.separator") +
                    "ntnu" + System.getProperty("file.separator") + "paths" + System.getProperty("file.separator")  + "FileHandling"
                    +  System.getProperty("file.separator") + "StoryFiles" + System.getProperty("file.separator") + fileName + ".paths");

            if (!storyFromFile.exists()) {
                throw new FileNotFoundException("File " + fileName + " not found.");
            }
            if (storyFromFile.length() == 0) {
                throw new EmptyFileException("File " + fileName + "is empty.");
            }

            Scanner myReader = new Scanner(storyFromFile);
            StringBuilder storyInfo = new StringBuilder();


            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                storyInfo.append(data).append("\n");
            }
            myReader.close();
            readFile.getStory(storyInfo.toString());

    }

    public void readFile(File file) throws FileNotFoundException, EmptyFileException, InvalidFileDataException {
        ReadFile readFile = new ReadFile();

        if (!file.exists()) {
            throw new FileNotFoundException("File " + file.getName() + " not found.");
        }
        if (file.length() == 0) {
            throw new EmptyFileException("File " + file.getName() + "is empty.");
        }

        Scanner myReader = new Scanner(file);
        StringBuilder storyInfo = new StringBuilder();


        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            storyInfo.append(data).append("\n");
        }
        myReader.close();

        readFile.getStory(storyInfo.toString());



    }


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
    } catch (IndexOutOfBoundsException e ) {
            throw new InvalidFileDataException("Invalid file");

    }
    }

    public static int addLink(String[] storyContentArray,Passage passage, int index) {
        while (!storyContentArray[index].isEmpty()) {

            String[] links = storyContentArray[index].split("[{}()]");

            String[] linksWithoutBlank = Arrays.stream(links).filter(x -> !x.isEmpty()).toArray(String[]::new);



            Link linkFromNewPassage = null;

            linkFromNewPassage = LinkBuilder.newInstance()
                    .setText(linksWithoutBlank[0].replace("[","").replace("]",""))
                    .setReference(linksWithoutBlank[1])
                    .build();



            for (int j = 2; j < linksWithoutBlank.length; j++) {

                linkFromNewPassage.addAction(setAction(linksWithoutBlank[j]));

            }

            passage.addLink(linkFromNewPassage);

            if ((index + 1) == storyContentArray.length) {
                break;
            }
            index++;
        }
        return index + 1;
    }

   public static Action setAction(String action) {

     if (action.contains("GoldAction")) {
         GoldAction goldAction = new GoldAction();
         int valueGoldAction = Integer.parseInt(action.replaceAll("[^0-9]", ""));

         goldAction.goldAction(valueGoldAction);
         return goldAction;

     }  else if (action.contains("HealthAction")) {
         HealthAction healthAction = new HealthAction();
         int valueHealthAction = Integer.parseInt(action.replaceAll("[^0-9]", ""));

         healthAction.healthAction(valueHealthAction);
         return healthAction;

     }   else if (action.contains("ScoreAction")) {
         ScoreAction scoreAction = new ScoreAction();
         int valueScoreAction = Integer.parseInt(action.replaceAll("[^0-9]", ""));

         scoreAction.scoreAction(valueScoreAction);
         return scoreAction;
     }
     else if (action.contains("InventoryAction")) {
        InventoryAction inventoryAction = new InventoryAction();
         String[] inventoryItemArr = action.split(":");
         String valueInventoryAction = inventoryItemArr[1];
         inventoryAction.inventoryAction(valueInventoryAction);
         return inventoryAction;
     }
     else {
         System.out.println("problem");
     }

        return null;
    }
}
