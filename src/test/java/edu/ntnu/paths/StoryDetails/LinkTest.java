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
    Link  linkToNextPassage;

    HealthAction healthAction, healthActionTestTwoWithSameClass;

    ScoreAction scoreAction;

    @BeforeEach
    void setUp() {
        linkToNextPassage = LinkBuilder.newInstance()
                .setText("text about next passage")
                .setReference("reference to the next passage")
                .build();

        healthAction = new HealthAction();

        scoreAction = new ScoreAction();

        healthAction.healthAction(10);

        scoreAction.scoreAction(5);

        linkToNextPassage.addAction(healthAction);

        linkToNextPassage.addAction(scoreAction);
    }

    @Nested
    @DisplayName("Testing Game object constructor with invalid fields")
    class creatingInvalidGameObject {

        @Test
        void linkWithoutText() {
            assertThrows(NullPointerException.class, () -> {
                Link linkWithoutText = LinkBuilder.newInstance()
                        .setText("")
                        .setReference("reference to the next passage")
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

    }

    @Nested
    @DisplayName("Testing the get methods of the passage object")
    class testingGetMethods {
        @Test
        void getText() {
            Assertions.assertEquals("text about next passage", linkToNextPassage.getText());
        }

        @Test
        void getReference() {
            Assertions.assertEquals("reference to the next passage", linkToNextPassage.getReference());
        }

        @Test
        void getActions() {
            List<Action> actions = new ArrayList<>();
            actions.add(healthAction);
            actions.add(scoreAction);

            Assertions.assertEquals(actions, linkToNextPassage.getActions());
        }
    }

  @Nested
  @DisplayName("Testing the addAction method with valid and invalid action")
  class testingAddActionMethod {
      @Test
      void addActionValidAction() {
          GoldAction goldAction = new GoldAction();
          goldAction.goldAction(25);

          Assertions.assertTrue(linkToNextPassage.addAction(goldAction));
      }

      @Test
      void addActionAlreadyInList() {
          Assertions.assertFalse(linkToNextPassage.addAction(healthAction));
      }

      @Test
      void addActionOfSameClassInList(){

          healthActionTestTwoWithSameClass = new HealthAction();

          healthActionTestTwoWithSameClass.healthAction(10);

          Assertions.assertFalse(linkToNextPassage.addAction(healthActionTestTwoWithSameClass));
      }
  }


}