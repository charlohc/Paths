package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.GameDetails.PlayerBuilder;
import org.junit.jupiter.api.*;

class HealthGoalTest {
    HealthGoal healthGoal;
    Player player;

    @BeforeEach
    void setUp() {
        healthGoal = new HealthGoal();

        player = PlayerBuilder.newInstance()
                .setName("Kari")
                .setHealth(50)
                .setGold(10)
                .setScore(10)
                .build();

        healthGoal.healthGoal(50);
    }

    @Nested
    @DisplayName("Testing the healthGoal method with valid and invalid minimum health number input")
    class testingGoldGoalMethod {
        @Test
        void healthGoalValidInput() {
            Assertions.assertTrue(healthGoal.healthGoal(20));
        }

        @Test
        void healthGoalInvalidInput() {
            Assertions.assertFalse(healthGoal.healthGoal(0));
        }
    }

    @Nested
    @DisplayName("Testing the isFulfilled method with valid and invalid player, and valid and invalid health value")
    class testingIsFulfilledMethod {
        @Test
        @DisplayName("Testing the isFulfilled method with a valid player with the required amount of health")
        void isFulfilledValidPlayerFulfilledHealth() {
            player.addHealth(40);
            Assertions.assertTrue(healthGoal.isFulfilled(player));
        }

        @Test
        @DisplayName("Testing the isFulfilled method with a valid player but not the required amount of health")
        void isFulfilledValidPlayerNotFulfilledHealth() {
            Assertions.assertFalse(healthGoal.isFulfilled(player));
        }

        @Test
        @DisplayName("Testing the isFulfilled method with an invalid player with the required amount of health")
        void isFulfilledInvalidPlayerFulfilledHealth() {
            player.addHealth(40);
            Assertions.assertFalse(healthGoal.isFulfilled(null));
        }

        @Test
        @DisplayName("Testing the isFulfilled method with an invalid player without the required amount of health")
        void isFulfilledInvalidPlayerNotFulfilledHealth() {
            Assertions.assertFalse(healthGoal.isFulfilled(null));
        }
    }

}