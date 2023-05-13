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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.prefs.Preferences;

public class CreatePlayer {

    private final Preferences prefs = Preferences.userNodeForPackage(CreatePlayer.class);

    public Button createGoalsButton;

    @FXML
    private TextField nameField;

    @FXML
    private Label feedBackLabel;

    @FXML
    private void initialize() {
        if (PlayerManager.getInstance().playerExist()) createGoalsButton.setDisable(false);

        nameField.setText(prefs.get("name", ""));

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            prefs.put("name", newValue);
        });

    }

    @FXML
    private void handleSubmit() {
        String name = nameField.getText();

        if (name.isEmpty()) {
            feedBackLabel.getStyleClass().add("errorMessage");
            feedBackLabel.setText("Please enter a name.");
            return;
        }


        prefs.put("name", name);

        feedBackLabel.getStyleClass().remove("errorMessage");
        feedBackLabel.setText("Player details saved.");

        Player newPlayer = PlayerBuilder.newInstance()
                .setName(name)
                .build();

        PlayerManager.getInstance().setPlayer(new Player(newPlayer));

        createGoalsButton.setDisable(false);
    }

    @FXML
    private void createGoals(ActionEvent event) throws IOException {
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

