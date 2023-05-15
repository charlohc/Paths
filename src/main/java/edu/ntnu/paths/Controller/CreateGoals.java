package edu.ntnu.paths.Controller;

import edu.ntnu.paths.GameDetails.Game;
import edu.ntnu.paths.GameDetails.GameBuilder;
import edu.ntnu.paths.Goals.*;
import edu.ntnu.paths.Managers.GameManager;
import edu.ntnu.paths.Managers.PlayerManager;
import edu.ntnu.paths.Managers.StoryManager;
import edu.ntnu.paths.StoryDetails.Story;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.prefs.Preferences;

public class CreateGoals {
    private final Preferences prefs = Preferences.userNodeForPackage(CreateGoals.class);

    private Game newGame;
    private Story currentStory;
    private Spinner<Integer> intSpinner;
    private TextField inventoryTextField;
    private ArrayList<String> inventoryGoalList = new ArrayList<>();

    private ArrayList<Goal> goals = new ArrayList<>();
    private ArrayList<String> allInventoryStory;
    private Pane labelGoal,guidanceGoal,inputGoal, goalsContainerPane;
    private VBox topVBox, dropdownContainer, goalsContainerVBox, createGoalContainer, allInventoryContainer;
    private ComboBox<String> goalDropdown;
    private AnchorPane bottomAnchorPane;
    private Button addGoalButton;

    private Label feedbackLabel;

    public void start(Stage stage) {
        currentStory = StoryManager.getInstance().getStory();
        newGame = GameManager.getInstance().getGame();
        allInventoryStory = currentStory.getAllInventoryItems();

        BorderPane root = new BorderPane();
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/create-goals.css")).toExternalForm()
        );

        if (newGame != null) {
            addGoalsToContainer();
        }

        createTop();
        createLeftSection();
        createCentre();
        createRightSection();
        createBottom();

        root.setTop(topVBox);

        root.setLeft(dropdownContainer);

        root.setCenter(createGoalContainer);

        root.setRight(goalsContainerPane);

        root.setBottom(bottomAnchorPane);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void createTop() {
        Label header = new Label("Set your goals for the game");
        header.setId("header");
        Label infoGoalsLabel = new Label("You can have as many goals as you would like,\nbut you can only have one of the score, gold and health type");
        infoGoalsLabel.setId("infoGoalsLabel");
         topVBox = new VBox(header, infoGoalsLabel);
        topVBox.setAlignment(Pos.CENTER);
    }

    private void createLeftSection() {
        dropdownContainer = new VBox();
        dropdownContainer.setSpacing(10);
        dropdownContainer.setAlignment(Pos.TOP_LEFT);
        dropdownContainer.setId("dropDownContainer");
        goalDropdown = new ComboBox<>();
        goalDropdown.getItems().addAll("Gold", "Health", "Score", "Inventory");
        goalDropdown.setPromptText("Select a goal");

        goalDropdown.setOnAction(e -> {  String selectedOption = goalDropdown.getValue(); handleDropdownChange(selectedOption);});

        dropdownContainer.getChildren().add(goalDropdown);
    }

    private void createCentre() {
        createGoalContainer = new VBox();
        createGoalContainer.setSpacing(15);
        createGoalContainer.setAlignment(Pos.TOP_LEFT);
        createGoalContainer.setStyle("-fx-padding: 110px 0px 0px 0px");

        labelGoal = new Pane();

        inputGoal = new Pane();

        guidanceGoal = new VBox();

        allInventoryContainer = new VBox();
        allInventoryContainer.setSpacing(10);
        allInventoryContainer.setAlignment(Pos.TOP_CENTER);

        feedbackLabel = new Label();

        createGoalContainer.getChildren().addAll(labelGoal, inputGoal, guidanceGoal, allInventoryContainer, feedbackLabel);
    }

