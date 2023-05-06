package edu.ntnu.paths.FileHandling;

import edu.ntnu.paths.Exceptions.EmptyFileException;
import edu.ntnu.paths.Exceptions.InvalidFileDataException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;

class ReadFileTest {
    ReadFile readFile;
    String pathTestFiles = "";

    @BeforeEach
    void setUp() {
        readFile = new ReadFile();
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
                readFile.readFileFromFileName("non_existing_file");
            });
        }

        @Test
        void testReadFileWithInvalidFilePath() {

            Assertions.assertThrows(FileNotFoundException.class, () -> {
                readFile.readFileFromFile(new File("invalid/file/path.paths"));
            });

        }

        @Test
        void testReadFileWithEmptyFile() {
            Assertions.assertThrows(EmptyFileException.class, () -> {
                readFile.readFileFromFile(new File(pathTestFiles + "EmptyFile.paths"));
            });
        }

        @Test
        void fileCompressedText() {
            Assertions.assertThrows(InvalidFileDataException.class, () -> {
                readFile.readFileFromFile(new File(pathTestFiles + "CompressedText.paths"));
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

            Assertions.assertThrows(NullPointerException.class, () -> {
                readFile.getStory(null);
            });
        }

        @Test
        public void testGetStoryWithInvalidStoryInfo() {

            String invalidStoryInfo = "This is an invalid story info";

            Assertions.assertThrows(InvalidFileDataException.class, () -> {
                readFile.getStory(invalidStoryInfo);
            });
        }

        @Nested
        @DisplayName("Testing the getStory method with invalid info in the files")
        class invalidInfoFile {

            @Test
            public void fileWithBreakLineCommand() {
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    readFile.readFileFromFile(new File(pathTestFiles + "FileWithBreakLineCommand.paths"));
                });
            }

            @Test
            public void fileWithEmptyLink() {
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    readFile.readFileFromFile(new File(pathTestFiles + "FileWithEmptyLink.paths"));
                });
            }
        }

        @Nested
        @DisplayName("Test the readFile method with functioning file")
        class functioningFile {
            @Test
            public void testReadStoryWithValidStory() {


            }

        }
    }
}