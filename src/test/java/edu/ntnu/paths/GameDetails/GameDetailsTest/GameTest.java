package edu.ntnu.paths.GameDetails.GameDetailsTest;

import edu.ntnu.paths.Actions.HealthAction;
import edu.ntnu.paths.GameDetails.Game;
import edu.ntnu.paths.GameDetails.Player;
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


        player = new Player("Kari", 100, 10,10);

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

        //this is what need extra security, player, story and list of goals
        game = new Game(new Player(player), new Story(story), new ArrayList<>(goals));



    }
    @Nested
    @DisplayName("Testing Game object constructor with invalid fields")
    class creatingInvalidGameObject {

        @Test
        void gameWithoutPlayer() {
           assertThrows(NullPointerException.class, () -> {
                Game gameWithoutPlayer = new Game(null,story,goals);
            });
        }

        @Test
        void gameWithoutStory() {
            assertThrows(NullPointerException.class, () -> {
                Game gameWithOutStory = new Game(player,null,goals);
            });
        }

        @Test
        void gameWithoutGoals() {
            assertThrows(NullPointerException.class, () -> {
                Game gameWithOutStory = new Game(player,story,new ArrayList<Goal>());
            });
        }
    }

    @Nested
    @DisplayName("Testing the get methods of the game class")
    class testingGameGetMethods {
        @Test
        void getPlayer() {
            Assertions.assertEquals(player.getName(),game.getPlayer().getName());
        }

        //ok to test this way vs with whole object
        @Test
        void getStory() {
            Assertions.assertEquals(story.getTittle(), game.getStory().getTittle());
        }

        @Test
        void getGoals() {
            Assertions.assertEquals(goals, game.getGoals());
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