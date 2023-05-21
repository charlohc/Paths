package edu.ntnu.paths.GameDetails;

import edu.ntnu.paths.Actions.*;
import edu.ntnu.paths.Goals.*;
import edu.ntnu.paths.StoryDetails.*;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;
    Player player;
    Passage openingPassage, porchPassage;
    Story story;

    GoldGoal goldGoal;

    HealthGoal healthGoal;

    InventoryGoal inventoryGoal;

    ScoreGoal scoreGoal;

    HealthAction healthAction;
    GoldAction goldAction;

    InventoryAction inventoryAction;
    List<Goal> goals;

    List<String> inventory;
    Link linkToPorchPassage, linkToNowhere;

    @BeforeEach
    void setUp() {
        goldGoal = new GoldGoal();

        healthGoal = new HealthGoal();

        inventoryGoal = new InventoryGoal();

        scoreGoal = new ScoreGoal();

        goals = new ArrayList<>();

        healthAction = new HealthAction();

        healthAction.healthAction(10);

        goldAction = new GoldAction();

        goldAction.goldAction(10);

        inventoryAction = new InventoryAction();

        inventoryAction.inventoryAction("sword");


        player = PlayerBuilder.newInstance()
                .setName("Kari")
                .setHealth(50)
                .setGold(5)
                .setScore(10)
                .build();

        openingPassage = PassageBuilder.newInstance()
                .setTitle("Beginning")
                .setContent("You woke up and felt at once all alone")
                .build();

        porchPassage = PassageBuilder.newInstance()
                .setTitle("The porch")
                .setContent("There are no people outside, and its all so quite")
                .build();


        story = StoryBuilder.newInstance()
                .setTitle("The last person on earth")
                .setOpeningPassage(openingPassage)
                .build();

        story.addPassage(openingPassage);

        linkToPorchPassage = LinkBuilder.newInstance()
                .setText("Go outside")
                .setReference("The porch")
                .build();

        linkToNowhere = LinkBuilder.newInstance()
                .setText("text")
                .setReference("nowhere")
                .build();


        openingPassage.addLink(linkToPorchPassage);


        story.addPassage(porchPassage);


        linkToPorchPassage.addAction(healthAction);

        linkToPorchPassage.addAction(goldAction);

        linkToPorchPassage.addAction(inventoryAction);


        goldGoal.goldGoal(10);

        healthGoal.healthGoal(70);

        scoreGoal.scoreGoal(35);

        inventory = new ArrayList<>();
        inventory.add("sword");
        inventoryGoal.inventoryGoal(inventory);

        goals.add(goldGoal);
        goals.add(healthGoal);
        goals.add(scoreGoal);
        goals.add(inventoryGoal);

        game = GameBuilder.newInstance()
                .setPlayer(new Player(player))
                .setStory(new Story(story))
                .setGoals(new ArrayList<>(goals))
                .build();


    }

    @Nested
    @DisplayName("Testing Game object constructor with invalid fields")
    class creatingInvalidGameObject {

        @Test
        void gameWithoutPlayer() {
            assertThrows(NullPointerException.class, () -> {
                Game gameWithoutPlayer = GameBuilder.newInstance()
                        .setPlayer(null)
                        .setStory(story)
                        .setGoals(goals)
                        .build();
            });
        }

        @Test
        void gameWithoutStory() {
            assertThrows(NullPointerException.class, () -> {
                Game gameWithOutStory = GameBuilder.newInstance()
                        .setPlayer(player)
                        .setStory(null)
                        .setGoals(goals)
                        .build();
            });
        }
    }

    @Nested
    @DisplayName("Testing the get methods of the game class")
    class testingGameGetMethods {
        @Test
        void getPlayer() {
            assertEquals(player.getName(), game.getPlayer().getName());
        }

        @Test
        void getStory() {
            Assertions.assertEquals(story.getTittle(), game.getStory().getTittle());
        }

        @Test
        void getGoals() {
            assertEquals(goals, game.getGoals());
        }
    }

    @Nested
    @DisplayName("Testing the game methods with valid input")
    class testingGameMethodsValidInput {
        @Test
        void begin() {
            Assertions.assertEquals(openingPassage, game.begin());
        }

        @Test
        void go() {
            Assertions.assertEquals(porchPassage, game.go(linkToPorchPassage));
        }
    }

    @Nested
    @DisplayName("Testing the game methods with invalid input")
    class testingGameMethodsInvalidInput {

        @Test
        void go() {
            Assertions.assertNull(game.go(linkToNowhere));
        }
    }

    @Nested
    @DisplayName("Testing action methods affect player when moving between passages")
    class actionMethodsWorking {
        @Test
        void correctValuesPlayerAfterChangingPassage() {
            game.go(linkToPorchPassage);
            Player playerAfterChangingPassage = new Player(player);

            for (Action action : linkToPorchPassage.getActions()) {
                if (action instanceof GoldAction goldAction) {
                    playerAfterChangingPassage.addGold(goldAction.getGold());
                } else if (action instanceof HealthAction healthAction) {
                    playerAfterChangingPassage.changeHealth(healthAction.getHealth());
                } else if (action instanceof ScoreAction scoreAction) {
                    playerAfterChangingPassage.addScore(scoreAction.getPoints());
                } else if (action instanceof InventoryAction inventoryAction) {
                    playerAfterChangingPassage.addToInventory(inventoryAction.getItem());
                }
            }

            Assertions.assertEquals(player.getGold() + 10, playerAfterChangingPassage.getGold());
            Assertions.assertEquals(player.getHealth() + 10 , playerAfterChangingPassage.getHealth());
        }

        @Test
        void CorrectInventoryAfterChangingPassage() {
            game.go(linkToPorchPassage);
            Player playerAfterChangingPassage = new Player(player);

            for (Action action : linkToPorchPassage.getActions()) {
                if (action instanceof GoldAction goldAction) {
                    playerAfterChangingPassage.addGold(goldAction.getGold());
                } else if (action instanceof HealthAction healthAction) {
                    playerAfterChangingPassage.changeHealth(healthAction.getHealth());
                } else if (action instanceof ScoreAction scoreAction) {
                    playerAfterChangingPassage.addScore(scoreAction.getPoints());
                } else if (action instanceof InventoryAction inventoryAction) {
                    playerAfterChangingPassage.addToInventory(inventoryAction.getItem());
                }
            }

            Assertions.assertEquals(new ArrayList<String>(), player.getInventory());
            Assertions.assertEquals(inventory, playerAfterChangingPassage.getInventory());
        }
    }

    @Nested
    @DisplayName("Testing the goals methods")
    class goalMethodsWorking {

        @Test
        void goalFulfilledGoldHealth(){
            game.go(linkToPorchPassage);
            Player playerAfterChangingPassage = new Player(player);

            for (Action action : linkToPorchPassage.getActions()) {
                if (action instanceof GoldAction goldAction) {
                    playerAfterChangingPassage.addGold(goldAction.getGold());
                } else if (action instanceof HealthAction healthAction) {
                    playerAfterChangingPassage.changeHealth(healthAction.getHealth());
                } else if (action instanceof ScoreAction scoreAction) {
                    playerAfterChangingPassage.addScore(scoreAction.getPoints());
                } else if (action instanceof InventoryAction inventoryAction) {
                    playerAfterChangingPassage.addToInventory(inventoryAction.getItem());
                }
            }
            Assertions.assertFalse(goldGoal.isFulfilled(player));
            Assertions.assertTrue(goldGoal.isFulfilled(playerAfterChangingPassage));
        }

        @Test
        void goalFulfilledInventory(){
            game.go(linkToPorchPassage);
            Player playerAfterChangingPassage = new Player(player);

            for (Action action : linkToPorchPassage.getActions()) {
                if (action instanceof GoldAction goldAction) {
                    playerAfterChangingPassage.addGold(goldAction.getGold());
                } else if (action instanceof HealthAction healthAction) {
                    playerAfterChangingPassage.changeHealth(healthAction.getHealth());
                } else if (action instanceof ScoreAction scoreAction) {
                    playerAfterChangingPassage.addScore(scoreAction.getPoints());
                } else if (action instanceof InventoryAction inventoryAction) {
                    playerAfterChangingPassage.addToInventory(inventoryAction.getItem());
                }
            }
            Assertions.assertFalse(inventoryGoal.isFulfilled(player));
            Assertions.assertTrue(inventoryGoal.isFulfilled(playerAfterChangingPassage));
        }
    }

}