    private void createRightSection() {
        Label yourGoalsLabel = new Label("Your selected goals");
        yourGoalsLabel.setId("yourGoalsLabel");
        goalsContainerVBox = new VBox();
        goalsContainerVBox.setSpacing(10);
        goalsContainerVBox.setId("goalsContainer");
        goalsContainerVBox.setAlignment(Pos.TOP_CENTER);
        goalsContainerPane = new Pane(yourGoalsLabel, goalsContainerVBox);
        BorderPane.setMargin(goalsContainerPane, new Insets(100, 50, 0, 0));
    }

    private void createBottom() {
        bottomAnchorPane = new AnchorPane();

        Button startGameButton = new Button("Start game");
        startGameButton.setOnAction(this::beginGame);
        AnchorPane.setBottomAnchor(startGameButton, 20.0);
        AnchorPane.setRightAnchor(startGameButton, 50.0);

        Button goBackButton = new Button("Go back");
        goBackButton.setOnAction(this::goBack);
        AnchorPane.setBottomAnchor(goBackButton, 20.0);
        AnchorPane.setLeftAnchor(goBackButton, 50.0);

        bottomAnchorPane.getChildren().addAll(startGameButton, goBackButton);
    }

    private void handleDropdownChange(String selectedOption) {
        clearAllContainers();

        Label goalNameLabel = new Label(selectedOption + " goal");
        labelGoal.getChildren().add(goalNameLabel);

        // Only create the "add goal" button if it doesn't already exist
        if (addGoalButton == null) {
            addGoalButton = new Button("add goal");
        }
        addGoalButton.setOnAction(e -> {
            addGoal(selectedOption);
        });

        int minVal = 0;
        int step = 1;

        switch (selectedOption) {
            case "Gold" -> {
                int maxVal = currentStory.findMaxGold();
                int initialValue = currentStory.findMaxGold() / 2;

                Label goldGuidanceLabel = new Label("Pssst...\nThe best path in the game gives " + maxVal + " gold");
                guidanceGoal.getChildren().add(goldGuidanceLabel);

                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minVal, maxVal, initialValue, step);
                intSpinner = new Spinner<>();
                intSpinner.setEditable(false);
                intSpinner.setValueFactory(valueFactory);

                inputGoal.getChildren().add(intSpinner);
            }
            case "Health" -> {
                int maxVal = 100;
                int initialValue = 100;
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minVal, maxVal, initialValue, step);
                intSpinner = new Spinner<>();
                intSpinner.setEditable(false);
                intSpinner.setValueFactory(valueFactory);

                inputGoal.getChildren().add(intSpinner);
            }
            case "Score" -> {
                int maxVal = currentStory.findMaxScore();
                int initialValue = currentStory.findMaxScore() / 2;

                Label scoreGuidanceLabel = new Label("Pssst...\nThe best path in the game gives " + maxVal + " score");
                guidanceGoal.getChildren().add(scoreGuidanceLabel);

                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minVal, maxVal, initialValue, step);
                intSpinner = new Spinner<>();
                intSpinner.setEditable(false);
                intSpinner.setValueFactory(valueFactory);

