package edu.ntnu.paths.JavaFX;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The HelpPage class displays a pop-up window with help information.
 */
public class HelpPage {

    /**
     * Displays the help pop-up window.
     * @param parentStage the parent stage to which the pop-up window is attached
     */
    public void displayPopUp(Stage parentStage) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(parentStage);
        popupStage.setTitle("Help Page");

        VBox content = new VBox();
        content.setAlignment(Pos.TOP_CENTER);
        content.setPrefWidth(600);

        Label headerLabel = new Label("Paths Help");
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        VBox innerContent = new VBox();
        innerContent.setSpacing(10);

        Label importHeaderLabel = new Label("Import File");
        importHeaderLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label importTextLabel = new Label("""
        The filetype this game uses is the \".paths\" filetype.
        There is a special requirement for this filetype. There are three stories located in the StoryFiles folder.
        These follow the requirements for the \".paths\" filetype.
        After importing the file, you get information about the selected file.
        The broken links symbolize links with references to passages that do not exist.""");
        importTextLabel.setStyle("-fx-line-spacing: 3px;");


        Label createUserHeaderLabel = new Label("Create User");
        createUserHeaderLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label createUserTextLabel = new Label("To play the game, you have to create a user. " +
                "The user needs to have the necessary information, name, health and gold.");
        createUserTextLabel.setStyle("-fx-line-spacing: 3px;");

        Label createGoalsHeaderLabel = new Label("Create Goals");
        createGoalsHeaderLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label createGoalsTextLabel = new Label("""
                Before playing the game, you can create goals for the gameplay.\s
                There are four types of goals: Inventory, Score, Health, and Gold. In the game, these goals will have an effect.\s
                Before choosing your goals, you will receive hints about what to expect from the game.""");
        createGoalsTextLabel.setStyle("-fx-line-spacing: 3px;");

        Label gameHeaderLabel = new Label("The Game");
        gameHeaderLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label gameTextLabel = new Label("""
                The game is a selective game that gives the player power to choose their next turn.\s
                You select which path to choose by clicking the button that leeds to the choosen passage. \s
                But be aware that there can be significant consequences from your choices.\s
                It is smart to look at the actions that will occur if choosing the specific passage.\s
                The actions can help fullfill the goal, or have negative inpact on the health - resulting in early termination of the game.\s
                You can always start over by pressing the 'Restart' button, with is in the top right corner of the game div.""");
        gameTextLabel.setStyle("-fx-line-spacing: 3px;");

        innerContent.getChildren().addAll(
                importHeaderLabel,
                importTextLabel,
                createUserHeaderLabel,
                createUserTextLabel,
                createGoalsHeaderLabel,
                createGoalsTextLabel,
                gameHeaderLabel,
                gameTextLabel
        );

        content.getChildren().addAll(
                headerLabel,
                innerContent
        );

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        scrollPane.prefViewportWidthProperty().bind(scrollPane.widthProperty());
        scrollPane.prefViewportHeightProperty().bind(scrollPane.heightProperty());

        Scene scene = new Scene(scrollPane, 650, 500);
        popupStage.setScene(scene);
        popupStage.show();
    }
}
