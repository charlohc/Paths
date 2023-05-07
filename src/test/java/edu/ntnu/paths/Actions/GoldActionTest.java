package edu.ntnu.paths.Actions;

import edu.ntnu.paths.GameDetails.Player;
import org.junit.jupiter.api.*;

class GoldActionTest {

    GoldAction goldAction;
    Player player;

    @BeforeEach
    void setUp() {
        goldAction = new GoldAction();

        player = new Player("Kari", 100,10,10);
    }

    @Nested
    @DisplayName("Testing the gold action method with valid and invalid number input")
    class testingGoldActionMethod {
        @Test
        void goldActionValidInput() {
            Assertions.assertTrue(goldAction.goldAction(10));
        }

        @Test
        void goldActionInvalidInput() {
            Assertions.assertFalse(goldAction.goldAction(-10));
        }
    }

    @Nested
    @DisplayName("Testing the execute method with valid and invalid player and testing for correct value")
    class testingExecuteMethod {
        @Test
        void executeValidPlayer() {
            goldAction.goldAction(10);
            Assertions.assertTrue(goldAction.execute(player));
        }

        @Test
        void executeGivesCorrectGoldValue() {
            int previousGoldValuePlayer = player.getGold();
            goldAction.goldAction(10);
            goldAction.execute(player);
            Assertions.assertEquals(previousGoldValuePlayer + 10, player.getGold());
        }

        @Test
        void executeInvalidPlayer() {
            Assertions.assertFalse(goldAction.execute(null));
        }
    }
}