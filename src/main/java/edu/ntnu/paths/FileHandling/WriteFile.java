package edu.ntnu.paths.FileHandling;

import edu.ntnu.paths.StoryDetails.Story;
import edu.ntnu.paths.Exceptions.StoryExistException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class for writing a Story object to a file.
 */
public class WriteFile {

    /**
     * Writes a Story object to a file.
     *
     * @param story The Story object to write.
     * @throws StoryExistException If a story with the same name already exists.
     */
    public void writeStoryToFile(Story story) throws StoryExistException {
        String text = story.toString();

        try {
            String fileName = story.getTittle();
            String filePath = System.getProperty("user.dir") + File.separator
                    + "src" + File.separator + "main" + File.separator
                    + "java" + File.separator + "edu" + File.separator +
                    "ntnu" + File.separator + "paths" + File.separator  + "FileHandling"
                    +  File.separator + "StoryFiles" + File.separator + fileName + ".paths";

            if (new File(filePath).exists()) {
                throw new StoryExistException("Story with the given name already exists.");
            }

            FileWriter fWriter = new FileWriter(filePath);
            fWriter.write(text);
            fWriter.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public static void main(String[] args) {

    }
}
