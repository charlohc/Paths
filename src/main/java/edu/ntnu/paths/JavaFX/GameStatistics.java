package edu.ntnu.paths.JavaFX;

import edu.ntnu.paths.GameDetails.Game;
import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.Goals.*;
import edu.ntnu.paths.Managers.GameManager;
import javafx.scene.shape.Circle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The GameStatistics class displays the statistics of the current game played.
 */
public class GameStatistics {

    private BorderPane root;
    private VBox topVBox, centerVbox;
    private AnchorPane bottomAnchorPane;

    private Game currentGame;
    private Player player, playerBeforeGame;

    /**
     * Starts the game interface with the specified stage and current game.
     * @param stage the stage to display the game interface
     * @param currentGameCopy the copy of the current game
     */
    public void start(Stage stage, Game currentGameCopy) {
        currentGame = currentGameCopy;
        player = currentGameCopy.getPlayer();
        playerBeforeGame = GameManager.getInstance().getGame().getPlayer();
        root = new BorderPane();
        GoldGoal goldGoal = new GoldGoal();

        root.setPadding(new Insets(10));
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/style/game-statistics.css")).toExternalForm()
        );

        createTop();
        createCentre();
        createBottom();

        root.setTop(topVBox);
        root.setCenter(centerVbox);
        root.setBottom(bottomAnchorPane);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Created the top of the application
     * Contains information about the scene
     */
    private void createTop() {
        ImageView imageView = new ImageView(getClass().getResource("/edu/ntnu/paths/resources/img/help-button.png").toExternalForm());
        imageView.setFitWidth(40);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        AnchorPane imgAnchorPane = new AnchorPane();
        AnchorPane.setRightAnchor(imageView, 10.0);
        imgAnchorPane.getChildren().add(imageView);

        imgAnchorPane.setOnMouseClicked(this::handleHelpPage);

        Label header = new Label("Game Statistics");
        header.setId("headerLabel");
        Label content = new Label("Here you can see whether or not your goals were reached");
        content.setId("infoLabel");
        topVBox = new VBox(imgAnchorPane, header, content);
        topVBox.setAlignment(Pos.CENTER);
    }

    /**
     * Creates the centre of the application
     * Contains information about the different goals, and what values the player has obtained throughout the game
     */
    private void createCentre() {
        centerVbox = new VBox();
        HBox goalsBox = new HBox();
        centerVbox.setAlignment(Pos.CENTER);

        Label healthGoalLabel = new Label();
        Label scoreGoalLabel = new Label();
        Label goldGoalLabel = new Label();
        Label inventoryGoalLabel = new Label();

        if (currentGame.getGoals() == null) {
            Label noGoals = new Label("You had no goals for the game...");
            centerVbox.getChildren().add(noGoals);
        } else {
            healthGoalLabel = new Label("Health Goal: " + getGoalValueOfType(HealthGoal.class));
            scoreGoalLabel = new Label("Score Goal: " + getGoalValueOfType(ScoreGoal.class));
            goldGoalLabel = new Label("Gold Goal: " + getGoalValueOfType(GoldGoal.class));
            inventoryGoalLabel = new Label("Inventory Goal: " + getGoalValueOfType(InventoryGoal.class));
        }

        Label startHealthLabel = new Label("Start health: " + playerBeforeGame.getHealth());
        Label startGoldLabel = new Label("Start Gold: " + playerBeforeGame.getGold());

        Label finalHealthLabel = new Label("Final Health: " + currentGame.getPlayer().getHealth());
        Label scoreLabel = new Label("Final Score: " + currentGame.getPlayer().getScore());
        Label finalGoldLabel = new Label("Final Gold: " + currentGame.getPlayer().getGold());
        Label inventoryLabel = new Label("Final Inventory: " + currentGame.getPlayer().getInventory());

        VBox healthBox = createGoalBox(healthGoalLabel, startHealthLabel, finalHealthLabel, HealthGoal.class);
        healthBox.setId("healthBox");
        VBox scoreBox = createGoalBox(scoreGoalLabel, new Label(), scoreLabel, ScoreGoal.class);
        scoreBox.setId("scoreBox");
        VBox goldBox = createGoalBox(goldGoalLabel, startGoldLabel, finalGoldLabel, GoldGoal.class);
        goldBox.setId("goldBox");
        VBox inventoryBox = createGoalBox(inventoryGoalLabel, new Label(), inventoryLabel, InventoryGoal.class);
        inventoryBox.setId("inventoryBox");

        goalsBox.getChildren().addAll(healthBox, scoreBox, goldBox, inventoryBox);
        goalsBox.setSpacing(30);
        goalsBox.setId("goalsBox");
        goalsBox.setAlignment(Pos.CENTER);

        if (currentGame.getGoals() != null) {
            Label fulfilledGoalsLabel = new Label("Fulfilled Goals: ");
            int fulfilledGoalsCount = 0;

            if (currentGame.getGoals() != null) {
                for (Goal goal : currentGame.getGoals()) {
                    if (goal.isFulfilled(player)) {
                        fulfilledGoalsCount++;
                    }
                }
            }


            Label fulfilledCountLabel = new Label(String.valueOf(fulfilledGoalsCount));

            VBox fulfilledGoalsBox = new VBox(fulfilledGoalsLabel, fulfilledCountLabel);
            fulfilledGoalsBox.setAlignment(Pos.CENTER);

            centerVbox.getChildren().addAll(goalsBox, fulfilledGoalsBox);
            VBox.setMargin(goalsBox, new Insets(10));
            VBox.setMargin(fulfilledGoalsBox, new Insets(10));
        } else {
            centerVbox.getChildren().addAll(goalsBox);
            VBox.setMargin(goalsBox, new Insets(10));
        }
    }

