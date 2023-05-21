package edu.ntnu.paths.Goals;

import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.GameDetails.PlayerBuilder;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;

class InventoryGoalTest {
    InventoryGoal inventoryGoal;

    List<String> mandatoryItems;
    Player player;

    @BeforeEach
    void setUp() {
        inventoryGoal = new InventoryGoal();

        player = PlayerBuilder.newInstance()
                .setName("Kari")
                .setHealth(50)
                .setGold(10)
                .setScore(10)
                .build();

        mandatoryItems = new ArrayList<>();
        mandatoryItems.add("axe");
        mandatoryItems.add("key");
        inventoryGoal.inventoryGoal(mandatoryItems);

    }
    @Nested
    @DisplayName("Testing the isFulfilled method with valid and invalid player, and valid and invalid inventory list")
    class testingIsFulfilledMethod {
        @Test
        @DisplayName("Testing the isFulfilled method with a valid player with the required inventory list")
        void isFulfilledValidPlayerFulfilledInventory() {
            player.addToInventory("axe");
            player.addToInventory("key");

            Assertions.assertTrue(inventoryGoal.isFulfilled(player));
        }

        @Test
        @DisplayName("Testing the isFulfilled method with a valid player but not the required inventory list")
        void isFulfilledValidPlayerNotFulfilledInventory() {
            Assertions.assertFalse(inventoryGoal.isFulfilled(player));
        }

        @Test
        @DisplayName("Testing the isFulfilled method with an invalid player, with the required inventory list")
        void isFulfilledInvalidPlayerFulfilledInventory() {
            player.addToInventory("axe");
            player.addToInventory("key");

            Assertions.assertFalse(inventoryGoal.isFulfilled(null));
        }

        @Test
        @DisplayName("Testing the isFulfilled method with an invalid player without the required inventory list")
        void isFulfilledInvalidPlayerNotFulfilledInventory() {
            Assertions.assertFalse(inventoryGoal.isFulfilled(null));
        }
    }

}