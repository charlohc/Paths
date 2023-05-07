package edu.ntnu.paths.StoryDetails;

import edu.ntnu.paths.Actions.Action;
import edu.ntnu.paths.Actions.GoldAction;
import edu.ntnu.paths.Actions.HealthAction;
import edu.ntnu.paths.Actions.ScoreAction;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LinkTest {
    Link linkToPorchPassage;

    HealthAction healthAction, healthActionTestTwoWithSameClass;

    ScoreAction scoreAction;

    @BeforeEach
    void setUp() {
        linkToPorchPassage = LinkBuilder.newInstance()
                .setText("Go outside")
                .setReference("The porch")
                .build();

        healthAction = new HealthAction();

        scoreAction = new ScoreAction();

        healthAction.healthAction(10);

        scoreAction.scoreAction(5);

        linkToPorchPassage.addAction(healthAction);

        linkToPorchPassage.addAction(scoreAction);
    }

    @Nested
    @DisplayName("Testing Game object constructor with invalid fields")
    class creatingInvalidGameObject {

        @Test
        void linkWithoutText() {
            assertThrows(NullPointerException.class, () -> {
                Link linkWithoutText = LinkBuilder.newInstance()
                        .setText("")
                        .setReference("The porch")
                        .build();
            });
        }

        @Test
        void linkWithInvalidTextSpecialCharacter() {
            assertThrows(IllegalArgumentException.class, () -> {
                Link linkTextWithSpecialCharacter = LinkBuilder.newInstance()
                        .setText("{ go outside}")
                        .setReference("The porch")
                        .build();
            });
        }

        @Test
        void linkWithInvalidTextNewLine() {
            assertThrows(IllegalArgumentException.class, () -> {
                Link linkTextWithNewLine = LinkBuilder.newInstance()
                        .setText("Go outside \n")
                        .setReference("The porch")
                        .build();
            });
        }

        @Test
        void linkWithoutReference() {
            assertThrows(NullPointerException.class, () -> {
                Link linkWithoutReference = LinkBuilder.newInstance()
                        .setText("text about next passage")
                        .setReference("")
                        .build();
            });
        }

        @Test
        void linkWithInvalidReferenceSpecialCharacter() {
            assertThrows(IllegalArgumentException.class, () -> {
                Link linkReferenceWithSpecialCharacter = LinkBuilder.newInstance()
                        .setText("Go outside")
                        .setReference("*The porch")
                        .build();
            });
        }

        @Test
        void linkWithInvalidReferenceNewLine() {
            assertThrows(IllegalArgumentException.class, () -> {
                Link linkReferenceWithNewLine = LinkBuilder.newInstance()
                        .setText("Go outside")
                        .setReference("The porch" + "\n")
                        .build();
            });
        }

    }

    @Nested
    @DisplayName("Testing the get methods of the passage object")
    class testingGetMethods {
        @Test
        void getText() {
            Assertions.assertEquals("text about next passage", linkToPorchPassage.getText());
        }

        @Test
        void getReference() {
            Assertions.assertEquals("reference to the next passage", linkToPorchPassage.getReference());
        }

        @Test
        void getActions() {
            List<Action> actions = new ArrayList<>();
            actions.add(healthAction);
            actions.add(scoreAction);

            Assertions.assertEquals(actions, linkToPorchPassage.getActions());
        }
    }

  @Nested
  @DisplayName("Testing the addAction method with valid and invalid action")
  class testingAddActionMethod {
      @Test
      void addActionValidAction() {
          GoldAction goldAction = new GoldAction();
          goldAction.goldAction(25);

          Assertions.assertTrue(linkToPorchPassage.addAction(goldAction));
      }

      @Test
      void addActionAlreadyInList() {
          Assertions.assertFalse(linkToPorchPassage.addAction(healthAction));
      }

      @Test
      void addActionOfSameClassInList(){

          healthActionTestTwoWithSameClass = new HealthAction();

          healthActionTestTwoWithSameClass.healthAction(10);

          Assertions.assertFalse(linkToPorchPassage.addAction(healthActionTestTwoWithSameClass));
      }
  }


}