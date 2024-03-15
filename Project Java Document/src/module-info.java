module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires org.controlsfx.controls;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    opens gui to javafx.fxml;
    exports gui;
    exports main;
}
