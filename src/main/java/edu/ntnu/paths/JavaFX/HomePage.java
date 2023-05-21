package edu.ntnu.paths.JavaFX;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The HomePage class represents the home page of the application.
 */
public class HomePage {

    private final Scene scene;

    /**
     * Constructs a new instance of the HomePage class.
     * Initializes the UI elements and sets up the scene.
     */
    public HomePage() {
        ImageView helpImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/help-button.png")).toExternalForm());
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
        root.setStyle("-fx-alignment: center;");
        root.setBackground(new Background(new BackgroundImage(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/edu/ntnu/paths/resources/img/background.jpg"))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        )));
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/style/home-page.css")).toExternalForm()
        );
        scene = new Scene(root, 1000, 600);
    }


    /**
     * Handles the action event when the "Import story" button is clicked.
     * @param actionEvent the action event triggered by clicking the "Import story" button
     */
    private void openImportStoryScene(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ImportedStory importedStory = new ImportedStory();
        try {
            importedStory.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action event when the "Information" button is clicked.
     * @param event the action event triggered by clicking the "Information" button
     */
    private void handleHelpPage(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        HelpPage helpPage = new HelpPage();
        helpPage.displayPopUp(stage);
    }

    /**
     * Returns the scene associated with the HomePage.
     * @return the scene of the home page
     */
    public Scene getScene() {
        return scene;
    }
}

