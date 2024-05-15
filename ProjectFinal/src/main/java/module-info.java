module com.example.projectfinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.naming;
    requires java.desktop;

    opens com.example.projectfinal to javafx.fxml;
    exports com.example.projectfinal;
}