package edu.ntnu.paths.Controller;

import edu.ntnu.paths.GameDetails.Game;
import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.Goals.*;
import edu.ntnu.paths.Managers.GameManager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

public class GameStatistics {

    private BorderPane root;
    private VBox topVBox, centerVbox;
    private AnchorPane bottomAnchorPane;

    private Game currentGame;
    private Player player;


    public void start(Stage stage, Game currentGameCopy) {
        currentGame = currentGameCopy;
        player = currentGameCopy.getPlayer();
        root = new BorderPane();
        root.setPadding(new Insets(10));
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/style/game-statistics.css")).toExternalForm()
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

    private void createTop() {
        ImageView imageView = new ImageView(getClass().getResource("/edu/ntnu/paths/Controller/img/help-button.png").toExternalForm());
        imageView.setFitWidth(40);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        AnchorPane imgAnchorPane = new AnchorPane();
        AnchorPane.setRightAnchor(imageView, 10.0);
        imgAnchorPane.getChildren().add(imageView);

        imgAnchorPane.setOnMouseClicked(this::handleHelpPage);

        Label header = new Label("Game Statistics");
        header.setId("headerLabel");
        Label content = new Label("Here you can see whether or not your goals was reached");
        content.setId("infoLabel");
        topVBox = new VBox(imgAnchorPane, header, content);
        topVBox.setAlignment(Pos.CENTER);
    }

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
        Label healthLabel = new Label("Health: " + currentGame.getPlayer().getHealth());
        Label scoreLabel = new Label("Score: " + currentGame.getPlayer().getScore());
        Label goldLabel = new Label("Gold: " + currentGame.getPlayer().getGold());
        Label inventoryLabel = new Label("Inventory: " + currentGame.getPlayer().getInventory());

        VBox healthBox = new VBox(healthGoalLabel, healthLabel);
        healthBox.setId("healthBox");
        VBox scoreBox = new VBox(scoreGoalLabel, scoreLabel);
        scoreBox.setId("scoreBox");
        VBox goldBox = new VBox(goldGoalLabel, goldLabel);
        goldBox.setId("goldBox");
        VBox inventoryBox = new VBox(inventoryGoalLabel, inventoryLabel);
        inventoryBox.setId("inventoryBox");

        goalsBox.getChildren().addAll(healthBox, scoreBox, goldBox, inventoryBox);
        goalsBox.setSpacing(20);
        goalsBox.setAlignment(Pos.CENTER);

        Label fulfilledGoalsLabel = new Label("Fulfilled Goals: ");
        int fulfilledGoalsCount = 0;

        if (currentGame.getGoals() !=null) {
        for (Goal goal : currentGame.getGoals()) {
            if (goal instanceof HealthGoal healthGoal && healthGoal.isFulfilled(player)) {
                fulfilledGoalsCount++;
            } else if (goal instanceof ScoreGoal scoreGoal && scoreGoal.isFulfilled(player)) {
                fulfilledGoalsCount++;
            } else if (goal instanceof GoldGoal goldGoal && goldGoal.isFulfilled(player)) {
                fulfilledGoalsCount++;
            } else if (goal instanceof InventoryGoal inventoryGoal && inventoryGoal.isFulfilled(player)) {
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
    }

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




    private void createBottom() {
        bottomAnchorPane = new AnchorPane();

        Button goBackButton = new Button("Go back");
        goBackButton.setOnAction(this::handleGoBack);
        AnchorPane.setBottomAnchor(goBackButton, 20.0);
        AnchorPane.setLeftAnchor(goBackButton, 50.0);

        bottomAnchorPane.getChildren().addAll(goBackButton);
        root.setBottom(bottomAnchorPane);

    }

    private void handleGoBack(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        BeginGame beginGame = new BeginGame();
        try {
            beginGame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handleHelpPage(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        HelpPage helpPage = new HelpPage();
        helpPage.displayPopUp(stage);
    }


}

