package dev.gbl.login_with_database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;

public class RegisterController {

    @FXML
    TextField regUsernameTextField;
    @FXML
    PasswordField regPasswordField;
    @FXML
    TextField regEmailAddress;
    @FXML
    Label registerMsgLabel;

    public void onBackButtonClick(ActionEvent e) {
        try{
            SceneSwitcher sceneSwitcher = new SceneSwitcher();
            sceneSwitcher.switchScene("login.fxml",e);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void onRegisterButtonClick(ActionEvent e) {
        boolean isExistingUser = checkUserExistence(regUsernameTextField.getText(),
                regEmailAddress.getText());
        if (!isExistingUser) {
            insertUserToDb(regUsernameTextField.getText(), regPasswordField.getText(),
                    regEmailAddress.getText());
            boolean registerValidation = checkUserExistence(regUsernameTextField.getText(), regEmailAddress.getText());
            if (registerValidation) {
                registerMsgLabel.setText("Account created successfully!");
            }
        }
        else
            registerMsgLabel.setText("User already exists!");
    }

    public boolean checkUserExistence(String username, String emailAddress) {
        DBHelper connectionLink = new DBHelper();
        String verifyQuery = "SELECT COUNT(1) FROM useraccounts WHERE Username = '"
                + username + "' AND EmailAddress = '" + emailAddress + "'";

        try (Connection connectToDb = connectionLink.getConnection()) {
            Statement statement = connectToDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyQuery);
            if (queryResult.getInt(1) == 1) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertUserToDb(String username, String password, String emailAddress) {
        DBHelper connectionLink = new DBHelper();


        String insertQuery = "INSERT INTO useraccounts ('Username', 'Password', 'EmailAddress')" +
                "VALUES(?, ?, ?)";

        try (Connection connectToDb = connectionLink.getConnection();
             PreparedStatement preparedStatement = connectToDb.prepareStatement(insertQuery)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, emailAddress);
            preparedStatement.executeUpdate();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}