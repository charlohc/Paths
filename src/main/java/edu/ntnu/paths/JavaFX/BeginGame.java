package edu.ntnu.paths.JavaFX;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The BeginGame class contains the game the user can interact with
 */
public class BeginGame {

    private Game currentGame, currentGameCopy;
    private Player player;
    private BorderPane root;
    private VBox topVBox, centerVbox;
    private HBox goalsGame;
    private AnchorPane bottomAnchorPane;
    private Button viewStatsButton, goBackButton;
    private StackPane gameDiv;
    private Label healthLabel, goldLabel, scoreLabel, inventoryLabel, titleLabel, contentLabel;
    private ProgressBar healthBar;
    private Image goldImage, scoreImage, healthImage, inventoryImage;

    /**
     * Starts the game and initializes the UI.
     * @param stage the JavaFX stage to display the game UI on
     */
    public void start(Stage stage) {
        currentGame = GameManager.getInstance().getGame();
        currentGameCopy = new Game(currentGame);
        goldImage = new Image(getClass().getResource("/edu/ntnu/paths/resources/img/coin.png").toExternalForm());
        healthImage = new Image(getClass().getResource("/edu/ntnu/paths/resources/img/heart.png").toExternalForm());
        scoreImage = new Image(getClass().getResource("/edu/ntnu/paths/resources/img/trophy.png").toExternalForm());
        inventoryImage = new Image(getClass().getResource("/edu/ntnu/paths/resources/img/bag.png").toExternalForm());
        player = currentGameCopy.getPlayer();
        player.setInventory(new ArrayList<>());
        root = new BorderPane();
        goalsGame = new HBox();

        root.setPadding(new Insets(10));
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/style/begin-game.css")).toExternalForm()
        );

        createTop();
        createCentre();
        createBottom();

        root.setTop(topVBox);
        root.setCenter(centerVbox);
        root.setBottom(bottomAnchorPane);

        Scene scene = new Scene(root, 1005, 620);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates the top of the scene
     * Containing the title of the story
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

