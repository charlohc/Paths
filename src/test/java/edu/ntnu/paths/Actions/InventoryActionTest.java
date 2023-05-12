package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.GameDetails.PlayerBuilder;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class InventoryActionTest {

    InventoryAction inventoryAction;
    Player player;

    @BeforeEach
    void setUp() {
        inventoryAction = new InventoryAction();

        player = PlayerBuilder.newInstance()
                .setName("Kari")
                .setHealth(50)
                .setGold(10)
                .setScore(10)
                .build();
    }

    @Test
    void getMethod() {
        inventoryAction.inventoryAction("axe");
        Assertions.assertEquals("axe", inventoryAction.getItem());
    }

    @Nested
    @DisplayName("Testing the inventory action method with valid and invalid score inventory input")
    class testingInventoryActionMethod {
        @Test
        void inventoryActionValidInput() {
            assertTrue(inventoryAction.inventoryAction("axe"));
        }

        @Test
        void inventoryActionInvalidInput() {
            assertFalse(inventoryAction.inventoryAction(""));
        }
    }

    @Nested
    @DisplayName("Testing the execute method with valid and invalid player and testing for correct inventory value")
    class testingExecuteMethod {
        @Test
        void executeValidPlayer() {
            inventoryAction.inventoryAction("axe");
            assertTrue(inventoryAction.execute(player));
        }

        @Test
        void executeGivesCorrectHealthValue() {
            String inventory = "axe";
            inventoryAction.inventoryAction(inventory);
            inventoryAction.execute(player);
            assertEquals(inventory, player.getInventory().get(0));
        }

        @Test
        void executeInvalidPlayer() {
            assertFalse(inventoryAction.execute(null));
        }
    }
}