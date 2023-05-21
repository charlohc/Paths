module edu.ntnu.idatt2001.paths_v2 {
    requires javafx.controls;
    requires java.prefs;

    exports edu.ntnu.paths.JavaFX;
    exports edu.ntnu.paths.Goals;
    exports edu.ntnu.paths.GameDetails;
    opens edu.ntnu.paths.JavaFX to javafx.fxml;
}