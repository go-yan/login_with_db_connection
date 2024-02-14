module dev.gbl.login_with_database {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens dev.gbl.login_with_database to javafx.fxml;
    exports dev.gbl.login_with_database;
}