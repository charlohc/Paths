package edu.ntnu.paths.Controller;

import edu.ntnu.paths.Goals.GoldGoal;
import edu.ntnu.paths.Goals.HealthGoal;
import edu.ntnu.paths.Goals.InventoryGoal;
import edu.ntnu.paths.Goals.ScoreGoal;
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
    private Spinner<Integer> intSpinner;
    private TextField textField;
    private ArrayList<String> inventoryGoalList = new ArrayList<>();
    @FXML
    public VBox goalsContainer;
    @FXML
    public StackPane inputGoal, setGoalButton, labelGoal;
    @FXML
    private ComboBox<String> dropdown;


    @FXML
    private void initialize() {
        dropdown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleDropdownChange(newValue);
        });
    }

    private void handleDropdownChange(String selectedOption) {
        labelGoal.getChildren().clear();
        inputGoal.getChildren().clear();
        setGoalButton.getChildren().clear();

        Label goalNameLabel = new Label(selectedOption + " goal");
        goalNameLabel.setLayoutX(100);
        labelGoal.getChildren().add(goalNameLabel);

        Button addGoalButton = new Button("add goal");
        setGoalButton.getChildren().add(addGoalButton);
        addGoalButton.setOnAction(e -> {
            addGoal(selectedOption);
        });

        intSpinner = new Spinner<>();
        intSpinner.setEditable(true);
        int minVal = 1;
        int maxVal = 100;
        int initialValue = 50;
        int step = 1;
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minVal, maxVal, initialValue, step);
        intSpinner.setValueFactory(valueFactory);
        textField = new TextField();


        switch (selectedOption) {
            case "Gold", "Health", "Score" -> inputGoal.getChildren().add(intSpinner);
            case "Inventory" -> inputGoal.getChildren().add(textField);
        }
    }

    private void addGoal(String goalName) {

        switch (goalName) {
            case "Gold" -> {
                GoldGoal goldGoal = new GoldGoal();
                goldGoal.goldGoal(intSpinner.getValue());
            }
            case "Health" -> {
                HealthGoal healthGoal = new HealthGoal();
                healthGoal.healthGoal(intSpinner.getValue());
            }
            case "Inventory" -> {
                InventoryGoal inventoryGoal = new InventoryGoal();
                inventoryGoalList.add(textField.getText());
                inventoryGoal.inventoryGoal(inventoryGoalList);
            }
            case "Score" -> {
                ScoreGoal scoreGoal = new ScoreGoal();
                scoreGoal.scoreGoal(intSpinner.getValue());
            }
        }
        Pane pane = new Pane();
        Label label;

        switch (goalName) {
            case "Gold", "Health", "Score":
                label = new Label(goalName + " : " + intSpinner.getValue());
                pane.getChildren().add(label);
                goalsContainer.getChildren().add(pane);
                break;

            case "Inventory":
                label = new Label(goalName + " : " + textField.getText());
                pane.getChildren().add(label);
                goalsContainer.getChildren().add(pane);

                break;
        }

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
