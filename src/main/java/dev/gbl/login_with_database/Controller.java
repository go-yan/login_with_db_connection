package dev.gbl.login_with_database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Controller {
    @FXML
    private Button exitButton;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public void loginButtonOnAction(ActionEvent e) {
        if (!usernameField.getText().isBlank() && !passwordField.getText().isBlank()) {

            validateLogin();
        }
        else {
            messageLabel.setText("Please enter username and/or password.");
        }
    }

    public void exitButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin() {
        String email;

        DBUtil connectionLink = new DBUtil();
        Connection connectToDb = connectionLink.getConnection();

        String verifyLogin = "SELECT COUNT(1), EmailAddress FROM useraccounts WHERE Username = '" + usernameField.getText()
                + "' AND Password = '" + passwordField.getText() + "'";

        try {
            Statement statement = connectToDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                email = queryResult.getString("EmailAddress");
                if (queryResult.getInt(1) == 1) {
                    messageLabel.setText("Welcome " + email);
                }
                else {
                    messageLabel.setText("Invalid credentials. Try again.");
                }
            }
            connectToDb.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}