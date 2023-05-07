package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;
import org.junit.jupiter.api.*;

class GoldGoalTest {

    GoldGoal goldGoal;
    Player player;

    @BeforeEach
    void setUp() {
        goldGoal = new GoldGoal();

        player = new Player("Kari", 100,10,0);

        goldGoal.goldGoal(10);
    }

    @Nested
    @DisplayName("Testing the goldGoal method with valid and invalid minimum gold number input")
    class testingGoldGoalMethod {
        @Test
        void goldGoalValidInput() {
            Assertions.assertTrue(goldGoal.goldGoal(20));
        }

        @Test
        void goldGoalInvalidInput() {
            Assertions.assertFalse(goldGoal.goldGoal(-20));
        }
    }

    @Nested
    @DisplayName("Testing the isFulfilled method with valid and invalid player, and valid and invalid gold value")
    class testingIsFulfilledMethod {
        @Test
        @DisplayName("Testing the isFulfilled method with a valid player with the required amount of gold")
        void isFulfilledValidPlayerFulfilledGold() {
            player.addGold(10);
            Assertions.assertTrue(goldGoal.isFulfilled(player));
        }

        @Test
        @DisplayName("Testing the isFulfilled method with a valid player but not the required amount of gold")
        void isFulfilledValidPlayerNotFulfilledGold() {
            Assertions.assertFalse(goldGoal.isFulfilled(player));
        }

        @Test
        @DisplayName("Testing the isFulfilled method with an invalid player with the required amount of gold")
        void isFulfilledInvalidPlayerFulfilledGold() {
            player.addGold(10);
            Assertions.assertFalse(goldGoal.isFulfilled(null));
        }

        @Test
        @DisplayName("Testing the isFulfilled method with an invalid player without the required amount of gold")
        void isFulfilledInvalidPlayerNotFulfilledGold() {
            Assertions.assertFalse(goldGoal.isFulfilled(null));
        }
    }
}