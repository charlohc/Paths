package edu.ntnu.paths.Controller;

import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.GameDetails.PlayerBuilder;
import edu.ntnu.paths.Managers.PlayerManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.prefs.Preferences;

public class CreateUser {

    private final Preferences prefs = Preferences.userNodeForPackage(CreateUser.class);

    public Button createGoalsButton;

    @FXML
    private TextField nameField;

    @FXML
    private Spinner<Integer> healthField, goldField;

    @FXML
    private Label feedBackLabel;

    @FXML
    private void initialize() {
        if (PlayerManager.getInstance().playerExist()) createGoalsButton.setDisable(false);

        nameField.setText(prefs.get("name", ""));
        healthField.getValueFactory().setValue(prefs.getInt("health", 100));
        goldField.getValueFactory().setValue(prefs.getInt("gold", 10));

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            prefs.put("name", newValue);
        });

        healthField.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            prefs.putInt("health", newValue);
        });

        goldField.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            prefs.putInt("gold", newValue);
        });
    }

    @FXML
    private void handleSubmit() {
        String name = nameField.getText();
        int health = healthField.getValue();
        int gold = goldField.getValue();

        if (name.isEmpty()) {
            feedBackLabel.getStyleClass().add("errorMessage");
            feedBackLabel.setText("Please enter a name.");
            return;
        }

        if (health < 0 || health > 100) {
            feedBackLabel.getStyleClass().add("errorMessage");
            feedBackLabel.setText("Health must be between 0 and 100.");
            return;
        }

        if (gold < 0 || gold > 9999) {
            feedBackLabel.getStyleClass().add("errorMessage");
            feedBackLabel.setText("Gold must be between 0 and 9999.");
            return;
        }

        prefs.put("name", name);
        prefs.putInt("health", health);
        prefs.putInt("gold", gold);

        feedBackLabel.getStyleClass().remove("errorMessage");
        feedBackLabel.setText("Player details saved.");

        Player newPlayer = PlayerBuilder.newInstance()
                .setName(name)
                .setHealth(health)
                .setGold(gold)
                .build();

        PlayerManager.getInstance().setPlayer(new Player(newPlayer));

        createGoalsButton.setDisable(false);
    }

    @FXML
    private void importStory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create-goals.fxml"));
        Parent importedStory = loader.load();
        Scene importedStoryScene = new Scene(importedStory, 1000, 600);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(importedStoryScene);
        currentStage.show();
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home-page.fxml"));
        Parent importedStory = loader.load();
        Scene importedStoryScene = new Scene(importedStory, 1000, 600);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(importedStoryScene);
        currentStage.show();
    }
}

