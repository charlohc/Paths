package edu.ntnu.paths.Controller;

import edu.ntnu.paths.Goals.*;
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
import java.util.Objects;

public class CreateGoals {
    private Story currentStory;
    private Spinner<Integer> intSpinner;
    private TextField textField;
    private ArrayList<String> inventoryGoalList = new ArrayList<>();

    private BorderPane root;
    private VBox goalsContainer;
    private ComboBox<String> goalDropdown;
    private VBox labelGoal;
    private VBox inputGoal;
    private VBox guidanceGoal;
    private HBox setGoalButton;
    private VBox allInventoryContainer;

    public void start(Stage stage) {
        currentStory = StoryManager.getInstance().getStory();

        root = new BorderPane();
        root.setPadding(new Insets(20));
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/create-goals.css")).toExternalForm()
        );

        // Top section
        Label header = new Label("Set your goals for the game");
        header.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        header.setPadding(new Insets(0, 0, 20, 0));
        root.setTop(header);

        // Left section
        VBox dropdownContainer = new VBox();
        dropdownContainer.setSpacing(10);
        dropdownContainer.setAlignment(Pos.CENTER_LEFT);
        goalDropdown = new ComboBox<>();
        goalDropdown.getItems().addAll("Gold", "Health", "Score", "Inventory");
        goalDropdown.setPromptText("Select a goal");

        goalDropdown.setOnAction(e -> {  String selectedOption = goalDropdown.getValue().toString(); handleDropdownChange(selectedOption);});

        dropdownContainer.getChildren().add(goalDropdown);

        root.setLeft(dropdownContainer);

        // Center section
        VBox centerContainer = new VBox();
        centerContainer.setSpacing(10);
        centerContainer.setAlignment(Pos.CENTER);

        labelGoal = new VBox();
        labelGoal.setSpacing(10);
        labelGoal.setAlignment(Pos.CENTER);

        inputGoal = new VBox();
        inputGoal.setSpacing(10);
        inputGoal.setAlignment(Pos.CENTER);

        guidanceGoal = new VBox();
        guidanceGoal.setSpacing(10);
        guidanceGoal.setAlignment(Pos.CENTER);

        allInventoryContainer = new VBox();
        allInventoryContainer.setSpacing(10);
        allInventoryContainer.setAlignment(Pos.CENTER);

        centerContainer.getChildren().addAll(labelGoal, inputGoal, guidanceGoal, allInventoryContainer);
        root.setCenter(centerContainer);

        // Right section
        setGoalButton = new HBox();
        setGoalButton.setAlignment(Pos.CENTER_RIGHT);

        Button backButton = new Button("Back");
        backButton.setOnAction(this::goBack);
        Button startGameButton = new Button("Start game");
        startGameButton.setOnAction(this::beginGame);

        setGoalButton.getChildren().addAll(backButton, startGameButton);

        root.setRight(setGoalButton);

