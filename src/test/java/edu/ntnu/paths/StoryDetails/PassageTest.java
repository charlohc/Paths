package edu.ntnu.paths.StoryDetails;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class PassageTest {

    Passage openingPassage;

    Link linkToPorchPassage;

    @BeforeEach
    void setUp() {
        openingPassage = PassageBuilder.newInstance()
                .setTitle("Beginning")
                .setContent("You woke up and felt at once all alone")
                .build();

        linkToPorchPassage = LinkBuilder.newInstance()
                .setText("Go outside")
                .setReference("The porch")
                .build();

        openingPassage.addLink(linkToPorchPassage);
    }

    @Nested
    @DisplayName("Testing player object constructor with invalid fields")
    class creatingInvalidPassageObject {

        @Test
        void passageWithoutTittle() {
            assertThrows(NullPointerException.class, () -> {
               Passage passageWithoutTittle = PassageBuilder.newInstance()
                        .setTitle("")
                        .setContent("You woke up and felt at once all alone")
                        .build();
            });
        }

        @Test
        void passageWithoutContent() {
            assertThrows(NullPointerException.class, () -> {
                Passage passageWithoutContent = PassageBuilder.newInstance()
                        .setTitle("Beginning")
                        .setContent("")
                        .build();
            });
        }

        }

    @Nested
    @DisplayName("Testing get methods from Passage object")
    class validGetMethods {
        @Test
        void getTittle() {
            Assertions.assertEquals("Beginning", openingPassage.getTittle());
        }

        @Test
        void getContent() {
            Assertions.assertEquals("You woke up and felt at once all alone", openingPassage.getContent());
        }

        @Test
        void getLinks() {
            Assertions.assertEquals(openingPassage.getLinks().get(0), linkToPorchPassage);
        }
    }

    @Nested
    @DisplayName("Testing the addLink and has link method with valid input")
    class methodsAddAndHasLinkValid {

        @Test
        void addLinkValid() {
            Link addableLink = LinkBuilder.newInstance()
                    .setText("next passage")
                    .setReference("next passage reference")
                    .build();

            Assertions.assertTrue(openingPassage.addLink(addableLink));
        }

        @Test
        void hasLinkValid() {
            Assertions.assertTrue(openingPassage.hasLink(linkToPorchPassage));
        }
    }

    @Nested
    @DisplayName("Testing the addLink and has link method with invalid input")
    class methodsAddAndHasLinkInvalid {

        @Test
        void addLinkAlreadyInPassage() {
            Assertions.assertFalse(openingPassage.addLink(linkToPorchPassage));
        }

        @Test
        void hasLinkLinkNotInList() {
            Link linkNotInList = LinkBuilder.newInstance()
                    .setText("next passage")
                    .setReference("next passage reference")
                    .build();

            Assertions.assertFalse(openingPassage.hasLink(linkNotInList));
        }
    }
}