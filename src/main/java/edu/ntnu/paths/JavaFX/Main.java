package edu.ntnu.paths.JavaFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Main extends Application {
    private final Preferences prefs = Preferences.userNodeForPackage(ImportedStory.class);
    @Override
    public void start(Stage stage){
        HomePage homePage = new HomePage();
        Scene scene = homePage.getScene();

        stage.setTitle("Paths");
        stage.setMinWidth(550);
        stage.setMinHeight(500);
        stage.setMaxWidth(1200);
        stage.setMaxHeight(700);
        stage.setScene(scene);
        stage.show();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {

                prefs.removeNode();
            } catch (BackingStoreException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public static void main(String[] args) {
        launch();
    }
}
