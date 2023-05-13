package edu.ntnu.paths.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        HomePage homePage = new HomePage();
        Scene scene = homePage.getScene();

        stage.setTitle("Paths");
        stage.setMinWidth(550);
        stage.setMinHeight(500);
        stage.setMaxWidth(1200);
        stage.setMaxHeight(700);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
