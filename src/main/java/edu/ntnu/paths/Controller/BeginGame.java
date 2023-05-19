package edu.ntnu.paths.Controller;

import edu.ntnu.paths.Actions.*;
import edu.ntnu.paths.GameDetails.Game;
import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.Managers.GameManager;
import edu.ntnu.paths.StoryDetails.Link;
import edu.ntnu.paths.StoryDetails.Passage;
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
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Objects;

public class BeginGame {

    private Game currentGame, currentGameCopy;
    private Player player;
    private BorderPane root;
    private VBox topVBox, centerVbox;
    private HBox goalsGame;
    private AnchorPane bottomAnchorPane;
    private Button viewStatsButton;
    private StackPane gameDiv;
    private Label healthLabel, goldLabel, scoreLabel, inventoryLabel, titleLabel, contentLabel;
    private ProgressBar healthBar;

    public void start(Stage stage) {
        currentGame = GameManager.getInstance().getGame();
        currentGameCopy = new Game(currentGame);

        player = currentGameCopy.getPlayer();
        player.setInventory(new ArrayList<>());
        root = new BorderPane();
        goalsGame = new HBox();

        root.setPadding(new Insets(10));
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/style/begin-game.css")).toExternalForm()
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

        Label header = new Label(currentGameCopy.getStory().getTittle());
        header.setId("header");
        topVBox = new VBox(imgAnchorPane, header);
        topVBox.setAlignment(Pos.CENTER);
    }

    private void createCentre() {
        centerVbox = new VBox();
        centerVbox.setAlignment(Pos.CENTER);

        goldLabel = new Label("Gold: " + player.getGold());
        scoreLabel = new Label("Score: " + player.getScore());
        inventoryLabel = new Label("Inventory: " + player.getInventory());

        HBox labelsBox = new HBox(goldLabel, scoreLabel, inventoryLabel);
        labelsBox.setSpacing(10);
        labelsBox.setAlignment(Pos.CENTER);

        healthLabel = new Label(player.getHealth() + "%");
        healthBar = new ProgressBar();
        healthBar.setPrefWidth(200);
        healthBar.setProgress(currentGameCopy.getPlayer().getHealth() / 100.0);
        setHealthBarColor(healthBar);

        HBox healthBox = new HBox(healthBar, healthLabel);
        healthBox.setSpacing(10);
        healthBox.setAlignment(Pos.CENTER);
        centerVbox.getChildren().addAll(labelsBox, healthBox);

        gameDiv = new StackPane();
        gameDiv.getStyleClass().add("game-div");
        gameDiv.setMinSize(1000, 300);
        Button startGameButton = new Button("start game");
        startGameButton.setAlignment(Pos.CENTER);
        gameDiv.getChildren().add(startGameButton);
        startGameButton.setOnAction(event -> startGame());

        centerVbox.getChildren().add(gameDiv);
        //goalProgress();

    }

    private void startGame() {
        gameDiv.getChildren().clear();
        Passage startPassage = currentGameCopy.begin();

        VBox passageBox = new VBox(10);
        passageBox.setPadding(new Insets(10));

        titleLabel = new Label(startPassage.getTittle());
        titleLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        contentLabel = new Label(startPassage.getContent());
        contentLabel.setStyle("-fx-font-size: 16px;");
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(500);

        passageBox.getChildren().addAll( titleLabel, contentLabel);

        HBox linksBox = new HBox(10);

        for (Link link : startPassage.getLinks()) {
            Label linkTextLabel = new Label(link.getText());
            linkTextLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            Button linkButton = new Button(link.getReference());
            linkButton.setStyle("-fx-font-size: 16px;");

            VBox linkContentBox = new VBox(5);
            linkContentBox.setPadding(new Insets(5));
            linkContentBox.getChildren().addAll(linkTextLabel, linkButton);

            for (Action action : link.getActions()) {
                Pair<Image, String> actionImageAndValue = getActionImageAndValue(action);
                Image actionImage = actionImageAndValue.getKey();
                String actionValue = actionImageAndValue.getValue();

                Label actionLabel = new Label(actionValue);

                actionLabel.setStyle("-fx-font-size: 14px; -fx-font-style: italic;");

                ImageView actionImageView = new ImageView(actionImage);
                actionImageView.setFitWidth(20);
                actionImageView.setPreserveRatio(true);

                HBox actionHBox = new HBox();
                actionHBox.getChildren().addAll(actionLabel, actionImageView );
                linkContentBox.getChildren().add(actionHBox);
            }
            HBox linksContentPassageBox = new HBox(linkContentBox);

            linkButton.setOnAction(event -> {
                Passage newPassage = currentGameCopy.go(link);
                addAction(link);
                loadPassage(newPassage);
            });

            linksBox.getChildren().add(linksContentPassageBox);
        }
        linksBox.setAlignment(Pos.CENTER);

        passageBox.getChildren().add(linksBox);

        gameDiv.getChildren().add(passageBox);
        passageBox.setAlignment(Pos.TOP_CENTER);

    }



