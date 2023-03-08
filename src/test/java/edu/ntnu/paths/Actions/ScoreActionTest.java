package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.Goals.ScoreGoal;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ScoreActionTest {

    ScoreAction scoreAction;
    Player player;

    @BeforeEach
    void setUp() {
        scoreAction = new ScoreAction();

        player = new Player("Kari", 100,0,10);
    }

    @Nested
    @DisplayName("Testing the score action method with valid and invalid score number input")
    class testingGoldActionMethod {
        @Test
        void scoreActionValidInput() {
            Assertions.assertTrue(scoreAction.scoreAction(10));
        }

        @Test
        void scoreActionInvalidInput() {
            Assertions.assertFalse(scoreAction.scoreAction(-10));
        }
    }

    @Nested
    @DisplayName("Testing the execute method with valid and invalid player and testing for correct score value")
    class testingExecuteMethod {
        @Test
        void executeValidPlayer() {
            scoreAction.scoreAction(10);
            Assertions.assertTrue(scoreAction.execute(player));
        }

        @Test
        void executeGivesCorrectHealthValue() {
            int previousScorePlayer = player.getScore();
            scoreAction.scoreAction(10);
            scoreAction.execute(player);
            assertEquals(previousScorePlayer + 10, player.getScore());
        }

        @Test
        void executeInvalidPlayer() {
            Assertions.assertFalse(scoreAction.execute(null));
        }
    }
}