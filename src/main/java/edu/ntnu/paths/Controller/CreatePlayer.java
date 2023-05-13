package edu.ntnu.paths.Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.prefs.Preferences;

public class CreatePlayer {

    private final Preferences prefs = Preferences.userNodeForPackage(CreatePlayer.class);

    public void start(Stage stage) {

        // Create layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/create-player.css")).toExternalForm()
        );

        // Create header
        Label header = new Label("Create user");
        header.setStyle("-fx-font-size: 36pt; -fx-text-fill: black;");
        header.setAlignment(Pos.CENTER);
        root.setTop(header);

        // Create center
        VBox center = new VBox(10);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(20));

        Label nameLabel = new Label("Player Name");
        TextField nameField = new TextField(prefs.get("name", ""));
        nameField.setMaxWidth(200);

        Label healthLabel = new Label("Player Health");
        Spinner<Integer> healthField = new Spinner<>(0, 100, prefs.getInt("health", 100));

        Label goldLabel = new Label("Player Gold");
        Spinner<Integer> goldField = new Spinner<>(0, 9999, prefs.getInt("gold", 10));

        Label feedbackLabel = new Label();
        feedbackLabel.setWrapText(true);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> handleSubmit(nameField, healthField, goldField, feedbackLabel));

        center.getChildren().addAll(nameLabel, nameField, healthLabel, healthField, goldLabel, goldField, submitButton, feedbackLabel);
        root.setCenter(center);

        // Create bottom
        AnchorPane bottom = new AnchorPane();
        bottom.setPadding(new Insets(20));
        bottom.setPrefHeight(60);
        bottom.setStyle("-fx-background-color: #F4F4F4;");

        Button createGoalsButton = new Button("Create goals");
        //createGoalsButton.setDisable(true);
        createGoalsButton.setOnAction(this::handleCreateGoals);
        AnchorPane.setBottomAnchor(createGoalsButton, 20.0);
        AnchorPane.setRightAnchor(createGoalsButton, 50.0);
        createGoalsButton.setPrefSize(120, 40);

        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(this::handleGoBack);
        AnchorPane.setBottomAnchor(goBackButton, 20.0);
        AnchorPane.setLeftAnchor(goBackButton, 50.0);
        goBackButton.setPrefSize(120, 40);

        bottom.getChildren().addAll(createGoalsButton, goBackButton);
        root.setBottom(bottom);

        // Create scene and show stage
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void handleSubmit(TextField nameField, Spinner<Integer> healthField, Spinner<Integer> goldField, Label feedbackLabel) {
        String name = nameField.getText();
        int health = healthField.getValue();
        int gold = goldField.getValue();

        if (name.isEmpty()) {
            feedbackLabel.getStyleClass().add("errorMessage");
            feedbackLabel.setText("Please enter a name.");
            return;
        }

        if (health <= 0 || health > 100) {
            feedbackLabel.getStyleClass().add("errorMessage");
            feedbackLabel.setText("Health must be between 1 and 100.");
            return;
        }

        if (gold < 0 || gold > 9999) {
            feedbackLabel.getStyleClass().add("errorMessage");
            feedbackLabel.setText("Gold must be between 0 and 9999.");
            return;
        }
        // Store values in preferences
        prefs.put("name", name);
        prefs.putInt("health", health);
        prefs.putInt("gold", gold);

        // Show success message
        feedbackLabel.getStyleClass().add("successMessage");
        feedbackLabel.setText("User created successfully.");

        // Enable "Create goals" button
        Button createGoalsButton = (Button) feedbackLabel.getParent().getChildrenUnmodifiable().get(0);
        createGoalsButton.setDisable(false);
    }

    private void handleCreateGoals(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        CreateGoals createGoals = new CreateGoals();
        try {
            createGoals.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGoBack(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ImportedStory importedStory = new ImportedStory();
        try {
            importedStory.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
