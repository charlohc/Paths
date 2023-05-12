package edu.ntnu.paths.GameDetails;

import edu.ntnu.paths.Actions.HealthAction;
import edu.ntnu.paths.Goals.*;
import edu.ntnu.paths.StoryDetails.*;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//need to do more test containing action and goal, make sure that action and goal are changing between passages
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
    List<Goal> goals;

    Link linkToPorchPassage, linkToNowhere;

    @BeforeEach
    void setUp(){
        goldGoal = new GoldGoal();

        healthGoal = new HealthGoal();

        inventoryGoal = new InventoryGoal();

        scoreGoal = new ScoreGoal();

        goals = new ArrayList<>();

        healthAction = new HealthAction();

        healthAction.healthAction(10);


        player = PlayerBuilder.newInstance()
                .setName("Kari")
                .setHealth(50)
                .setGold(10)
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


        goldGoal.goldGoal(10);

        healthGoal.healthGoal(70);

        scoreGoal.scoreGoal(35);

        List<String> inventory = new ArrayList<>();
        inventory.add("axe");
        inventory.add("key");
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
            assertEquals(player.getName(),game.getPlayer().getName());
        }

        //ok to test this way vs with whole object
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
}