        // Bottom section
        goalsContainer = new VBox();
        goalsContainer.setSpacing(10);
        goalsContainer.setAlignment(Pos.CENTER);
        root.setBottom(goalsContainer);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }


    private void handleDropdownChange(String selectedOption) {
        clearAllContainers();

        Label goalNameLabel = new Label(selectedOption + " goal");
        goalNameLabel.setLayoutX(100);
        labelGoal.getChildren().add(goalNameLabel);

        Button addGoalButton = new Button("add goal");
        setGoalButton.getChildren().add(addGoalButton);
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
                intSpinner.setEditable(true);
                intSpinner.setValueFactory(valueFactory);

                inputGoal.getChildren().add(intSpinner);
            }
            case "Health" -> {
                intSpinner = new Spinner<>();
                intSpinner.setEditable(true);
                inputGoal.getChildren().add(intSpinner);
            }
            case "Score" -> {
                int maxVal = currentStory.findMaxScore();
                int initialValue = currentStory.findMaxScore() / 2;

                Label scoreGuidanceLabel = new Label("Pssst...\nThe best path in the game gives " + maxVal + " score");
                guidanceGoal.getChildren().add(scoreGuidanceLabel);

                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minVal, maxVal, initialValue, step);
                intSpinner = new Spinner<>();
                intSpinner.setEditable(true);
                intSpinner.setValueFactory(valueFactory);

                inputGoal.getChildren().add(intSpinner);
            }
            case "Inventory" -> {
                Label inventoryGuidanceLabel = new Label("Pssst...\nBelow you can see all items in the game");
                guidanceGoal.getChildren().add(inventoryGuidanceLabel);
                for (String inventory : currentStory.getAllInventoryItems()) {
                    Pane inventoryPane = new Pane();
                    Label inventoryLabel = new Label(inventory);
                    inventoryPane.getChildren().add(inventoryLabel);
                    allInventoryContainer.getChildren().add(inventoryPane);
                }
                textField = new TextField();
                inputGoal.getChildren().add(textField);
            }
        }
    }

    private void addGoal(String goalName) {
        Pane pane = new Pane();
        Label label = new Label();

        switch (goalName) {
            case "Gold" -> {
                GoldGoal goldGoal = new GoldGoal();
                goldGoal.goldGoal(intSpinner.getValue());
                label.setText(goalName + " : " + intSpinner.getValue());
            }
            case "Health" -> {
                HealthGoal healthGoal = new HealthGoal();
                healthGoal.healthGoal(intSpinner.getValue());
                label.setText(goalName + " : " + intSpinner.getValue());
            }
            case "Score" -> {
                ScoreGoal scoreGoal = new ScoreGoal();
                scoreGoal.scoreGoal(intSpinner.getValue());
                label.setText(goalName + " : " + intSpinner.getValue());
            }
            case "Inventory" -> {
                InventoryGoal inventoryGoal = new InventoryGoal();
                inventoryGoalList.add(textField.getText());
                inventoryGoal.inventoryGoal(inventoryGoalList);
                label.setText(goalName + " : " + textField.getText());
            }
        }

        updateGoalsList();
        clearAllContainers();
        pane.getChildren().add(label);
        goalsContainer.getChildren().add(pane);
    }



    private void addGoldGoal() {
        Label goalValueLabel = new Label("Enter gold value:");
        goalValueLabel.setLayoutX(100);
        inputGoal.getChildren().add(goalValueLabel);

        intSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
        inputGoal.getChildren().add(intSpinner);

        textField = new TextField();
        textField.setPromptText("Goal name");
        inputGoal.getChildren().add(textField);

        Label guidanceLabel = new Label("Collect gold to reach your goal.");
        guidanceGoal.getChildren().add(guidanceLabel);
    }

    private void addHealthGoal() {
        Label goalValueLabel = new Label("Enter health value:");
        goalValueLabel.setLayoutX(100);
        inputGoal.getChildren().add(goalValueLabel);

        intSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
        inputGoal.getChildren().add(intSpinner);

        textField = new TextField();
        textField.setPromptText("Goal name");
        inputGoal.getChildren().add(textField);

        Label guidanceLabel = new Label("Stay healthy to reach your goal.");
        guidanceGoal.getChildren().add(guidanceLabel);
    }

    private void addScoreGoal() {
        Label goalValueLabel = new Label("Enter score value:");
        goalValueLabel.setLayoutX(100);
        inputGoal.getChildren().add(goalValueLabel);

        intSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
        inputGoal.getChildren().add(intSpinner);

        textField = new TextField();
        textField.setPromptText("Goal name");
        inputGoal.getChildren().add(textField);

        Label guidanceLabel = new Label("Score points to reach your goal.");
        guidanceGoal.getChildren().add(guidanceLabel);
    }

    private void addInventoryGoal() {
        Label inventoryLabel = new Label("Add items to the inventory goal:");
        inventoryLabel.setLayoutX(100);
        allInventoryContainer.getChildren().add(inventoryLabel);

        HBox addInventoryBox = new HBox();
        addInventoryBox.setSpacing(10);
        addInventoryBox.setAlignment(Pos.CENTER);

        TextField inventoryField = new TextField();
        inventoryField.setPromptText("Item name");
        addInventoryBox.getChildren().add(inventoryField);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String item = inventoryField.getText();
            inventoryGoalList.add(item);
            inventoryField.clear();
            updateInventoryList();
        });

        addInventoryBox.getChildren().add(addButton);
        allInventoryContainer.getChildren().add(addInventoryBox);

        updateInventoryList();

        Label guidanceLabel = new Label("Collect all items in the inventory to reach your goal.");
        guidanceGoal.getChildren().add(guidanceLabel);
    }

    private void updateInventoryList() {
        allInventoryContainer.getChildren().clear();

        for (String item : inventoryGoalList) {
            Label itemLabel = new Label(item);
            allInventoryContainer.getChildren().add(itemLabel);
        }
    }

    private void updateGoalsList() {
        goalsContainer.getChildren().clear();
        /*for (Goal goal : currentStory.get) {
            Label goalLabel = new Label(goal.toString());
            goalsContainer.getChildren().add(goalLabel);
        }*/
    }

    private void clearAllContainers() {
        labelGoal.getChildren().clear();
        setGoalButton.getChildren().clear();
        inputGoal.getChildren().clear();
        guidanceGoal.getChildren().clear();
        allInventoryContainer.getChildren().clear();
        inventoryGoalList.clear();
        intSpinner = null;
        textField = null;
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

