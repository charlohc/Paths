package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.GameDetails.PlayerBuilder;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ScoreActionTest {

    ScoreAction scoreAction;
    Player player;

    @BeforeEach
    void setUp() {
        scoreAction = new ScoreAction();

        player = PlayerBuilder.newInstance()
                .setName("Kari")
                .setHealth(50)
                .setGold(10)
                .setScore(10)
                .build();
    }

    @Test
    void getMethod() {
        scoreAction.scoreAction(10);
        Assertions.assertEquals(10, scoreAction.getPoints());
    }

    @Nested
    @DisplayName("Testing the score action method with valid and invalid score number input")
    class testingGoldActionMethod {
        @Test
        void scoreActionValidInput() {
            assertTrue(scoreAction.scoreAction(10));
        }

        @Test
        void scoreActionInvalidInput() {
            assertFalse(scoreAction.scoreAction(-10));
        }
    }

    @Nested
    @DisplayName("Testing the execute method with valid and invalid player and testing for correct score value")
    class testingExecuteMethod {
        @Test
        void executeValidPlayer() {
            scoreAction.scoreAction(10);
            assertTrue(scoreAction.execute(player));
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
            assertFalse(scoreAction.execute(null));
        }
    }
}