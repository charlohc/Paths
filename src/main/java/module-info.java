module edu.ntnu.idatt2001.paths_v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports edu.ntnu.paths.Controller;
    exports edu.ntnu.paths.Goals;
    opens edu.ntnu.paths.Controller to javafx.fxml;
}