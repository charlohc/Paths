package edu.ntnu.paths.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePage {

    @FXML
    private void importStory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("import-story.fxml"));
        Parent importedStory = loader.load();
        Scene importedStoryScene = new Scene(importedStory,1000,600);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(importedStoryScene);
        currentStage.show();
    }
}