    /**
     * Returns the value of a specific goal type in the current game.
     * @param goalType the class object representing the goal type
     * @return the value of the goal type, or an empty string if the goal type is not found
     */
    private String getGoalValueOfType(Class<? extends Goal> goalType) {
        for (Goal goal : currentGame.getGoals()) {
            if (goalType.isInstance(goal)) {
                if (goal instanceof HealthGoal healthGoal) {
                    return String.valueOf(healthGoal.getHealth());
                } else if (goal instanceof ScoreGoal scoreGoal) {
                    return String.valueOf(scoreGoal.getScore());
                } else if (goal instanceof GoldGoal goldGoal) {
                    return String.valueOf(goldGoal.getGold());
                } else if (goal instanceof InventoryGoal inventoryGoal) {
                    return String.valueOf(inventoryGoal.getInventory());
                }
            }
        }
        return "";
    }

    /**
     * Creates a VBox containing the goal information.
     * @param goalLabel the label displaying the goal name
     * @param startValueLabel the label displaying the starting value of the goal
     * @param valueLabel the label displaying the current value of the goal
     * @param goalType the class object representing the goal type
     * @return the VBox containing the goal information
     */
    private VBox createGoalBox(Label goalLabel, Label startValueLabel, Label valueLabel, Class<? extends Goal> goalType) {
        VBox goalBox = new VBox();
        goalBox.setAlignment(Pos.CENTER);

        Circle circle = new Circle(40);
        circle.setId("circle");
        circle.setFill(getGoalBackgroundColor(goalType));

        ImageView actionImageView = getActionImageView(goalType);
        actionImageView.setFitWidth(35);
        actionImageView.setPreserveRatio(true);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(circle, actionImageView);

        goalBox.getChildren().addAll(stackPane, goalLabel, startValueLabel, valueLabel);
        VBox.setMargin(goalLabel, new Insets(5));
        VBox.setMargin(startValueLabel, new Insets(5));
        VBox.setMargin(valueLabel, new Insets(5));

        return goalBox;
    }

    /**
     * Returns the background color for a goal based on its fulfillment status.
     * @param goalType the class object representing the goal type
     * @return the background color for the goal
     */
    private Paint getGoalBackgroundColor(Class<? extends Goal> goalType) {
        boolean isFulfilled = false;
        if (currentGame.getGoals() != null) {
            for (Goal goal : currentGame.getGoals()) {
                if (goalType.isInstance(goal)) {
                    if (goal.isFulfilled(player)) {
                        isFulfilled = true;
                        break;
                    }
                }
            }
        }
        return isFulfilled ? Color.LIGHTGREEN : Color.LIGHTBLUE;
    }

    /**
     * Returns the ImageView representing the action associated with a specific goal type.
     * @param goalType the class object representing the goal type
     * @return the ImageView representing the action associated with the goal type, or null if not found
     */
    private ImageView getActionImageView(Class<? extends Goal> goalType) {
        Image actionImage;
        if (goalType == HealthGoal.class) {
            actionImage = new Image(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/heart.png")).toExternalForm());
        } else if (goalType == ScoreGoal.class) {
            actionImage = new Image(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/trophy.png")).toExternalForm());
        } else if (goalType == GoldGoal.class) {
            actionImage = new Image(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/coin.png")).toExternalForm());
        } else if (goalType == InventoryGoal.class) {
            actionImage = new Image(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/bag.png")).toExternalForm());
        } else {
            actionImage = null;
        }

        return new ImageView(actionImage);
    }

    /**
     * Creates the bottom of the scene
     * Containing buttons to maneuver to other scenes
     */
    private void createBottom() {
        bottomAnchorPane = new AnchorPane();

        Button goBackButton = new Button("Go back");
        goBackButton.setOnAction(this::handleGoBack);
        AnchorPane.setBottomAnchor(goBackButton, 20.0);
        AnchorPane.setLeftAnchor(goBackButton, 50.0);

        bottomAnchorPane.getChildren().addAll(goBackButton);
        root.setBottom(bottomAnchorPane);

    }

    /**
     * Handles the action event when the "Go Back" button is clicked.
     * @param actionEvent the action event triggered by clicking the "Go Back" button
     */
    private void handleGoBack(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        BeginGame beginGame = new BeginGame();
        try {
            beginGame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action event when the "Information" button is clicked.
     * @param event the action event triggered by clicking the "Information" button
     */
    private void handleHelpPage(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        HelpPage helpPage = new HelpPage();
        helpPage.displayPopUp(stage);
    }


}

