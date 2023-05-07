module edu.ntnu.paths {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.ntnu.paths to javafx.fxml;
    exports edu.ntnu.paths;
}