package edu.ntnu.paths.Controller;

import edu.ntnu.paths.Goals.GoldGoal;
import edu.ntnu.paths.Goals.HealthGoal;
import edu.ntnu.paths.Goals.InventoryGoal;
import edu.ntnu.paths.Goals.ScoreGoal;
import edu.ntnu.paths.Managers.StoryManager;
import edu.ntnu.paths.StoryDetails.Story;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class CreateGoals {
    private Story currentStory;
    private Spinner<Integer> intSpinner;
    private TextField textField;
    private ArrayList<String> inventoryGoalList = new ArrayList<>();

    @FXML
    private VBox goalsContainer, allInventoryContainer;

    @FXML
    private StackPane inputGoal, setGoalButton, labelGoal, guidanceGoal;

    @FXML
    private ComboBox<String> goalDropdown;

    @FXML
    private void initialize() {
        currentStory = StoryManager.getInstance().getStory();

        goalDropdown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleDropdownChange(newValue);
        });
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

    private void clearAllContainers() {
        labelGoal.getChildren().clear();
        inputGoal.getChildren().clear();
        guidanceGoal.getChildren().clear();
        setGoalButton.getChildren().clear();
        allInventoryContainer.getChildren().clear();
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

        pane.getChildren().add(label);
        goalsContainer.getChildren().add(pane);
    }


    @FXML
    private void beginGame(ActionEvent event) throws IOException {

    }
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create-player.fxml"));
        Parent importedStory = loader.load();
        Scene importedStoryScene = new Scene(importedStory, 1000, 600);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(importedStoryScene);
        currentStage.show();
    }
}
