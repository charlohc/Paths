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

    Link linkToPorchPassage;

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

        openingPassage = new Passage("Beginning", "You woke up and felt at once all alone");

        porchPassage = new Passage("The porch", "There are no people outside, and its all so quite");


        story = StoryBuilder.newInstance()
                    .setTitle("The last person on earth")
                    .setOpeningPassage(openingPassage)
                    .build();

        story.addPassage(openingPassage);

        linkToPorchPassage = LinkBuilder.newInstance()
                .setText("Go outside")
                .setReference("The porch")
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

        game = new Game(new Player(player),story, new ArrayList<>(goals));


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
            Assertions.assertEquals(player.toString(),game.getPlayer().toString());
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
    @DisplayName("Testing the game methods")
    class testingGameMethods {
        @Test
        void begin() {
            Assertions.assertEquals(openingPassage, game.begin());
        }

        @Test
        void go() {
            /*System.out.println(game.getStory().getPassages());*/


            //Spillet skal ha to passages, beginning skal ha en link til porch, når søker på pasasje basert på link skal
            //få opp porch siden det er neste "sene"

            //Jeg har link fra openingPassage til prochPassage

            //Den som referer har referansen i links hvor referansen i linken matcher tittel i passage objektet.



           System.out.println(game.getStory().getPassages());
            System.out.println();
            System.out.println();
            System.out.println(game.go(linkToPorchPassage));



        }
    }
}