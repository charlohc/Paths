module edu.ntnu.idatt2001.paths_v2 {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens edu.ntnu.paths to javafx.fxml;
    exports edu.ntnu.paths;
}