package dev.gbl.login_with_database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    String email;

    public void loginButtonOnAction(ActionEvent e) {
        if (!usernameField.getText().isBlank() && !passwordField.getText().isBlank()) {
            boolean isExistingUser = validateLogin();
            if (isExistingUser) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("logged_in.fxml"));
                    Parent root = loader.load();

                    LoggedInController loggedInController = loader.getController();
                    loggedInController.displayEmail(email);

                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }
        else {
            messageLabel.setText("Please enter username and/or password.");
        }
    }

    public void registerButtonOnAction(ActionEvent e) {
        SceneSwitcher switcher = new SceneSwitcher();
        switcher.switchScene("register.fxml", e);
    }

    public void exitButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public boolean validateLogin() {
        DBHelper connectionLink = new DBHelper();
        String verifyQuery = "SELECT COUNT(1), EmailAddress FROM useraccounts WHERE Username = '" + usernameField.getText()
                + "' AND Password = '" + passwordField.getText() + "'";

        try (Connection connectToDb = connectionLink.getConnection()) {
            Statement statement = connectToDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyQuery);

             while (queryResult.next()) {
                    email = queryResult.getString("EmailAddress");
                    if (queryResult.getInt(1) == 1) {
                        return true;
                    } else {
                        messageLabel.setText("Invalid credentials. Try again.");
                    }
                }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}