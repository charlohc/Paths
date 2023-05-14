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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomePage {

    private Scene scene;

    public HomePage() {
        Label titleLabel = new Label("Paths");
        titleLabel.setId("titleLabel");

        Label welcomeLabel = new Label("Welcome to Story Game");
        welcomeLabel.setId("welcomeLabel");

        Button importButton = new Button("Import story");
        importButton.setOnAction(this::openImportStoryScene);
        importButton.setId("importButton");

        VBox container = new VBox(titleLabel, welcomeLabel, importButton);
        container.setId("container");
        container.getStyleClass().add("container");
        container.setSpacing(40);

        StackPane root = new StackPane(container);
        root.setStyle("-fx-background-size: cover; -fx-alignment: center");
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/home-page.css")).toExternalForm()
        );
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