        Label header = new Label(currentGameCopy.getStory().getTittle());
        header.setId("header");
        topVBox = new VBox(imgAnchorPane, header);
        topVBox.setAlignment(Pos.CENTER);
    }

    /**
     * Creates the center of the scene
     * Contains information about the gold, inventory, score and health of the user
     * displays a game div where the user can interact with the story
     */
    private void createCentre() {
        centerVbox = new VBox();
        centerVbox.setAlignment(Pos.CENTER);

        goldLabel = new Label("Gold : " + player.getGold());
        ImageView imageViewGold = new ImageView(goldImage);
        imageViewGold.setFitWidth(25);
        imageViewGold.setFitHeight(20);
        HBox goldInfo = new HBox(goldLabel, imageViewGold);

        inventoryLabel = new Label("Inventory : " + player.getInventory());
        ImageView imageViewInventory = new ImageView(inventoryImage);
        imageViewInventory.setFitWidth(25);
        imageViewInventory.setFitHeight(20);
        HBox inventoryInfo = new HBox(inventoryLabel, imageViewInventory);

        scoreLabel = new Label("Score : " + player.getScore());
        ImageView imageViewScore = new ImageView(scoreImage);
        imageViewScore.setFitWidth(25);
        imageViewScore.setFitHeight(20);
        HBox scoreInfo = new HBox(scoreLabel, imageViewScore);

        HBox labelsBox = new HBox(goldInfo, scoreInfo, inventoryInfo);
        labelsBox.setSpacing(10);
        labelsBox.setAlignment(Pos.CENTER);

        healthLabel = new Label(player.getHealth() + "%");
        healthBar = new ProgressBar();
        healthBar.setPrefWidth(200);
        healthBar.setProgress(currentGameCopy.getPlayer().getHealth() / 100.0);
        setHealthBarColor(healthBar);

        ImageView imageViewHealth = new ImageView(healthImage);
        imageViewHealth.setFitWidth(25);
        imageViewHealth.setFitHeight(20);

        HBox healthBox = new HBox(healthBar, healthLabel, imageViewHealth);
        healthBox.setSpacing(10);
        healthBox.setAlignment(Pos.CENTER);
        centerVbox.getChildren().addAll(labelsBox, healthBox);

        gameDiv = new StackPane();
        gameDiv.getStyleClass().add("game-div");
        gameDiv.setMinSize(990, 320);
        Button startGameButton = new Button("start game");
        startGameButton.setAlignment(Pos.CENTER);
        gameDiv.getChildren().add(startGameButton);
        startGameButton.setOnAction(event -> startGame());

        centerVbox.getChildren().add(gameDiv);
    }

    /**
     * Starts the game by displaying the initial passage and its associated content and links.
     * Clears the existing game content and loads the starting passage.
     */
    private void startGame() {
        gameDiv.getChildren().clear();
        Passage startPassage = currentGameCopy.begin();

        VBox passageBox = new VBox(10);
        passageBox.setPadding(new Insets(10));

        titleLabel = new Label(startPassage.getTittle());
        titleLabel.setId("titleLabel");

        contentLabel = new Label(startPassage.getContent());
        contentLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: normal;");
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(700);

        Image storyImage = imageFromContent(startPassage.getContent());
        ImageView storyImageView = new ImageView(storyImage);
        storyImageView.setFitWidth(60);
        storyImageView.setPreserveRatio(true);
        storyImageView.setSmooth(true);
        StackPane anchorPaneImageStory = new StackPane(storyImageView);
        anchorPaneImageStory.setAlignment(Pos.BOTTOM_RIGHT);

        passageBox.getChildren().addAll(titleLabel, contentLabel);

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

        passageBox.getChildren().addAll(linksBox, anchorPaneImageStory);

        gameDiv.getChildren().add(passageBox);
        passageBox.setAlignment(Pos.TOP_CENTER);

    }

    /**
     * Loads a passage into the game interface, displaying the passage content and associated links.
     * Clears the existing game content and updates player stats and inventory.
     * @param passage the Passage object to be loaded
     */
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

        ImageView imageViewRestart = new ImageView(getClass().getResource("/edu/ntnu/paths/resources/img/restart-button.png").toExternalForm());
        imageViewRestart.setFitWidth(35);
        imageViewRestart.setPreserveRatio(true);
        imageViewRestart.setSmooth(true);

        AnchorPane imgAnchorPane = new AnchorPane();
        AnchorPane.setRightAnchor(imageViewRestart, 10.0);
        imgAnchorPane.getChildren().add(imageViewRestart);

        imageViewRestart.setOnMouseClicked(event -> {
            currentGameCopy = new Game(currentGame);
            player = currentGameCopy.getPlayer();
            player.setInventory(new ArrayList<>());

            goldLabel.setText("Gold: " + player.getGold());
            scoreLabel.setText("Score: " + player.getScore());
            inventoryLabel.setText("Inventory: " + player.getInventory());
            viewStatsButton.setDisable(true);
            goBackButton.setDisable(true);


            setHealthBarColor(healthBar);
            startGame();
        });

        titleLabel = new Label(passage.getTittle());
        titleLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        contentLabel = new Label(passage.getContent());
        contentLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: normal;");
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(700);

       Image storyImage = imageFromContent(passage.getContent());
       ImageView storyImageView = new ImageView(storyImage);
       storyImageView.setFitWidth(60);
       storyImageView.setPreserveRatio(true);
       storyImageView.setSmooth(true);
       StackPane anchorPaneImageStory = new StackPane(storyImageView);
       anchorPaneImageStory.setAlignment(Pos.BOTTOM_RIGHT);

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

                if (actionLabel.getText().contains("-")) actionLabel.setTextFill(Color.RED);

                ImageView actionImageView = new ImageView(actionImage);
                actionImageView.setFitWidth(23);
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
            goBackButton.setDisable(false);
        }

        linksBox.setAlignment(Pos.CENTER);

        passageBox.getChildren().addAll(linksBox, anchorPaneImageStory);

        gameDiv.getChildren().add(passageBox);
        passageBox.setAlignment(Pos.TOP_CENTER);
    }

    /**
     * Retrieves the appropriate image based on the content of a passage.
     * @param content the content of the passage
     * @return the Image associated with the content, or null if no matching Image is found
     */
    private Image imageFromContent(String content) {
        String contentLowerCase = content.toLowerCase();
        if (contentLowerCase.contains("witch")) {
            return new Image(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/witch.png")).toExternalForm());
        } else if (contentLowerCase.contains("library") || contentLowerCase.contains("books")) {
            return new Image(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/books.png")).toExternalForm());
        } else if (contentLowerCase.contains("path")) {
            return new Image(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/path.png")).toExternalForm());
        } else if (contentLowerCase.contains("forest")) {
            return new Image(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/forest.png")).toExternalForm());
        } else if (contentLowerCase.contains("dragon")) {
            return new Image(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/dragon.png")).toExternalForm());
        } else if (contentLowerCase.contains("tree")) {
            return new Image(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/tree.png")).toExternalForm());
        } else if (contentLowerCase.contains("relic")) {
            return new Image(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/relic.png")).toExternalForm());
        } else if (contentLowerCase.contains("ruins")) {
            return new Image(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/ruins.png")).toExternalForm());
        }
        return null;
    }

    /**
     * Retrieves the appropriate Image and value associated with an Action.
     * @param action the Action object
     * @return a Pair containing the Image and value associated with the Action, or null if the Action type is not recognized
     */
    private Pair<Image, String> getActionImageAndValue(Action action) {
        if (action instanceof GoldAction goldAction) {
            String value = String.valueOf(goldAction.getGold());
            return new Pair<>(goldImage, value);
        } else if (action instanceof HealthAction healthAction) {
            String value = String.valueOf(healthAction.getHealth());
            return new Pair<>(healthImage, value);
        } else if (action instanceof ScoreAction scoreAction) {
            String value = String.valueOf(scoreAction.getPoints());
            return new Pair<>(scoreImage, value);
        } else if (action instanceof InventoryAction inventoryAction) {
            String value = inventoryAction.getItem();
            return new Pair<>(inventoryImage, value);
        }
        return null;
    }

    /**
     * Adds the effects of the actions associated with a link to the player.
     * @param link the Link object containing the actions
     */
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

    /**
     * Sets the color and fill ratio of the health bar based on the player's health value.
     * @param healthBar the ProgressBar representing the health bar
     */
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

    /**
     * Displays the game over screen and shows the game over message.
     * Allows the player to restart the game.
     */
    private void gameOver() {
        gameDiv.getChildren().clear();
        setHealthBarColor(healthBar);
        titleLabel.setText("Game Over");
        titleLabel.setId("GameOver");
        contentLabel.setText("");
        titleLabel.setAlignment(Pos.TOP_CENTER);
        viewStatsButton.setDisable(false);
        goBackButton.setDisable(false);
        Button restart = new Button("Restart");

        VBox gameOverContainer = new VBox();
        gameOverContainer.setAlignment(Pos.CENTER);
        gameOverContainer.setSpacing(10);

        VBox.setMargin(restart, new Insets(0, 0, 50, 0));
        VBox.setMargin(titleLabel, new Insets(10, 0, 0, 0));

        gameOverContainer.getChildren().addAll(titleLabel, restart);

        gameDiv.getChildren().addAll(gameOverContainer);
    }


    /**
     * Creates the bottom of the scene
     * Containing buttons to maneuver to other scenes
     */
    private void createBottom() {
        bottomAnchorPane = new AnchorPane();
        viewStatsButton = new Button("View Stats");
        viewStatsButton.setOnAction(this::handleViewStats);
        viewStatsButton.setDisable(true);
        AnchorPane.setBottomAnchor(viewStatsButton, 20.0);
        AnchorPane.setRightAnchor(viewStatsButton, 50.0);

        goBackButton = new Button("Go Back");
        goBackButton.setOnAction(this::handleGoBack);
        AnchorPane.setBottomAnchor(goBackButton, 20.0);
        AnchorPane.setLeftAnchor(goBackButton, 50.0);

        bottomAnchorPane.getChildren().addAll(viewStatsButton, goBackButton);
        root.setBottom(bottomAnchorPane);
    }

    /**
     * Handles the action event when the "View Statistics" button is clicked.
     * @param actionEvent the action event triggered by clicking the "View Statistics" button
     */
    private void handleViewStats(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        GameStatistics gameStatistics = new GameStatistics();
        try {
            gameStatistics.start(stage, currentGameCopy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Handles the action event when the "Go Back" button is clicked.
     * @param actionEvent the action event triggered by clicking the "Go Back" button
     */
    private void handleGoBack(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        CreateGoals createGoals = new CreateGoals();
        try {
            createGoals.start(stage);
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