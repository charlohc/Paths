package edu.ntnu.paths.FileHandling;


import edu.ntnu.paths.Actions.*;
import edu.ntnu.paths.StoryDetails.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReadFile {

    public static void main(String[] args) {

        String fileName = "The last person on earth";
        try {
            File myObj = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
                    + "src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator")
                    + "java" + System.getProperty("file.separator")  + "edu" + System.getProperty("file.separator") +
                    "ntnu" + System.getProperty("file.separator") + "paths" + System.getProperty("file.separator")  + "FileHandling"
                    +  System.getProperty("file.separator") + fileName + ".paths");

            Scanner myReader = new Scanner(myObj);
            StringBuilder storyInfo = new StringBuilder();




            while (myReader.hasNextLine()) {
                 String data = myReader.nextLine();
                 storyInfo.append(data).append("\n");
            }
            myReader.close();
            getStory(storyInfo.toString());
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void getStory(String storyInfo) {

        String[] storyContentArray = storyInfo.split("\n");


        //System.out.println(Arrays.toString(storyContentArray));



        Passage openingPassage = PassageBuilder.newInstance()
                .setTitle(storyContentArray[2].replace(":", ""))
                .setContent(storyContentArray[3])
                .build();

        Story story = StoryBuilder.newInstance()
                .setTitle(storyContentArray[0])
                .setOpeningPassage(openingPassage)
                .build();


        int index = 4;

        while (!storyContentArray[index].isEmpty()) {

            String[] links = storyContentArray[index].split("[{}()]");

            String[] linksWithoutBlank = Arrays.stream(links).filter(x -> !x.isEmpty()).toArray(String[]::new);



            Link openingPassageLink = null;

                 openingPassageLink = LinkBuilder.newInstance()
                        .setText(linksWithoutBlank[0])
                        .setReference(linksWithoutBlank[1])
                        .build();



            for (int j = 2; j < linksWithoutBlank.length; j++) {

                    openingPassageLink.addAction(setAction(linksWithoutBlank[j]));

            }
            
            openingPassage.addLink(openingPassageLink);
            index++;
        }

        Passage passage = null;

        while (!(index == storyContentArray.length)) {


                if (storyContentArray[index].contains("::")) {
                     passage = PassageBuilder.newInstance()
                            .setTitle(storyContentArray[index].replaceAll("::",""))
                            .setContent(storyContentArray[index + 1])
                            .build();

                     story.addPassage(passage);
                    index += 2;
                } else {

                    while (!storyContentArray[index].isEmpty()) {

                        String[] links = storyContentArray[index].split("[{}()]");

                        String[] linksWithoutBlank = Arrays.stream(links).filter(x -> !x.isEmpty()).toArray(String[]::new);



                        Link passageLink = null;

                        passageLink = LinkBuilder.newInstance()
                                .setText(linksWithoutBlank[0])
                                .setReference(linksWithoutBlank[1])
                                .build();



                        for (int j = 2; j < linksWithoutBlank.length; j++) {

                            passageLink.addAction(setAction(linksWithoutBlank[j]));

                        }

                        assert passage != null;
                        passage.addLink(passageLink);
                        index++;
                    }

                    index++;
                }

        }

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
