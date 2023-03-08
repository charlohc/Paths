package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class InventoryActionTest {

    InventoryAction inventoryAction;
    Player player;

    @BeforeEach
    void setUp() {
        inventoryAction = new InventoryAction();

        player = new Player("Kari", 100,10,10);
    }

    @Nested
    @DisplayName("Testing the inventory action method with valid and invalid score inventory input")
    class testingInventoryActionMethod {
        @Test
        void inventoryActionValidInput() {
            Assertions.assertTrue(inventoryAction.inventoryAction("axe"));
        }

        @Test
        void inventoryActionInvalidInput() {
            Assertions.assertFalse(inventoryAction.inventoryAction(""));
        }
    }

    @Nested
    @DisplayName("Testing the execute method with valid and invalid player and testing for correct inventory value")
    class testingExecuteMethod {
        @Test
        void executeValidPlayer() {
            inventoryAction.inventoryAction("axe");
            Assertions.assertTrue(inventoryAction.execute(player));
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
            Assertions.assertFalse(inventoryAction.execute(null));
        }
    }
}