    private void loadPassage(Passage passage) {
        gameDiv.getChildren().clear();
        if (player.getHealth() == 0) {
            gameOver();
            return;
        }
        setHealthBarColor(healthBar);
        scoreLabel.setText("Score: " + player.getScore());
        goldLabel.setText("Gold : " + player.getGold());
        inventoryLabel.setText("Inventory : " + player.getInventory());

        VBox passageBox = new VBox(10);
        passageBox.setPadding(new Insets(10));

        ImageView imageView = new ImageView(getClass().getResource("/edu/ntnu/paths/Controller/img/restart-button.png").toExternalForm());
        imageView.setFitWidth(40);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        AnchorPane imgAnchorPane = new AnchorPane();
        AnchorPane.setRightAnchor(imageView, 10.0);
        imgAnchorPane.getChildren().add(imageView);

        imageView.setOnMouseClicked(event -> {
            currentGameCopy = new Game(currentGame);
            player = currentGameCopy.getPlayer();
            player.setInventory(new ArrayList<>());

            goldLabel.setText("Gold: " + player.getGold());
            scoreLabel.setText("Score: " + player.getScore());
            inventoryLabel.setText("Inventory: " + player.getInventory());

            setHealthBarColor(healthBar);
            startGame();
        });

        titleLabel = new Label(passage.getTittle());
        titleLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        contentLabel = new Label(passage.getContent());
        contentLabel.setStyle("-fx-font-size: 16px;");
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(500);

        passageBox.getChildren().addAll(imgAnchorPane, titleLabel, contentLabel);

        HBox linksBox = new HBox(10);

        for (Link link : passage.getLinks()) {
            Label linkTextLabel = new Label(link.getText());
            linkTextLabel.setStyle("-fx-font-size: 16px; -fx-font-weight:bold;");
            Button linkButton = new Button(link.getReference());
            linkButton.setStyle("-fx-font-size: 16px;");

            VBox linkContentBox = new VBox(5);
            linkContentBox.setPadding(new Insets(10));
            linkContentBox.getChildren().addAll(linkTextLabel, linkButton);

            for (Action action : link.getActions()) {
                Pair<Image, String> actionImageAndValue = getActionImageAndValue(action);
                Image actionImage = actionImageAndValue.getKey();
                String actionValue = actionImageAndValue.getValue();

                Label actionLabel = new Label(actionValue);

                ImageView actionImageView = new ImageView(actionImage);
                actionImageView.setFitWidth(30);
                actionImageView.setPreserveRatio(true);

                HBox actionHBox = new HBox();
                actionHBox.getChildren().addAll(actionLabel, actionImageView );
                linkContentBox.getChildren().add(actionHBox);
            }

            HBox linkBox = new HBox(10);
            linkBox.getChildren().add(linkContentBox);

            linkButton.setOnAction(event -> {
                Passage newPassage = currentGameCopy.go(link);
                addAction(link);
                loadPassage(newPassage);
            });

            linksBox.getChildren().add(linkBox);
        }

        if (passage.getLinks().size() == 0){
            viewStatsButton.setDisable(false);
        }

        linksBox.setAlignment(Pos.CENTER);

        passageBox.getChildren().add(linksBox);

        gameDiv.getChildren().add(passageBox);
        passageBox.setAlignment(Pos.TOP_CENTER);
    }


