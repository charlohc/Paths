package edu.ntnu.paths.JavaFX;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class HomePage {

    private final Scene scene;

    public HomePage() {
        ImageView helpImageView = new ImageView(getClass().getResource("/edu/ntnu/paths/Controller/img/help-button.png").toExternalForm());
        helpImageView.setFitWidth(40);
        helpImageView.setPreserveRatio(true);
        helpImageView.setSmooth(true);

        AnchorPane imgAnchorPane = new AnchorPane();
        AnchorPane.setRightAnchor(helpImageView, 10.0);
        imgAnchorPane.getChildren().add(helpImageView);

        imgAnchorPane.setOnMouseClicked(this::handleHelpPage);

        Label titleLabel = new Label("Paths");
        titleLabel.setId("titleLabel");

        Label welcomeLabel = new Label("Welcome to Story Game");
        welcomeLabel.setId("welcomeLabel");

        Button importButton = new Button("Import story");
        importButton.setOnAction(this::openImportStoryScene);
        importButton.setId("importButton");

        VBox container = new VBox(imgAnchorPane, titleLabel, welcomeLabel, importButton);
        container.setId("container");
        container.getStyleClass().add("container");
        container.setSpacing(40);

        StackPane root = new StackPane(container);
        root.setStyle("-fx-background-size: cover; -fx-alignment: center");
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/style/home-page.css")).toExternalForm()
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
    private void handleHelpPage(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        HelpPage helpPage = new HelpPage();
        helpPage.displayPopUp(stage);
    }

    public Scene getScene() {
        return scene;
    }
}

