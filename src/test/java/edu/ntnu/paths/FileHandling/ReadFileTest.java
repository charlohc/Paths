package edu.ntnu.paths.FileHandling;

import edu.ntnu.paths.Exceptions.InvalidFileDataException;
import edu.ntnu.paths.StoryDetails.Story;
import org.junit.jupiter.api.*;

import java.io.File;

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
    class TestExceptionsFileProperties {
        @Test
        void testReadFileWithNonExistentFile() {
            String nonExistingFile = "non_existing_file";
            Assertions.assertNull(readFile.readFileFromFileName(nonExistingFile));
        }

        @Test
        void testReadFileWithInvalidFilePath() {
            File invalidFilePath = new File("invalid/file/path.paths");
            Assertions.assertNull(readFile.readFile(invalidFilePath));
        }

        @Test
        void testReadFileWithEmptyFile() {
            File emptyFile = new File(pathTestFiles + "EmptyFile.paths");
            Assertions.assertNull(readFile.readFile(emptyFile));
        }

        @Test
        void fileCompressedText() {
            File compressedTextFile = new File(pathTestFiles + "CompressedText.paths");
            Assertions.assertNull(readFile.readFile(compressedTextFile));
        }

        @Test
        void fileWithoutPathsEnding() {
            File fileWithoutPathsEnding = new File(pathTestFiles + "FileWithoutPathsEnding.txt");
            Assertions.assertNull(readFile.readFile(fileWithoutPathsEnding));
        }
    }

    @Nested
    @DisplayName("Testing the getStory method with exceptions")
    class GetStoryExceptions {
        @Test
        public void testGetStoryWithNullStoryInfo() {

            Assertions.assertThrows(NullPointerException.class, () -> {
                readFile.getStory(null);
            });
        }


        @Test
        public void testGetStoryWithInvalidStoryInfo() {
            String invalidStoryInfo = "This is an invalid story info";
            try {
                Assertions.assertNull(readFile.getStory(invalidStoryInfo));
            } catch (InvalidFileDataException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Nested
    @DisplayName("Testing the getStory method with invalid info in the files")
    class InvalidInfoFile {
        @Test
        public void fileWithBreakLineCommand() {
            File fileWithBreakLineCommand = new File(pathTestFiles + "FileWithBreakLineCommand.paths");
            Assertions.assertNull(readFile.readFile(fileWithBreakLineCommand));
        }

        @Test
        public void fileWithEmptyLink() {
            File fileWithEmptyLink = new File(pathTestFiles + "FileWithEmptyLink.paths");
            Assertions.assertNull(readFile.readFile(fileWithEmptyLink));
        }

        @Test
        public void fileWithMoreColons() {
            File fileWithMoreColons = new File(pathTestFiles + "FileWithMoreColons.paths");
            Assertions.assertNull(readFile.readFile(fileWithMoreColons));
        }

        @Test
        public void fileWithoutColon() {
            File fileWithoutColon = new File(pathTestFiles + "FileWithoutColon.paths");
            Assertions.assertNull(readFile.readFile(fileWithoutColon));
        }

        @Test
        public void fileWithoutLink() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                readFile.readFile(new File(pathTestFiles + "FileWithoutLink.paths"));
            });
        }


        @Test
        public void fileWithoutPassage() {
            File fileWithoutPassage = new File(pathTestFiles + "FileWithoutPassage.paths");
            Assertions.assertNull(readFile.readFile(fileWithoutPassage));
        }

        @Test
        public void fileWithoutSpecialCharactersForLinks() {
            File fileWithoutSpecialCharsForLinks = new File(pathTestFiles + "FileWithoutSpecialCharsForLinks.paths");
            Assertions.assertNull(readFile.readFile(fileWithoutSpecialCharsForLinks));
        }
    }

        @Nested
        @DisplayName("Test the readFile method with functioning file")
        class functioningFile {
            @Test
            public void fileWithMoreBreakLines() {
                Assertions.assertNotNull(readFile.readFile(new File(pathTestFiles + "FileWithMoreBreakLines.paths")));
            }

            @Test
            public void fileWithoutAction() {
                Assertions.assertNotNull(readFile.readFile(new File(pathTestFiles + "FileWithoutAction.paths")));

            }

            @Test
            public void fileWithTheSameActionInLink() {
                Story storyFromFile = (Story) readFile.readFile(new File(pathTestFiles + "FileWithTheSameActionInLink.paths"));
                    Assertions.assertEquals(1, storyFromFile.getPassage().getLinks().get(0).getActions().size());
            }

        }
    }