                inputGoal.getChildren().add(intSpinner);
            }
            case "Inventory" -> {
                Label inventoryGuidanceLabel = new Label("Pssst...\nBelow you can see all items in the game");
                guidanceGoal.getChildren().add(inventoryGuidanceLabel);
                for (String inventory : allInventoryStory) {
                    Pane inventoryPane = new Pane();
                    inventoryPane.getChildren().add(new Label(inventory));
                    allInventoryContainer.getChildren().add(inventoryPane);
                }
                 inventoryTextField = new TextField();
                inputGoal.getChildren().add(inventoryTextField);
            }
        }
        if (!createGoalContainer.getChildren().contains(addGoalButton)) {
            createGoalContainer.getChildren().add(addGoalButton);
        }
    }


    private void addGoal(String goalName) {

        switch (goalName) {
            case "Gold" -> {
                boolean goldGoalExists = false;
                for (Goal goal : goals) {
                    if (goal instanceof GoldGoal) {
                        goldGoalExists = true;
                        break;
                    }
                }
                if (goldGoalExists) {
                    goals.removeIf(goal -> goal instanceof GoldGoal);
                }
                GoldGoal goldGoal = new GoldGoal();
                goldGoal.goldGoal(intSpinner.getValue());
                goals.add(goldGoal);
            }
            case "Health" -> {
                boolean healthGoalExist = false;
                for (Goal goal : goals) {
                    if (goal instanceof HealthGoal) {
                        healthGoalExist = true;
                        break;
                    }
                }
                if (healthGoalExist) {
                    goals.removeIf(goal -> goal instanceof HealthGoal);
                }
                HealthGoal healthGoal = new HealthGoal();
                healthGoal.healthGoal(intSpinner.getValue());
                goals.add(healthGoal);
            }
            case "Score" -> {
                boolean scoreGoalExist = false;
                for (Goal goal : goals) {
                    if (goal instanceof ScoreGoal) {
                        scoreGoalExist = true;
                        break;
                    }
                }
                if (scoreGoalExist) {
                    goals.removeIf(goal -> goal instanceof ScoreGoal);
                }
                ScoreGoal scoreGoal = new ScoreGoal();
                scoreGoal.scoreGoal(intSpinner.getValue());
                goals.add(scoreGoal);
            }
            case "Inventory" -> {
                String inventorySuggestion = inventoryTextField.getText().toLowerCase();

                if (!allInventoryStory.contains(inventorySuggestion)) {
                    feedbackLabel.setText("Inventory not in story");
                } else if (inventoryGoalList.contains(inventorySuggestion)) {
                    feedbackLabel.setText("Goal already added");
                } else {
                    InventoryGoal inventoryGoal = new InventoryGoal();
                    inventoryGoalList.add(inventorySuggestion.toLowerCase());
                    inventoryGoal.inventoryGoal(inventoryGoalList);
                    goals.add(inventoryGoal);
                }

            }

        }
         newGame = GameBuilder.newInstance()
                        .setStory(currentStory)
                                .setPlayer(PlayerManager.getInstance().getPlayer())
                                        .setGoals(new ArrayList<>(goals))
                                                .build();

        GameManager.getInstance().setGame(new Game(newGame));
        prefs.get("goals", String.valueOf(goals));
        addGoalsToContainer();
    }

    private void addGoalsToContainer() {
        goalsContainerVBox.getChildren().clear();
        boolean addedInventoryGoal = false;

        for (Goal goal : goals) {
            if (goal instanceof InventoryGoal inventoryGoal) {
                if (!addedInventoryGoal) {
                    List<String> mandatoryItems = inventoryGoal.getMandatoryItems();
                    for (String item : mandatoryItems) {
                        Pane pane = new Pane();
                        Label label = new Label("Inventory : " + item);
                        pane.getChildren().add(label);
                        goalsContainerVBox.getChildren().add(pane);
                    }
                    addedInventoryGoal = true;
                }
            } else if (goal instanceof GoldGoal goldGoal) {
                Pane pane = new Pane();
                Label label = new Label("Gold : " + goldGoal.getMinimumGold());
                pane.getChildren().add(label);
                goalsContainerVBox.getChildren().add(pane);
            } else if (goal instanceof ScoreGoal scoreGoal) {
                Pane pane = new Pane();
                Label label = new Label("Score : " + scoreGoal.getMinimumPoints());
                pane.getChildren().add(label);
                goalsContainerVBox.getChildren().add(pane);
            } else if (goal instanceof HealthGoal healthGoal) {
                Pane pane = new Pane();
                Label label = new Label("Health : " + healthGoal.getMinimumHealth());
                pane.getChildren().add(label);
                goalsContainerVBox.getChildren().add(pane);
            }
        }
    }





    private void clearAllContainers() {
        feedbackLabel.setText("");
        labelGoal.getChildren().clear();
        inputGoal.getChildren().clear();
        guidanceGoal.getChildren().clear();
        allInventoryContainer.getChildren().clear();
        intSpinner = null;
        inventoryTextField = null;
    }

    private void beginGame(ActionEvent actionEvent) {
    }

    private void goBack(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        CreatePlayer createPlayer = new CreatePlayer();
        try {
            createPlayer.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

