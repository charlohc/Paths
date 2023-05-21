package edu.ntnu.paths.JavaFX;

import edu.ntnu.paths.GameDetails.Game;
import edu.ntnu.paths.GameDetails.GameBuilder;
import edu.ntnu.paths.Goals.*;
import edu.ntnu.paths.Managers.GameManager;
import edu.ntnu.paths.Managers.PlayerManager;
import edu.ntnu.paths.Managers.StoryManager;
import edu.ntnu.paths.StoryDetails.Story;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The CreateGoals class lets the user set goals to achieve throughout the gameplay.
 */
public class CreateGoals {

    private Game newGame;
    private Story currentStory;
    private Spinner<Integer> intSpinner;
    private TextField inventoryTextField;
    private ArrayList<String> inventoryGoalList = new ArrayList<>();
    private ArrayList<Goal> goals;
    private ArrayList<String> allInventoryStory;
    private Pane labelGoal,guidanceGoal,inputGoal, goalsContainerPane;
    private VBox topVBox, dropdownContainer, goalsContainerVBox, createGoalContainer, allInventoryVBox, buttonContainer;
    private ScrollPane allInventoryScrollPane;
    private ComboBox<String> goalDropdown;
    private AnchorPane bottomAnchorPane;
    private Label feedbackLabel;

    /**
     * Starts the CreateGoals scene.
     * @param stage the JavaFX stage to display the scene
     *
     */
    public void start(Stage stage) {
        currentStory = StoryManager.getInstance().getStory();
        newGame = GameManager.getInstance().getGame();
        allInventoryStory = currentStory.getAllInventoryItems();
        goalsContainerVBox = new VBox();
         goals = new ArrayList<>();

        BorderPane root = new BorderPane();
        root.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/style/style.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/style/create-goals.css")).toExternalForm()
        );

        createTop();
        createLeftSection();
        createCentre();
        createRightSection();
        createBottom();

        root.setTop(topVBox);

        root.setLeft(dropdownContainer);

        root.setCenter(createGoalContainer);

        root.setRight(goalsContainerPane);

        root.setBottom(bottomAnchorPane);

        if (newGame != null) {
            goals = (ArrayList<Goal>) newGame.getGoals();
            if (goals != null) addGoalsToContainer();
        }

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Created the top of the application
     * Contains information about the scene
     */
    private void createTop() {
        ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/edu/ntnu/paths/resources/img/help-button.png")).toExternalForm());
        imageView.setFitWidth(40);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        AnchorPane imgAnchorPane = new AnchorPane();
        AnchorPane.setRightAnchor(imageView, 10.0);
        imgAnchorPane.getChildren().add(imageView);

        imgAnchorPane.setOnMouseClicked(this::handleHelpPage);

        Label header = new Label("Set your goals for the game");
        header.setId("header");
        Label infoGoalsLabel = new Label("You can have as many goals as you would like,\nbut you can only have one of the score, gold and health type");
        infoGoalsLabel.setId("infoGoalsLabel");
        topVBox = new VBox(imgAnchorPane, header, infoGoalsLabel);
        topVBox.setAlignment(Pos.CENTER);
    }

    /**
     * Creates the left section of the scene
     * Contains a drop-down menu with the different goals type
     */
    private void createLeftSection() {
        dropdownContainer = new VBox();
        dropdownContainer.setSpacing(10);
        dropdownContainer.setAlignment(Pos.TOP_LEFT);
        dropdownContainer.setId("dropDownContainer");
        goalDropdown = new ComboBox<>();
        goalDropdown.getItems().addAll("Gold", "Health", "Score", "Inventory");
        goalDropdown.setPromptText("Select a goal");

        goalDropdown.setOnAction(e -> {  String selectedOption = goalDropdown.getValue(); handleDropdownChange(selectedOption);});

        dropdownContainer.getChildren().add(goalDropdown);
    }

    /**
     * Creates the centre of the scene
     * Contains a field for selecting the goal, and information about the extent of the goal in the game
     */
    private void createCentre() {
        createGoalContainer = new VBox();
        createGoalContainer.setSpacing(15);
        createGoalContainer.setAlignment(Pos.TOP_LEFT);
        createGoalContainer.setStyle("-fx-padding: 110px 0px 0px 0px");

        labelGoal = new Pane();

        inputGoal = new Pane();

        guidanceGoal = new VBox();

        buttonContainer = new VBox();

        allInventoryVBox = new VBox();
        allInventoryVBox.setSpacing(10);
        allInventoryVBox.setAlignment(Pos.TOP_CENTER);

        feedbackLabel = new Label();

        createGoalContainer.getChildren().addAll(labelGoal, inputGoal, guidanceGoal, allInventoryVBox, feedbackLabel, buttonContainer);
    }

