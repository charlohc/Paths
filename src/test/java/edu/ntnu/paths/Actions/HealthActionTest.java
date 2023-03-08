package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class HealthActionTest {

    HealthAction healthAction;
    Player player;

    @BeforeEach
    void setUp() {
        healthAction = new HealthAction();

        player = new Player("Kari", 100,10,10);
    }

    @Nested
    @DisplayName("Testing the gold action method with valid and invalid number input")
    class testingGoldActionMethod {
        @Test
        void healthActionValidInput() {
            Assertions.assertTrue(healthAction.healthAction(10));
        }

        @Test
        void healthActionInvalidInput() {
            Assertions.assertFalse(healthAction.healthAction(-10));
        }
    }

    @Nested
    @DisplayName("Testing the execute method with valid and invalid player and testing for correct value")
    class testingExecuteMethod {
        @Test
        void executeValidPlayer() {
            healthAction.healthAction(10);
            Assertions.assertTrue(healthAction.execute(player));
        }

        @Test
        void executeGivesCorrectHealthValue() {
            int previousHealthPlayer = player.getHealth();
            healthAction.healthAction(10);
            healthAction.execute(player);
            assertEquals(previousHealthPlayer + 10, player.getHealth());
        }

        @Test
        void executeInvalidPlayer() {
            Assertions.assertFalse(healthAction.execute(null));
        }
    }
}