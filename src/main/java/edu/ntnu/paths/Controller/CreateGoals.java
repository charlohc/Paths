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
    private TextField inventoryTextField;
    private ArrayList<String> inventoryGoalList = new ArrayList<>();

    private Pane labelGoal,guidanceGoal,inputGoal;
    private BorderPane root;

    private Pane goalsContainerPane;
    private VBox goalsContainerVBox;
    private ComboBox<String> goalDropdown;
    private VBox createGoalContainer;
    private VBox allInventoryContainer;
    private AnchorPane bottomAnchorPane;
    private Button addGoalButton;

    public void start(Stage stage) {
        currentStory = StoryManager.getInstance().getStory();

        root = new BorderPane();
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/create-goals.css")).toExternalForm()
        );

        // Top section
        Label header = new Label("Set your goals for the game");
        header.setId("header");
        VBox pane = new VBox(header);
        pane.setAlignment(Pos.CENTER);
        root.setTop(pane);

        // Left section
        VBox dropdownContainer = new VBox();
        dropdownContainer.setSpacing(10);
        dropdownContainer.setAlignment(Pos.TOP_LEFT);
        dropdownContainer.setId("dropDownContainer");
        goalDropdown = new ComboBox<>();
        goalDropdown.getItems().addAll("Gold", "Health", "Score", "Inventory");
        goalDropdown.setPromptText("Select a goal");

        goalDropdown.setOnAction(e -> {  String selectedOption = goalDropdown.getValue().toString(); handleDropdownChange(selectedOption);});

        dropdownContainer.getChildren().add(goalDropdown);

        root.setLeft(dropdownContainer);

        // Center section
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

        createGoalContainer.getChildren().addAll(labelGoal, inputGoal, guidanceGoal, allInventoryContainer);
        root.setCenter(createGoalContainer);

        // Right section
        goalsContainerVBox = new VBox();
        goalsContainerVBox.setSpacing(10);
        goalsContainerVBox.setId("goalsContainer");
        goalsContainerVBox.setAlignment(Pos.TOP_CENTER);
        goalsContainerPane = new Pane(goalsContainerVBox);
        BorderPane.setMargin(goalsContainerPane, new Insets(100, 50, 0, 0));
        root.setRight(goalsContainerPane);

        // Bottom section
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
        root.setBottom(bottomAnchorPane);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
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
                for (String inventory : currentStory.getAllInventoryItems()) {
                    Pane inventoryPane = new Pane();
                    inventoryPane.getChildren().add(new Label(inventory));
                    allInventoryContainer.getChildren().add(inventoryPane);
                }
                 inventoryTextField = new TextField();
                inputGoal.getChildren().add(inventoryTextField);
            }
        }
        // Add the "add goal" button to the container if it doesn't exist
        if (!createGoalContainer.getChildren().contains(addGoalButton)) {
            createGoalContainer.getChildren().add(addGoalButton);
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
                inventoryGoalList.add(inventoryTextField.getText());
                inventoryGoal.inventoryGoal(inventoryGoalList);
                label.setText(goalName + " : " + inventoryTextField.getText());
            }
        }
        pane.getChildren().add(label);
        goalsContainerVBox.getChildren().add(pane);
    }


    private void clearAllContainers() {
        labelGoal.getChildren().clear();
        inputGoal.getChildren().clear();
        guidanceGoal.getChildren().clear();
        allInventoryContainer.getChildren().clear();
        inventoryGoalList.clear();
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

