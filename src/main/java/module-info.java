module edu.ntnu.idatt2001.paths_v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;

    exports edu.ntnu.paths.Controller;
    opens edu.ntnu.paths.Controller to javafx.fxml;
}