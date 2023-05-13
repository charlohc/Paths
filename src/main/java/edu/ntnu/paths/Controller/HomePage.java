package edu.ntnu.paths.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomePage {

    private Scene scene;
    
    public HomePage() {
        Label titleLabel = new Label("Paths");
        titleLabel.setStyle("-fx-font-size: 36pt; -fx-text-fill: black;");
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(350);
        titleLabel.setMinWidth(200);

        Label welcomeLabel = new Label("Welcome to Story Game");
        welcomeLabel.setStyle("-fx-font-size: 18pt; -fx-text-fill: black; -fx-padding: 100 0 0 0;");
        welcomeLabel.setWrapText(true);
        welcomeLabel.setMaxWidth(350);
        welcomeLabel.setMinWidth(250);

        Button importButton = new Button("Import story");
        importButton.setOnAction(this::openImportStoryScene);

        StackPane root = new StackPane(titleLabel, welcomeLabel, importButton);
        root.setStyle("-fx-background-size: cover;");
        root.getStylesheets().addAll(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/style.css")).toExternalForm());
        scene = new Scene(root, 1000, 600);
    }

    private void openImportStoryScene(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ImportedStory importedStory = new ImportedStory();
        try {
            importedStory.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Scene getScene() {
        return scene;
    }
    
}
