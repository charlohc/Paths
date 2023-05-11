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
    class testExceptionsFileProperties {
        @Test
        void testReadFileWithNonExistentFile() {

            Assertions.assertThrows(RuntimeException.class, () -> {
                readFile.readFileFromFileName("non_existing_file");
            });
        }

        @Test
        void testReadFileWithInvalidFilePath() {

            Assertions.assertThrows(RuntimeException.class, () -> {
                readFile.readFile(new File("invalid/file/path.paths"));
            });

        }

        @Test
        void testReadFileWithEmptyFile() {
            Assertions.assertThrows(RuntimeException.class, () -> {
                readFile.readFile(new File(pathTestFiles + "EmptyFile.paths"));
            });
        }

        @Test
        void fileCompressedText() {
            Assertions.assertThrows(RuntimeException.class, () -> {
                readFile.readFile(new File(pathTestFiles + "CompressedText.paths"));
            });
        }

        @Test
        void fileWithoutPathsEnding() {
            Assertions.assertThrows(RuntimeException.class, () -> {
                readFile.readFile(new File(pathTestFiles + "FileWithoutPathsEnding.txt"));
            });
        }

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
    }

        @Nested
        @DisplayName("Testing the getStory method with invalid info in the files")
        class invalidInfoFile {

            @Test
            public void fileWithBreakLineCommand() {
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    readFile.readFile(new File(pathTestFiles + "FileWithBreakLineCommand.paths"));
                });
            }

            @Test
            public void fileWithEmptyLink() {
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    readFile.readFile(new File(pathTestFiles + "FileWithEmptyLink.paths"));
                });
            }

            @Test
            public void fileWithMoreColons() {
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    readFile.readFile(new File(pathTestFiles + "FileWithMoreColons.paths"));
                });
            }

            @Test
            public void fileWithoutColon() {
                Assertions.assertThrows(RuntimeException.class, () -> {
                    readFile.readFile(new File(pathTestFiles + "FileWithoutColon.paths"));
                });
            }

            @Test
            public void fileWithoutLink() {
                Assertions.assertThrows(NullPointerException.class, () -> {
                    readFile.readFile(new File(pathTestFiles + "FileWithoutLink.paths"));
                });
            }
            @Test
            public void fileWithoutPassage() {
                Assertions.assertThrows(RuntimeException.class, () -> {
                    readFile.readFile(new File(pathTestFiles + "FileWithoutPassage.paths"));
                });
            }
            @Test
            public void fileWithoutSpecialCharactersForLinks() {
                Assertions.assertThrows(RuntimeException.class, () -> {
                    readFile.readFile(new File(pathTestFiles + "FileWithoutSpecialCharsForLinks.paths"));
                });
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
            public void fileWithNorwegianChars() {
                Story storyFromFile = (Story) readFile.readFile(new File(pathTestFiles + "FileWithNorwegianChars.paths"));
                Assertions.assertTrue(storyFromFile.getPassage().getContent().contains("ÆØÅ"));
            }

            @Test
            public void fileWithTheSameActionInLink() {
                Story storyFromFile = (Story) readFile.readFile(new File(pathTestFiles + "FileWithTheSameActionInLink.paths"));
                    Assertions.assertEquals(1, storyFromFile.getPassage().getLinks().get(0).getActions().size());
            }

        }
    }
