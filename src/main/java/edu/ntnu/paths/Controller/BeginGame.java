package edu.ntnu.paths.Controller;

import edu.ntnu.paths.Actions.*;
import edu.ntnu.paths.GameDetails.Game;
import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.Managers.GameManager;
import edu.ntnu.paths.StoryDetails.Link;
import edu.ntnu.paths.StoryDetails.Passage;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

public class BeginGame {

    private Game currentGame;
    private BorderPane root;
    private VBox topVBox, centerVbox;
    private AnchorPane bottomAnchorPane;
    private Button viewStatsButton;
    private Pane gameDiv;

    private ProgressBar healthBar;

    public void start(Stage stage) {
        currentGame = GameManager.getInstance().getGame();
        root = new BorderPane();
        root.setPadding(new Insets(10));
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/begin-game.css")).toExternalForm()
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
        Label header = new Label(currentGame.getStory().getTittle());
        header.setId("header");
        topVBox = new VBox(header);
        topVBox.setAlignment(Pos.CENTER);
    }

    private void createCentre() {
        centerVbox = new VBox();
        centerVbox.setAlignment(Pos.CENTER);

        Label goldLabel = new Label("Gold: ");
        Label scoreLabel = new Label("Score: ");
        Label inventoryLabel = new Label("Inventory: ");

        HBox labelsBox = new HBox(goldLabel, scoreLabel, inventoryLabel);
        labelsBox.setSpacing(10);
        labelsBox.setAlignment(Pos.CENTER);
        centerVbox.getChildren().add(labelsBox);

        healthBar = new ProgressBar();
        healthBar.setPrefWidth(200);
        healthBar.setProgress(currentGame.getPlayer().getHealth() / 100.0);
        setHealthBarColor(healthBar);
        centerVbox.getChildren().add(healthBar);

        gameDiv = new Pane();
        gameDiv.getStyleClass().add("game-div");
        gameDiv.setMinSize(800, 400);
        Button startGameButton = new Button("start game");
        startGameButton.setAlignment(Pos.CENTER);
        gameDiv.getChildren().add(startGameButton);
        startGameButton.setOnAction(this::startGame);
        centerVbox.getChildren().add(gameDiv);


    }

    private void startGame(ActionEvent actionEvent) {
        gameDiv.getChildren().clear();
        Passage startPassage = currentGame.begin();

        HBox passageBox = new HBox(10);
        passageBox.setPadding(new Insets(10));

        Label titleLabel = new Label(startPassage.getTittle());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label contentLabel = new Label(startPassage.getContent());
        contentLabel.setStyle("-fx-font-size: 14px;");
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(300);

        passageBox.getChildren().addAll(titleLabel, contentLabel);

        for (Link link : startPassage.getLinks()) {
            Label linkTextLabel = new Label(link.getText());
            linkTextLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Button linkButton = new Button(link.getReference());
            linkButton.setStyle("-fx-font-size: 14px;");

            VBox linkBox = new VBox(5);
            linkBox.setPadding(new Insets(5));

            linkBox.getChildren().addAll(linkTextLabel, linkButton);

            for (Action action : link.getActions()) {
                Label actionLabel = new Label(action.toString());
                actionLabel.setStyle("-fx-font-size: 12px; -fx-font-style: italic;");

                linkBox.getChildren().add(actionLabel);
            }

            linkButton.setOnAction(event -> {
                Passage newPassage = currentGame.go(link);

                Player player = currentGame.getPlayer();
                for (Action action : link.getActions()) {
                    if (action instanceof GoldAction) {
                        int goldAmount = ((GoldAction) action).getGold();
                        player.addGold(goldAmount);
                    } else if (action instanceof HealthAction) {
                        int healthAmount = ((HealthAction) action).getHealth();
                        player.changeHealth(healthAmount);
                    } else if (action instanceof ScoreAction) {
                        int scoreAmount = ((ScoreAction) action).getPoints();
                        player.addScore(scoreAmount);
                    } else if (action instanceof InventoryAction) {
                        String item = ((InventoryAction) action).getItem();
                        player.addToInventory(item);
                    }
                }

                loadPassage(newPassage);
            });

            passageBox.getChildren().add(linkBox);
        }

        gameDiv.getChildren().add(passageBox);
    }

    private void loadPassage(Passage passage) {
        gameDiv.getChildren().clear();
        setHealthBarColor(healthBar);

        HBox passageBox = new HBox(10);
        passageBox.setPadding(new Insets(10));

        Label titleLabel = new Label(passage.getTittle());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label contentLabel = new Label(passage.getContent());
        contentLabel.setStyle("-fx-font-size: 14px;");
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(300);
        passageBox.getChildren().addAll(titleLabel, contentLabel);

        for (Link link : passage.getLinks()) {
            Label linkTextLabel = new Label(link.getText());
            linkTextLabel.setStyle("-fx-font-size: 14px; -fx-font-weight:bold;");
            Button linkButton = new Button(link.getReference());
            linkButton.setStyle("-fx-font-size: 14px;");
            VBox linkBox = new VBox(5);
            linkBox.setPadding(new Insets(5));

            linkBox.getChildren().addAll(linkTextLabel, linkButton);

            for (Action action : link.getActions()) {
                Label actionLabel = new Label(action.toString());
                actionLabel.setStyle("-fx-font-size: 12px; -fx-font-style: italic;");
                linkBox.getChildren().add(actionLabel);
            }

            linkButton.setOnAction(event -> {
                Passage newPassage = currentGame.go(link);

                Player player = currentGame.getPlayer();
                for (Action action : link.getActions()) {
                    if (action instanceof GoldAction) {
                        int goldAmount = ((GoldAction) action).getGold();
                        player.addGold(goldAmount);
                    } else if (action instanceof HealthAction) {
                        int healthAmount = ((HealthAction) action).getHealth();
                        player.changeHealth(healthAmount);
                    } else if (action instanceof ScoreAction) {
                        int scoreAmount = ((ScoreAction) action).getPoints();
                        player.addScore(scoreAmount);
                    } else if (action instanceof InventoryAction) {
                        String item = ((InventoryAction) action).getItem();
                        player.addToInventory(item);
                    }
                }

                loadPassage(newPassage);
            });

            passageBox.getChildren().add(linkBox);
        }

        gameDiv.getChildren().add(passageBox);

    }

    private void setHealthBarColor(ProgressBar healthBar) {
        double health = currentGame.getPlayer().getHealth();

        double fillRatio = health / 100.0;

        if (health >= 50) {
            healthBar.setStyle("-fx-accent: green;");
        } else if (health >= 30) {
            healthBar.setStyle("-fx-accent: yellow;");
        } else {
            healthBar.setStyle("-fx-accent: red;");
        }

        healthBar.setProgress(fillRatio);
    }


    private void createBottom() {
        bottomAnchorPane = new AnchorPane();
        viewStatsButton = new Button("View Stats");
        viewStatsButton.setOnAction(this::handleViewStats);
        AnchorPane.setBottomAnchor(viewStatsButton, 20.0);
        AnchorPane.setRightAnchor(viewStatsButton, 50.0);

        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(this::handleGoBack);
        AnchorPane.setBottomAnchor(goBackButton, 20.0);
        AnchorPane.setLeftAnchor(goBackButton, 50.0);

        bottomAnchorPane.getChildren().addAll(viewStatsButton, goBackButton);
        root.setBottom(bottomAnchorPane);
    }

    private void handleViewStats(ActionEvent actionEvent) {
    }

    private void handleGoBack(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene
                ().getWindow();
        CreateGoals createGoals = new CreateGoals();
        try {
            createGoals.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}