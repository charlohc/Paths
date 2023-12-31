package edu.ntnu.paths.GameDetails;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerTest {
    Player originalPlayer, copyPlayer;

    @BeforeEach
    void setUp() {
       originalPlayer = PlayerBuilder.newInstance()
               .setName("Kari")
               .setHealth(50)
               .setGold(10)
               .setScore(10)
               .build();

       copyPlayer = new Player(originalPlayer);

   }

    @Nested
    @DisplayName("Testing player object constructor with invalid fields")
    class creatingInvalidPlayerObject {

        @Test
        void playerWithoutName() {
            assertThrows(NullPointerException.class, () -> {
                Player playerWithOutName = PlayerBuilder.newInstance()
                        .setName("")
                        .setHealth(100)
                        .setGold(10)
                        .setScore(10)
                        .build();
            });
        }
        @Test
        void playerWithNegativeHealth() {
            assertThrows(IllegalArgumentException.class, () -> {
                Player playerWithZeroHealth = PlayerBuilder.newInstance()
                        .setName("Kari")
                        .setHealth(-10)
                        .setGold(10)
                        .setScore(10)
                        .build();
            });
        }

        @Test
        void playerWithNegativeScore() {
            assertThrows(IllegalArgumentException.class, () -> {
                Player playerWithNegativeScore = PlayerBuilder.newInstance()
                        .setName("Kari")
                        .setHealth(100)
                        .setGold(10)
                        .setScore(-10)
                        .build();
            });
        }

        @Test
        void playerWithNegativeGold() {
            assertThrows(IllegalArgumentException.class, () -> {
                Player playerWithNegativeGold= PlayerBuilder.newInstance()
                        .setName("Kari")
                        .setHealth(100)
                        .setGold(-10)
                        .setScore(10)
                        .build();
            });
        }

    }

    @Nested
    @DisplayName("Testing the security of the player object")
    class securePlayerObject {

        @Test
        @DisplayName("Test that the original player object and copy object get different addresses")
        void differentObjectDifferentAddress() {
            Assertions.assertNotEquals(originalPlayer, copyPlayer);
        }

        @Test
        @DisplayName("Test that the original player object and copy object contains the same data, for instance name")
        void differentObjectSameData() {
            Assertions.assertEquals(originalPlayer.getName(), copyPlayer.getName());
        }
    }

    @Nested
    @DisplayName("Testing the get methods")
    class validGetMethods {
        @Test
        void getName() {
            Assertions.assertEquals("Kari", copyPlayer.getName());
        }

        @Test
        void getHealth() {
            Assertions.assertEquals(50, copyPlayer.getHealth());
        }

        @Test
        void getGold() {
            Assertions.assertEquals(10, copyPlayer.getGold());
        }

        @Test
        void getScore() {
            Assertions.assertEquals(10, copyPlayer.getGold());
        }

        @Test
        void getInventory() {
            Assertions.assertEquals(0, copyPlayer.getInventory().size());

        }
    }

    @Nested
    @DisplayName("Testing the adding methods with valid input")
    class validAddMethods {
        @Test
        void addScore() {
            int previousScore = copyPlayer.getScore();
            copyPlayer.addScore(10);
            Assertions.assertEquals(previousScore + 10, copyPlayer.getScore());
        }

        @Test
        void addGold() {
            int previousGold = copyPlayer.getGold();
            copyPlayer.addGold(10);
            Assertions.assertEquals(previousGold + 10, copyPlayer.getGold());
        }

        @Test
        void addHealth() {
            int previousHealth = copyPlayer.getHealth();
            copyPlayer.changeHealth(25);
            Assertions.assertEquals(previousHealth + 25, copyPlayer.getHealth());
        }


        @Test
        void addToInventory() {
            copyPlayer.addToInventory("Axe");
            copyPlayer.addToInventory(" key ");

            List<String> inventory = new ArrayList<>();
            inventory.add("axe");
            inventory.add("key");
            Assertions.assertEquals(inventory, copyPlayer.getInventory());
        }
    }

    @Nested
    @DisplayName("Testing the adding methods with invalid input")
    class invalidAddMethods {
        @Test
        void addScore() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> copyPlayer.addScore(-1));
        }

        @Test
        void addGold() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> copyPlayer.addGold(-1));
        }
        @Test
        void addHealth() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> copyPlayer.addGold(-1));

        }

        @Test
        void addToInventory() {
            Assertions.assertThrows(NullPointerException.class, () -> copyPlayer.addToInventory(""));

        }
    }
}