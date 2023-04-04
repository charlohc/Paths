package edu.ntnu.paths.FileHandling;

import edu.ntnu.paths.StoryDetails.Passage;
import edu.ntnu.paths.StoryDetails.Story;
import edu.ntnu.paths.StoryDetails.StoryBuilder;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files
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
                 storyInfo.append(data + "\n");
            }
            myReader.close();
            getStory(storyInfo.toString());
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Story getStory(String storyInfo){

        String[] storyInfoArr = storyInfo.split("\n");
        System.out.println(Arrays.toString(storyInfoArr));

        

     /*   Story story = StoryBuilder.newInstance()
                .setTitle(storyInfoArr[0])
                .setOpeningPassage(new Passage(storyInfoArr[2]))
                .build();*/

        return null;
    }

}
