package dev.gbl.login_with_database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoggedInController {

    @FXML
    Label welcomeLabel;
    @FXML
    Button loggedInExitButton;

    public void displayEmail(String emailAddress) {
        welcomeLabel.setText("Welcome,\n" + emailAddress);
    }

    public void onExitButtonClick(ActionEvent e) {
        Stage stage = (Stage) loggedInExitButton.getScene().getWindow();
        stage.close();
    }

    public void onLogoutButtonClick(ActionEvent e) {
        try {
            SceneSwitcher sceneSwitcher = new SceneSwitcher();
            sceneSwitcher.switchScene("login.fxml", e);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
