package edu.ntnu.paths.Controller;

import edu.ntnu.paths.FileHandling.ReadFile;
import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.Managers.PlayerManager;
import edu.ntnu.paths.Managers.StoryManager;
import edu.ntnu.paths.StoryDetails.Story;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ImportedStory {
    @FXML
    private TextField fileName;
    @FXML
    private Label deadLinks, errorMessage;

    @FXML
    private TextArea filePathField;

    @FXML
    protected void onSelectFileButtonClick() {
        ReadFile readFile = new ReadFile();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file");
        File selectedFile = fileChooser.showOpenDialog(null);
        Story storyFromFile = (Story) readFile.readFile(selectedFile);

        if (storyFromFile != null) {
            errorMessage.setText("");

            StoryManager.getInstance().setStory(new Story(storyFromFile));
            fileName.setText(selectedFile.getName());
            fileName.setDisable(false);

            filePathField.setText(selectedFile.getAbsolutePath());
            filePathField.setDisable(false);

            deadLinks.setText(String.valueOf(StoryManager.getInstance().getStory().getBrokenLinks().size()));
        } else {
            errorMessage.setText("Could not load file...");
        }
    }

    @FXML
    private void createPlayer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create-player.fxml"));
        Parent importedStory = loader.load();
        Scene importedStoryScene = new Scene(importedStory,1000,600);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(importedStoryScene);
        currentStage.show();
    }
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home-page.fxml"));
        Parent importedStory = loader.load();
        Scene importedStoryScene = new Scene(importedStory,1000,600);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(importedStoryScene);
        currentStage.show();
    }
}
