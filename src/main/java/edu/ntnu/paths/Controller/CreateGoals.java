package edu.ntnu.paths.Controller;

import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.Managers.PlayerManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class CreateGoals {

    public Pane goalsContainer;
    @FXML
    private ComboBox<String> dropdown;

    @FXML
    private Label contentLabel;

    @FXML
    private void initialize() {
        dropdown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleDropdownChange(newValue);
        });
    }

    private void handleDropdownChange(String selectedOption) {
        switch (selectedOption) {
            case "Gold" -> contentLabel.setText("Gold Goal");
            case "Health" -> contentLabel.setText("Health Goal");
            case "Inventory" -> contentLabel.setText("Inventory Goal");
            case "Score" -> contentLabel.setText("Score Goal");
        }
    }
    @FXML
    private void importStory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("import-story.fxml"));
        Parent importedStory = loader.load();
        Scene importedStoryScene = new Scene(importedStory, 1000, 600);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(importedStoryScene);
        currentStage.show();
    }
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create-user.fxml"));
        Parent importedStory = loader.load();
        Scene importedStoryScene = new Scene(importedStory, 1000, 600);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(importedStoryScene);
        currentStage.show();
    }
}