    /**
     * Creates the right section of the scene
     * Contains a container with the saved goals
     */
    private void createRightSection() {
        Label yourGoalsLabel = new Label("Your selected goals");
        yourGoalsLabel.setId("yourGoalsLabel");
        goalsContainerVBox.setSpacing(10);
        goalsContainerVBox.setId("goalsContainer");
        goalsContainerVBox.setAlignment(Pos.TOP_CENTER);
        goalsContainerPane = new Pane(yourGoalsLabel, goalsContainerVBox);
        BorderPane.setMargin(goalsContainerPane, new Insets(100, 50, 0, 0));
    }

    /**
     * Creates the bottom of the scene
     * Containing buttons to maneuver to other scenes
     */
    private void createBottom() {
        bottomAnchorPane = new AnchorPane();

        Button startGameButton = new Button("Start game");
        startGameButton.setOnAction(this::beginGame);
        AnchorPane.setBottomAnchor(startGameButton, 20.0);
        AnchorPane.setRightAnchor(startGameButton, 50.0);

        Button goBackButton = new Button("Go back");
        goBackButton.setOnAction(this::goBack);
        AnchorPane.setBottomAnchor(goBackButton, 20.0);
        AnchorPane.setLeftAnchor(goBackButton, 50.0);

        bottomAnchorPane.getChildren().addAll(startGameButton, goBackButton);
    }

    /**
     * Handles the change of the dropdown selection in the CreateGoals scene.
     * @param selectedOption the selected option from the dropdown
     */
    private void handleDropdownChange(String selectedOption) {
        clearAllContainers();
        clearButtons();
        allInventoryVBox.setId(null);

        if (goals == null) goals = new ArrayList<>();

        Label goalNameLabel = new Label(selectedOption + " goal");
        labelGoal.getChildren().add(goalNameLabel);

        int minVal = 0;
        int step = 1;

        switch (selectedOption) {
            case "Gold":
                int maxVal = currentStory.findMaxGold();
                int initialValue = currentStory.findMaxGold() / 2;

                Label goldGuidanceLabel = new Label("The best path in the game gives " + maxVal + " gold");
                guidanceGoal.getChildren().add(goldGuidanceLabel);

                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minVal, maxVal, initialValue, step);
                intSpinner = new Spinner<>();
                intSpinner.setEditable(false);
                intSpinner.setValueFactory(valueFactory);

                inputGoal.getChildren().add(intSpinner);
                addButtonWithImage(selectedOption, getClass().getResource("/edu/ntnu/paths/resources/img/coin.png").toExternalForm());
                break;
            case "Health":
                maxVal = 100;
                initialValue = 50;
                valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minVal, maxVal, initialValue, step);
                intSpinner = new Spinner<>();
                intSpinner.setEditable(false);
                intSpinner.setValueFactory(valueFactory);

                inputGoal.getChildren().add(intSpinner);
                addButtonWithImage(selectedOption, getClass().getResource("/edu/ntnu/paths/resources/img/heart.png").toExternalForm());
                break;
            case "Score":
                maxVal = currentStory.findMaxScore();
                initialValue = currentStory.findMaxScore() / 2;

                Label scoreGuidanceLabel = new Label("The best path in the game gives " + maxVal + " score");
                guidanceGoal.getChildren().add(scoreGuidanceLabel);

                valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minVal, maxVal, initialValue, step);
                intSpinner = new Spinner<>();
                intSpinner.setEditable(false);
                intSpinner.setValueFactory(valueFactory);

                inputGoal.getChildren().add(intSpinner);
                addButtonWithImage(selectedOption, getClass().getResource("/edu/ntnu/paths/resources/img/trophy.png").toExternalForm());
                break;
            case "Inventory":
                Label inventoryGuidanceLabel = new Label("Below you can see all items in the game");
                guidanceGoal.getChildren().add(inventoryGuidanceLabel);
                for (String inventory : allInventoryStory) {
                    Pane inventoryPane = new Pane();
                    inventoryPane.getChildren().add(new Label(inventory));
                    allInventoryVBox.getChildren().add(inventoryPane);
                }
                allInventoryVBox.setId("allInventoryVBox");
                inventoryTextField = new TextField();
                inputGoal.getChildren().add(inventoryTextField);
                allInventoryScrollPane = new ScrollPane(allInventoryVBox);
                addButtonWithImage(selectedOption, getClass().getResource("/edu/ntnu/paths/resources/img/bag.png").toExternalForm());
                break;
        }
    }

    /**
     * Adds a button with an image to the button container.
     * @param goalType the type of goal associated with the button
     * @param imagePath the path to the image for the button
     */
    private void addButtonWithImage(String goalType, String imagePath) {
        Button button = createButtonWithImage(goalType, imagePath);
        buttonContainer.getChildren().add(button);
    }

    /**
     * Creates a button with an image and text for adding a goal.
     * @param goalType the type of goal associated with the button
     * @param imagePath the path to the image for the button
     * @return the created button
     */
    private Button createButtonWithImage(String goalType, String imagePath) {
        Button button = new Button();
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(35);

        Label addGoalLabel = new Label("Add goal");
        addGoalLabel.setTextFill(Color.WHITE);

        HBox contentBox = new HBox();
        contentBox.setSpacing(10);
        contentBox.getChildren().addAll(addGoalLabel, imageView);

        button.setGraphic(contentBox);

        button.setOnAction(e -> {
            addGoal(goalType);
        });

        return button;
    }


    /**
     * Removes all the buttons in the buttonsContainer
     */
    private void clearButtons() {
        buttonContainer.getChildren().clear();
    }

    /**
     * Clears all the containers
     */
    private void clearAllContainers() {
        feedbackLabel.setText("");
        labelGoal.getChildren().clear();
        inputGoal.getChildren().clear();
        guidanceGoal.getChildren().clear();
        allInventoryVBox.getChildren().clear();
        intSpinner = null;
        inventoryTextField = null;
    }

    /**
     * Adds a goal of the specified type to the list of goals.
     * @param goalName The name of the goal type to add
     */
    private void addGoal(String goalName) {
        switch (goalName) {
            case "Gold" -> {
                boolean goldGoalExists = false;
                for (Goal goal : goals) {
                    if (goal instanceof GoldGoal) {
                        goldGoalExists = true;
                        break;
                    }
                }
                if (goldGoalExists) {
                    goals.removeIf(goal -> goal instanceof GoldGoal);
                }
                GoldGoal goldGoal = new GoldGoal();
                goldGoal.goldGoal(intSpinner.getValue());
                goals.add(goldGoal);
            }
            case "Health" -> {
                boolean healthGoalExist = false;
                for (Goal goal : goals) {
                    if (goal instanceof HealthGoal) {
                        healthGoalExist = true;
                        break;
                    }
                }
                if (healthGoalExist) {
                    goals.removeIf(goal -> goal instanceof HealthGoal);
                }
                HealthGoal healthGoal = new HealthGoal();
                healthGoal.healthGoal(intSpinner.getValue());
                goals.add(healthGoal);
            }
            case "Score" -> {
                boolean scoreGoalExist = false;
                for (Goal goal : goals) {
                    if (goal instanceof ScoreGoal) {
                        scoreGoalExist = true;
                        break;
                    }
                }
                if (scoreGoalExist) {
                    goals.removeIf(goal -> goal instanceof ScoreGoal);
                }
                ScoreGoal scoreGoal = new ScoreGoal();
                scoreGoal.scoreGoal(intSpinner.getValue());
                goals.add(scoreGoal);
            }
            case "Inventory" -> {
                String inventorySuggestion = inventoryTextField.getText().toLowerCase();
                if (!allInventoryStory.contains(inventorySuggestion)) {
                    feedbackLabel.setText("Inventory not in story");
                    feedbackLabel.setTextFill(Color.RED);
                } else if (inventoryGoalList.contains(inventorySuggestion)) {
                    feedbackLabel.setText("Goal already added");
                    feedbackLabel.setTextFill(Color.RED);
                } else {
                    feedbackLabel.setTextFill(Color.BLACK);
                    InventoryGoal inventoryGoal = new InventoryGoal();
                    inventoryGoalList.add(inventorySuggestion.toLowerCase());
                    inventoryGoal.inventoryGoal(inventoryGoalList);
                    goals.add(inventoryGoal);
                    feedbackLabel.setText("");
                }

            }

        }
         newGame = GameBuilder.newInstance()
                        .setStory(currentStory)
                                .setPlayer(PlayerManager.getInstance().getPlayer())
                                        .setGoals(new ArrayList<>(goals))
                                                .build();

        GameManager.getInstance().setGame(new Game(newGame));
        addGoalsToContainer();
    }

    /**
     * Adds the goals to the goals container in the user interface.
     */
    private void addGoalsToContainer() {
        goalsContainerVBox.getChildren().clear();
        boolean addedInventoryGoal = false;

        for (Goal goal : goals) {
            HBox hBox = null;

            if (goal instanceof InventoryGoal inventoryGoal) {
                if (!addedInventoryGoal) {
                    List<String> mandatoryItems = inventoryGoal.getInventory();
                    for (String item : mandatoryItems) {
                        hBox = createGoalHBox("Inventory: " + item, inventoryGoal);
                        goalsContainerVBox.getChildren().add(hBox);
                    }
                    addedInventoryGoal = true;
                }
            } else if (goal instanceof GoldGoal goldGoal) {
                hBox = createGoalHBox("Gold: " + goldGoal.getGold(), goldGoal);
                goalsContainerVBox.getChildren().add(hBox);
            } else if (goal instanceof ScoreGoal scoreGoal) {
                hBox = createGoalHBox("Score: " + scoreGoal.getScore(), scoreGoal);
                goalsContainerVBox.getChildren().add(hBox);
            } else if (goal instanceof HealthGoal healthGoal) {
                hBox = createGoalHBox("Health: " + healthGoal.getHealth(), healthGoal);
                goalsContainerVBox.getChildren().add(hBox);
            }

            if (hBox != null) {
                hBox.setSpacing(40);
            }
        }
    }

    /**
     * Creates an HBox container for displaying a goal in the goals container.
     * The HBox contains a label with the provided text and a "Remove" button.
     * The button is associated with the given goal and performs the corresponding removal action when clicked.
     * The HBox also includes a spacer and a button container for proper alignment.
     * @param labelText The text to be displayed in the label, representing the goal.
     * @param goal The goal object associated with the HBox.
     * @return The HBox container displaying the goal with a remove button.
     */
    private HBox createGoalHBox(String labelText, Goal goal) {
        HBox hBox = new HBox();
        Label label = new Label(labelText);
        hBox.getChildren().add(label);

        Button removeButton = new Button("Remove");
        removeButton.getStyleClass().addAll("remove-button", "black-and-white");

        if (goal instanceof InventoryGoal inventoryGoal) {
            int colonIndex = labelText.indexOf(":");
            String inventory = labelText.substring(colonIndex + 1).trim();
            removeButton.setOnAction(e -> {
                goals.remove(inventoryGoal);
                inventoryGoalList.remove(inventory);
                addGoalsToContainer();
            });
        } else if (goal instanceof GoldGoal goldGoal) {
            removeButton.setOnAction(e -> {
                goals.remove(goldGoal);
                addGoalsToContainer();
            });
        } else if (goal instanceof ScoreGoal scoreGoal) {
            removeButton.setOnAction(e -> {
                goals.remove(scoreGoal);
                addGoalsToContainer();
            });
        } else if (goal instanceof HealthGoal healthGoal) {
            removeButton.setOnAction(e -> {
                goals.remove(healthGoal);
                addGoalsToContainer();
            });
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox buttonContainer = new HBox(removeButton);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);

        hBox.getChildren().addAll(spacer, buttonContainer);

        return hBox;
    }

    /**
     * Begins the game based on the selected goals.
     * If no goals are selected, a default game is created without any goals.
     * After starting the game, the BeginGame window is displayed.
     * @param actionEvent The action event that triggers the method.
     */
    private void beginGame(ActionEvent actionEvent) {
        if (goals == null || goals.size() == 0 ) {
            newGame = GameBuilder.newInstance()
                .setStory(currentStory)
                .setPlayer(PlayerManager.getInstance().getPlayer())
                .build();

            GameManager.getInstance().setGame(new Game(newGame));
        }
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        BeginGame beginGame = new BeginGame();
        try {
            beginGame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action event when the "Go Back" button is clicked.
     * @param actionEvent the action event triggered by clicking the "Go Back" button
     */
    private void goBack(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        CreatePlayer createPlayer = new CreatePlayer();
        try {
            createPlayer.start(stage);
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
}

