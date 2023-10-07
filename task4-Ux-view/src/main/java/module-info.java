module com.example.testux {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.uxFiles to javafx.fxml;
    exports com.example.uxFiles;
}

//