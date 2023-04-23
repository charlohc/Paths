package edu.ntnu.paths.FileHandling;

import edu.ntnu.paths.Exceptions.StoryExistException;
import edu.ntnu.paths.StoryDetails.Story;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

    public void writeStoryToFile(Story story) throws StoryExistException {

        String text = story.toString();

        try {

            String fileName = story.getTittle();
            String filePath = System.getProperty("user.dir") + System.getProperty("file.separator")
                    + "src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator")
                    + "java" + System.getProperty("file.separator")  + "edu" + System.getProperty("file.separator") +
                    "ntnu" + System.getProperty("file.separator") + "paths" + System.getProperty("file.separator")  + "FileHandling"
                    +  System.getProperty("file.separator") + "StoryFiles" + System.getProperty("file.separator") + fileName + ".paths";

            if (new File(filePath).exists()) {
                throw new StoryExistException("Story with the given name already exist");
            }
            FileWriter fWriter = new FileWriter(filePath);

            fWriter.write(text);

            fWriter.close();

        }
        catch (IOException e) {

            System.out.print(e.getMessage());
        }

    }

    public static void main(String[] args) {

    }

}
