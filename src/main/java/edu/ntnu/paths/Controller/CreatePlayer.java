package edu.ntnu.paths.Controller;

import edu.ntnu.paths.GameDetails.Player;
import edu.ntnu.paths.GameDetails.PlayerBuilder;
import edu.ntnu.paths.Managers.PlayerManager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.prefs.Preferences;

public class CreatePlayer {

    private final Preferences prefs = Preferences.userNodeForPackage(CreatePlayer.class);

    private BorderPane root;
    private VBox topVBox, centerVbox;
    private AnchorPane bottomAnchorPane;
    private Button createGoalsButton;

    private Label feedbackLabel;


    public void start(Stage stage) {
        root = new BorderPane();
        root.setPadding(new Insets(10));
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/Controller/style/create-player.css")).toExternalForm()
        );
        createGoalsButton = new Button("Create goals");
        createGoalsButton.setDisable(PlayerManager.getInstance().getPlayer() == null);

        createTop();
        createCentre();
        createBottom();

        root.setTop(topVBox);
        root.setCenter(centerVbox);
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

        Label header = new Label("Create Player");
        header.setId("header");
        topVBox = new VBox(imgAnchorPane, header);
        topVBox.setAlignment(Pos.CENTER);
    }

    private void createCentre() {
        centerVbox = new VBox(10);
        centerVbox.setId("playerDetails");
        centerVbox.setAlignment(Pos.CENTER);
        centerVbox.setPadding(new Insets(20));

        Label nameLabel = new Label("Player Name");
        TextField nameField = new TextField(prefs.get("name", ""));
        nameField.setMaxWidth(200);

        Label healthLabel = new Label("Player Health");
        Spinner<Integer> healthField = new Spinner<>(0, 100, prefs.getInt("health", 100));

        Label goldLabel = new Label("Player Gold");
        Spinner<Integer> goldField = new Spinner<>(0, 9999, prefs.getInt("gold", 10));

        feedbackLabel = new Label();
        feedbackLabel.setWrapText(true);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> handleSubmit(nameField, healthField, goldField));

        centerVbox.getChildren().addAll(nameLabel, nameField, healthLabel, healthField, goldLabel, goldField, submitButton, feedbackLabel);

        submitButton.setOnAction(event -> handleSubmit(nameField, healthField, goldField));
    }

    private void createBottom() {
        bottomAnchorPane = new AnchorPane();

        createGoalsButton.setOnAction(this::handleCreateGoals);
        AnchorPane.setBottomAnchor(createGoalsButton, 20.0);
        AnchorPane.setRightAnchor(createGoalsButton, 50.0);

        Button goBackButton = new Button("Go back");
        goBackButton.setOnAction(this::handleGoBack);
        AnchorPane.setBottomAnchor(goBackButton, 20.0);
        AnchorPane.setLeftAnchor(goBackButton, 50.0);

        bottomAnchorPane.getChildren().addAll(createGoalsButton, goBackButton);
        root.setBottom(bottomAnchorPane);

    }

    private void handleSubmit(TextField nameField, Spinner<Integer> healthField, Spinner<Integer> goldField) {
        String name = nameField.getText();
        int health = healthField.getValue();
        int gold = goldField.getValue();

        if (name.isEmpty()) {
            feedbackLabel.getStyleClass().add("errorMessage");
            feedbackLabel.setText("Please enter a name.");
            return;
        }

        if (health <= 0 || health > 100) {
            feedbackLabel.getStyleClass().add("errorMessage");
            feedbackLabel.setText("Health must be between 1 and 100.");
            return;
        }

        if (gold < 0 || gold > 9999) {
            feedbackLabel.getStyleClass().add("errorMessage");
            feedbackLabel.setText("Gold must be between 0 and 9999.");
            return;
        }

        prefs.put("name", name);
        prefs.putInt("health", health);
        prefs.putInt("gold", gold);


        feedbackLabel.setId("successMessage");
        feedbackLabel.setText("User created successfully.");

        Player newPlayer = PlayerBuilder.newInstance()
                .setName(name)
                .setHealth(health)
                .setGold(gold)
                .build();
        PlayerManager.getInstance().setPlayer(new Player(newPlayer));

        createGoalsButton.setDisable(false);
    }

    private void handleCreateGoals(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        CreateGoals createGoals = new CreateGoals();
        try {
            createGoals.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGoBack(ActionEvent actionEvent) {
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

}
