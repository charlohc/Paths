package edu.ntnu.paths.FileHandling;

import edu.ntnu.paths.Exceptions.EmptyFileException;
import edu.ntnu.paths.Exceptions.InvalidFileDataException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;

class ReadFileTest {

    ReadFile readFile = new ReadFile();
    String pathTestFiles = "";

    @BeforeEach
    void setUp() {
        pathTestFiles = System.getProperty("user.dir") + System.getProperty("file.separator")
                + "src" + System.getProperty("file.separator") + "test" + System.getProperty("file.separator")
                + "java" + System.getProperty("file.separator") + "edu" + System.getProperty("file.separator") +
                "ntnu" + System.getProperty("file.separator") + "paths" + System.getProperty("file.separator") + "FileHandling"
                + System.getProperty("file.separator") + "TestFiles" + System.getProperty("file.separator");

    }

    @Nested
    @DisplayName("Testing the read file method with exceptions in file properties")
    class testExceptionsFileProperties {
        @Test
        void testReadFileWithNonExistentFile() {

            Assertions.assertThrows(FileNotFoundException.class, () -> {
                readFile.readFileFromPath("non_existing_file");
            });
        }

        @Test
        void testReadFileWithInvalidFilePath() {

            Assertions.assertThrows(FileNotFoundException.class, () -> {
                readFile.readFile(new File("invalid/file/path.paths"));
            });

        }

        @Test
        void testReadFileWithEmptyFile() {
            Assertions.assertThrows(EmptyFileException.class, () -> {
                readFile.readFile(new File(pathTestFiles + "EmptyFile.paths"));
            });
        }

        @Test
        void fileCompressedText() {
            Assertions.assertThrows(InvalidFileDataException.class, () -> {
                readFile.readFile(new File(pathTestFiles + "CompressedText.paths"));
            });

        }

    }

    @Nested
    @DisplayName("Testing the read file method with exceptions when creating story object")
    class testExceptionsStoryObject {
    }


    @Nested
    @DisplayName("Testing the getStory method with exceptions")
    class getStoryExceptions {
        @Test
        public void testGetStoryWithNullStoryInfo() {
            ReadFile readFile = new ReadFile();

            Assertions.assertThrows(NullPointerException.class, () -> {
                readFile.getStory(null);
            });
        }

        @Test
        public void testGetStoryWithInvalidStoryInfo() {
            ReadFile readFile = new ReadFile();

            String invalidStoryInfo = "This is an invalid story info";

            Assertions.assertThrows(InvalidFileDataException.class, () -> {
                readFile.getStory(invalidStoryInfo);
            });
        }

        @Nested
        @DisplayName("Test the readFile method with functioning file")
        class functioningFile {

        }

    }
}