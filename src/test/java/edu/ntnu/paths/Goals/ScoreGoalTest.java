package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.GameDetails.PlayerBuilder;
import org.junit.jupiter.api.*;


class ScoreGoalTest {
    ScoreGoal scoreGoal;
    Player player;

    @BeforeEach
    void setUp() {
        scoreGoal = new ScoreGoal();

        player = PlayerBuilder.newInstance()
                .setName("Kari")
                .setHealth(50)
                .setGold(10)
                .setScore(5)
                .build();

        scoreGoal.scoreGoal(10);
    }

    @Nested
    @DisplayName("Testing the scoreGoal method with valid and invalid minimum score number input")
    class testingScoreGoalMethod {
        @Test
        void scoreGoalValidInput() {
            Assertions.assertTrue(scoreGoal.scoreGoal(20));
        }

        @Test
        void scoreGoalInvalidInput() {
            Assertions.assertFalse(scoreGoal.scoreGoal(-20));
        }
    }

    @Nested
    @DisplayName("Testing the isFulfilled method with valid and invalid player, and valid and invalid score value")
    class testingIsFulfilledMethod {
        @Test
        @DisplayName("Testing the isFulfilled method with a valid player with the required score value")
        void isFulfilledValidPlayerFulfilledScore() {
            player.addScore(10);
            Assertions.assertTrue(scoreGoal.isFulfilled(player));
        }

        @Test
        @DisplayName("Testing the isFulfilled method with a valid player but not the required score value")
        void isFulfilledValidPlayerNotFulfilledScore() {
            Assertions.assertFalse(scoreGoal.isFulfilled(player));
        }

        @Test
        @DisplayName("Testing the isFulfilled method with an invalid player with the required score value")
        void isFulfilledInvalidPlayerFulfilledScore() {
            player.addScore(40);
            Assertions.assertFalse(scoreGoal.isFulfilled(null));
        }

        @Test
        @DisplayName("Testing the isFulfilled method with an invalid player without the required score value")
        void isFulfilledInvalidPlayerNotFulfilledScore() {
            Assertions.assertFalse(scoreGoal.isFulfilled(null));
        }
    }

}