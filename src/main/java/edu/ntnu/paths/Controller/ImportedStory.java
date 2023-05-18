package edu.ntnu.paths.Controller;

import edu.ntnu.paths.FileHandling.ReadFile;
import edu.ntnu.paths.Managers.StoryManager;
import edu.ntnu.paths.StoryDetails.Story;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;
import java.util.prefs.Preferences;

public class ImportedStory {

    private final Preferences prefs = Preferences.userNodeForPackage(ImportedStory.class);

    private Label deadLinksValueLabel, feedbackLabel;
    private VBox topVBox;
    private TextArea filePathTextArea, fileNameTextArea;
    private VBox centerVBox;
    private AnchorPane bottomAnchorPane;

    private Button createPlayerButton;


    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/style/import-story.css")).toExternalForm()
        );
        createPlayerButton = new Button("Create Player");
        createPlayerButton.setDisable(StoryManager.getInstance().getStory() == null);

        feedbackLabel = new Label();

        createTop();
        createCenter();
        createBottom();

        root.setTop(topVBox);
        root.setCenter(centerVBox);
        root.setBottom(bottomAnchorPane);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void createTop() {
        ImageView imageView = new ImageView(getClass().getResource("/edu/ntnu/paths/Controller/img/help-button.png").toExternalForm());
        imageView.setFitWidth(40);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        AnchorPane imgAnchorPane = new AnchorPane();
        AnchorPane.setRightAnchor(imageView, 10.0);
        imgAnchorPane.getChildren().add(imageView);

        imgAnchorPane.setOnMouseClicked(this::handleHelpPage);

        Label headerLabel = new Label("Import story from file");
        headerLabel.setId("headerLabel");

        Label infoLabel = new Label("Here you can add a story file");
        infoLabel.setId("infoLabel");
        Button chooseFileBtn = new Button("Choose File");
        chooseFileBtn.setOnAction(e -> onSelectFileButtonClick());
        topVBox = new VBox(30, imgAnchorPane, headerLabel, infoLabel, chooseFileBtn);
        topVBox.setId("topVBox");
        topVBox.setAlignment(Pos.CENTER);
    }

    private void createCenter() {
        Label locationLabel = new Label("Location");
        filePathTextArea = new TextArea( prefs.get("filePath", ""));
        filePathTextArea.setEditable(false);
        filePathTextArea.setDisable(true);
        Label nameLabel = new Label("Name");
        fileNameTextArea = new TextArea(prefs.get("fileName", ""));
        fileNameTextArea.setEditable(false);
        fileNameTextArea.setDisable(true);
        Label deadLinksLabel = new Label("Dead links");
        deadLinksValueLabel = new Label( prefs.get("deadLinks", ""));
        HBox fileInfoHBox = new HBox(30, new VBox(10, locationLabel, filePathTextArea), new VBox(10, nameLabel, fileNameTextArea), new VBox(10, deadLinksLabel, deadLinksValueLabel));
        fileInfoHBox.setAlignment(Pos.CENTER);
        Label errorLabel = new Label();
        centerVBox = new VBox(10, fileInfoHBox, errorLabel);
        centerVBox.getChildren().add(feedbackLabel);
        centerVBox.setAlignment(Pos.CENTER);
    }

    private void createBottom() {
        bottomAnchorPane = new AnchorPane();
        createPlayerButton.setOnAction(this::onCreatePlayerButtonClick);
        AnchorPane.setBottomAnchor(createPlayerButton, 20.0);
        AnchorPane.setRightAnchor(createPlayerButton, 50.0);

        Button goBackButton = new Button("Go back");
        goBackButton.setOnAction(this::onGoBackButtonClick);
        AnchorPane.setBottomAnchor(goBackButton, 20.0);
        AnchorPane.setLeftAnchor(goBackButton, 50.0);

        bottomAnchorPane.getChildren().addAll(createPlayerButton, goBackButton);
    }

    private void onSelectFileButtonClick() {
        feedbackLabel.setText("");
        FileChooser fileChooser = new FileChooser();
        ReadFile readFile = new ReadFile();
        fileChooser.setTitle("Choose story file");
        File file = fileChooser.showOpenDialog(null);
        Story storyFromFile = readFile.readFile(file);
        if (storyFromFile != null) {
            StoryManager.getInstance().setStory(storyFromFile);
            filePathTextArea.setText(file.getAbsolutePath());
            fileNameTextArea.setText(file.getName().replace(".paths",""));
            deadLinksValueLabel.setText(String.valueOf(storyFromFile.getBrokenLinks().size()));

            prefs.put("filePath", file.getAbsolutePath());
            prefs.put("fileName", file.getName().replace(".paths",""));
            prefs.put("deadLinks", String.valueOf(storyFromFile.getBrokenLinks().size()));

            filePathTextArea.setDisable(false);
            fileNameTextArea.setDisable(false);
            createPlayerButton.setDisable(false);

        } else {
            feedbackLabel.setText("Could not load file...");
        }
    }

    private void onCreatePlayerButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        CreatePlayer createPlayer = new CreatePlayer();
        try {
            createPlayer.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onGoBackButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        HomePage homePage = new HomePage();
        try {
            Scene scene = homePage.getScene();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handleHelpPage(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        HelpPage helpPage = new HelpPage();
        helpPage.displayPopUp(stage);
    }
}
