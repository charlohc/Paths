package edu.ntnu.paths.Controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpPage {

    public void displayPopUp(Stage parentStage) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(parentStage);
        popupStage.setTitle("Help Page");

        VBox content = new VBox();
        content.setAlignment(Pos.TOP_CENTER);
        content.setPrefWidth(600);

        Label headerLabel = new Label("Paths Help");
        headerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        VBox innerContent = new VBox();
        innerContent.setSpacing(10);

        Label importHeaderLabel = new Label("Import File");
        importHeaderLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label importTextLabel = new Label("The filetype this game uses is the \".paths\" filetype. " +
                "There is a special requirement for this filetype, etc.");

        Label createUserHeaderLabel = new Label("Create User");
        createUserHeaderLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label createUserTextLabel = new Label("To play the game, you have to create a user. " +
                "The user needs to have information such as name, etc.");

        Label createGoalsHeaderLabel = new Label("Create Goals");
        createGoalsHeaderLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label createGoalsTextLabel = new Label("""
                Before playing the game, you can create some goals for yourself.\s
                There are four types of goals: Inventory, Score, Health, and Gold. In the game, these goals will have an effect.\s
                Before choosing your goals, you will receive hints about what to expect from the game.""");

        Label gameHeaderLabel = new Label("The Game");
        gameHeaderLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label gameTextLabel = new Label("""
                The game is a selective game that gives the player power to choose their next turn.\s
                But be aware that there can be significant consequences from your choices.\s
                You can always start over by pressing the 'Restart' button.""");

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

        Scene scene = new Scene(scrollPane, 600, 400);
        popupStage.setScene(scene);
        popupStage.show();
    }
}