    private Pair<Image, String> getActionImageAndValue(Action action) {
        if (action instanceof GoldAction goldAction) {
            Image image = new Image(getClass().getResource("/edu/ntnu/paths/Controller/img/coin.png").toExternalForm());
            String value = goldAction.getGold() + "";
            return new Pair<>(image, value);
        } else if (action instanceof HealthAction healthAction) {
            Image image = new Image(getClass().getResource("/edu/ntnu/paths/Controller/img/heart.png").toExternalForm());
            String value = healthAction.getHealth() + "";
            return new Pair<>(image, value);
        } else if (action instanceof ScoreAction scoreAction) {
            Image image = new Image(getClass().getResource("/edu/ntnu/paths/Controller/img/trophy.png").toExternalForm());
            String value = scoreAction.getPoints() + "";
            return new Pair<>(image, value);
        } else if (action instanceof InventoryAction inventoryAction) {
            Image image = new Image(getClass().getResource("/edu/ntnu/paths/Controller/img/bag.png").toExternalForm());
            String value = inventoryAction.getItem();
            return new Pair<>(image, value);
        }
        return null;
    }

    private void addAction(Link link) {
        Player player = currentGameCopy.getPlayer();
        for (Action action : link.getActions()) {
            if (action instanceof GoldAction goldAction) {
                player.addGold(goldAction.getGold());
            } else if (action instanceof HealthAction healthAction) {
                player.changeHealth(healthAction.getHealth());
            } else if (action instanceof ScoreAction scoreAction) {
                player.addScore(scoreAction.getPoints());
            } else if (action instanceof InventoryAction inventoryAction) {
                player.addToInventory(inventoryAction.getItem());
            }
        }

    }



    private void setHealthBarColor(ProgressBar healthBar) {
        double health = player.getHealth();
        healthLabel.setText(health + "%");
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

/*    private void goalProgress() {
        goalsGame.getChildren().clear();

        int healthGoalGame = 0;
        int goldGoalGame = 0;
        int scoreGoalGame = 0;
        List<String> inventoryGoalGame = new ArrayList<>();
        boolean inventoryGoalBool = true;

        for (Goal goal : currentGame.getGoals()) {
            if (goal instanceof HealthGoal healthGoal) {
                healthGoalGame = healthGoal.getHealth();
            } else if (goal instanceof GoldGoal goldGoal) {
                System.out.println("gold " + goldGoal.getGold());
                goldGoalGame = goldGoal.getGold();
            } else if (goal instanceof ScoreGoal scoreGoal) {
                scoreGoalGame = scoreGoal.getScore();
            } else if (goal instanceof InventoryGoal inventoryGoal) {
                if (inventoryGoalBool) {
                    inventoryGoalGame = inventoryGoal.getInventory();
                    inventoryGoalBool = false;
                }
            }
        }

        healthGoalLabel = new Label("Health Goal: " + healthGoalGame);
        goldGoalLabel = new Label("Gold Goal: " + goldGoalGame);
        scoreGoalLabel = new Label("Score Goal: " + scoreGoalGame);
        inventoryGoalLabel = new Label("Inventory Goal: " + inventoryGoalGame);

        goalsGame = new HBox(healthGoalLabel, goldGoalLabel, scoreGoalLabel, inventoryGoalLabel);
        goalsGame.setId("goalsGame");
        goalsGame.setSpacing(20);
        goalsGame.setAlignment(Pos.CENTER);
        centerVbox.getChildren().add(goalsGame);
    }*/

    private void gameOver() {
        gameDiv.getChildren().clear();
        setHealthBarColor(healthBar);
        titleLabel.setText("Game Over");
        contentLabel.setText("");
        titleLabel.setAlignment(Pos.TOP_CENTER);
        viewStatsButton.setDisable(false);
        Button startGameButton = new Button("start game");
        startGameButton.setAlignment(Pos.CENTER);
        startGameButton.setOnAction(event -> startGame());
        gameDiv.getChildren().addAll(titleLabel, contentLabel, startGameButton);
    }


    private void createBottom() {
        bottomAnchorPane = new AnchorPane();
        viewStatsButton = new Button("View Stats");
        viewStatsButton.setOnAction(this::handleViewStats);
        viewStatsButton.setDisable(true);
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
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        GameStatistics gameStatistics = new GameStatistics();
        try {
            gameStatistics.start(stage, currentGameCopy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handleGoBack(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        CreateGoals createGoals = new CreateGoals();
        try {
            createGoals.start(stage);
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