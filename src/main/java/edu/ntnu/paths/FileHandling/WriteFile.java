package edu.ntnu.paths.FileHandling;

import edu.ntnu.paths.StoryDetails.Story;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

    public void writeGameToFile(Story story) {

        String text = story.toString();

        try {

            String fileName = story.getTittle();
            FileWriter fWriter = new FileWriter(System.getProperty("user.dir") + System.getProperty("file.separator")
                    + "src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator")
                    + "java" + System.getProperty("file.separator")  + "edu" + System.getProperty("file.separator") +
                    "ntnu" + System.getProperty("file.separator") + "paths" + System.getProperty("file.separator")  + "FileHandling"
                    +  System.getProperty("file.separator") + fileName + ".paths");

            System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));


            fWriter.write(text);

            System.out.println(text);

            fWriter.close();

            System.out.println(
                    "File is created successfully with the content.");
        } catch (IOException e) {

            System.out.print(e.getMessage());
        }

    }

    // Main driver method
    public static void main(String[] args) {

    }

}
