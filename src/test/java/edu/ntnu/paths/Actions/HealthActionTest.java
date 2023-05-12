package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.GameDetails.PlayerBuilder;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class HealthActionTest {

    HealthAction healthAction;
    Player player;

    @BeforeEach
    void setUp() {
        healthAction = new HealthAction();

        player = PlayerBuilder.newInstance()
                .setName("Kari")
                .setHealth(50)
                .setGold(10)
                .setScore(10)
                .build();
    }

    @Test
    void getMethod() {
        healthAction.healthAction(50);
        Assertions.assertEquals(50, healthAction.getHealth());
    }


    @Nested
    @DisplayName("Testing the gold action method with valid and invalid number input")
    class testingGoldActionMethod {
        @Test
        void healthActionValidInput() {
            assertTrue(healthAction.healthAction(10));
        }

        @Test
        void healthActionInvalidInput() {
            assertFalse(healthAction.healthAction(-10));
        }
    }

    @Nested
    @DisplayName("Testing the execute method with valid and invalid player and testing for correct value")
    class testingExecuteMethod {
        @Test
        void executeValidPlayer() {
            healthAction.healthAction(10);
            assertTrue(healthAction.execute(player));
        }

        @Test
        void executeGivesCorrectHealthValue() {
            int previousHealthPlayer = player.getHealth();
            healthAction.healthAction(10);
            healthAction.execute(player);
            assertEquals(previousHealthPlayer - 10, player.getHealth());
        }

        @Test
        void executeInvalidPlayer() {
            assertFalse(healthAction.execute(null));
        }
    }
}