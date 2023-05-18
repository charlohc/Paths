module edu.ntnu.idatt2001.paths_v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;

    exports edu.ntnu.paths.Controller;
    exports edu.ntnu.paths.Goals;
    exports edu.ntnu.paths.GameDetails;
    opens edu.ntnu.paths.Controller to javafx.fxml;
}