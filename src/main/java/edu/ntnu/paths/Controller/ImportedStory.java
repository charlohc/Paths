package edu.ntnu.paths.Controller;

import edu.ntnu.paths.FileHandling.ReadFile;
import edu.ntnu.paths.Managers.StoryManager;
import edu.ntnu.paths.StoryDetails.Story;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;
import java.util.prefs.Preferences;

public class ImportedStory {

    private final Preferences prefs = Preferences.userNodeForPackage(ImportedStory.class);

    private Label headerLabel;
    private Label infoLabel;
    private Button chooseFileBtn;
    private VBox topVBox;

    private Label locationLabel;
    private TextArea filePathTextArea;
    private Label nameLabel;
    private TextArea fileNameTextArea;
    private Label deadLinksLabel;
    private Label deadLinksValueLabel;
    private HBox fileInfoHBox;
    private Label errorLabel;
    private VBox centerVBox;

    private Button createPlayerButton;
    private Button goBackButton;
    private HBox bottomHBox;
    private AnchorPane bottomAnchorPane;


    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        root.setPrefSize(1000, 600);
        root.getStyleClass().add("import-story");

        createTop();
        createCenter();
        createBottom();

        root.setTop(topVBox);
        root.setCenter(centerVBox);
        root.setBottom(bottomAnchorPane);

        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/Style/import-story.css")).toExternalForm()
        );

        stage.setScene(scene);
        stage.show();
    }

    private void createTop() {
        headerLabel = new Label("Import story from file");
        headerLabel.setId("headerLabel");
        infoLabel = new Label("Here you can add a story file");
        infoLabel.setId("infoLabel");
        chooseFileBtn = new Button("Choose File");
        chooseFileBtn.setOnAction(e -> onSelectFileButtonClick());
        topVBox = new VBox(30, headerLabel, infoLabel, chooseFileBtn);
        topVBox.setId("topVBox");
        topVBox.setAlignment(Pos.CENTER);
    }

    private void createCenter() {
        locationLabel = new Label("Location");
        filePathTextArea = new TextArea();
        filePathTextArea.setEditable(false);
        filePathTextArea.setDisable(true);
        nameLabel = new Label("Name");
        fileNameTextArea = new TextArea();
        fileNameTextArea.setEditable(false);
        fileNameTextArea.setDisable(true);
        deadLinksLabel = new Label("Dead links");
        deadLinksValueLabel = new Label();
        fileInfoHBox = new HBox(30, new VBox(10, locationLabel, filePathTextArea), new VBox(10, nameLabel, fileNameTextArea), new VBox(10, deadLinksLabel, deadLinksValueLabel));
        fileInfoHBox.setAlignment(Pos.CENTER);
        errorLabel = new Label();
        centerVBox = new VBox(10, fileInfoHBox, errorLabel);
        centerVBox.setAlignment(Pos.CENTER);
    }

    private void createBottom() {
        bottomAnchorPane = new AnchorPane();

        createPlayerButton = new Button("Create Player");
        createPlayerButton.setOnAction(this::onCreatePlayerButtonClick);
        AnchorPane.setBottomAnchor(createPlayerButton, 20.0);
        AnchorPane.setRightAnchor(createPlayerButton, 50.0);

        goBackButton = new Button("Go back");
        goBackButton.setOnAction(this::onGoBackButtonClick);
        AnchorPane.setBottomAnchor(goBackButton, 20.0);
        AnchorPane.setLeftAnchor(goBackButton, 50.0);

        bottomAnchorPane.getChildren().addAll(createPlayerButton, goBackButton);
    }

    private void onSelectFileButtonClick() {
        FileChooser fileChooser = new FileChooser();
        ReadFile readFile = new ReadFile();
        fileChooser.setTitle("Choose story file");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Story storyFromFile = readFile.readFile(file);
            StoryManager.getInstance().setStory(storyFromFile);
            filePathTextArea.setText(file.getAbsolutePath());
            fileNameTextArea.setText(file.getName().replace(".paths",""));
            deadLinksValueLabel.setText(String.valueOf(storyFromFile.getBrokenLinks().size()));
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
}
