package com.carhiring.carhiring.presentation.controllers;

import com.carhiring.carhiring.business.OperatorService;
import com.carhiring.carhiring.common.Constants;
import com.carhiring.carhiring.common.Context;
import com.carhiring.carhiring.business.UserService;
import com.carhiring.carhiring.data.entities.Operator;
import com.carhiring.carhiring.data.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class HelloController {
    private static final Logger log = Logger.getLogger(HelloController.class);
    public final UserService userService = UserService.getInstance();

    @FXML
    AnchorPane mainAnchorPanel;

    @FXML
    Button loginButton;

    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordTextField;

    @FXML
    Label messageLabel;

    public void loginProcedure() throws IOException {
        messageLabel.setText("");

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (username.length() == 0 || password.length() == 0) {
            messageLabel.setStyle("-fx-text-fill: " + Constants.Values.ERROR_COLOR + ";");
            messageLabel.setText("The fields must be filled.");
            return;
        } else if (!userService.usernameValidate(username) || !userService.passwordValidate(password)) {
            messageLabel.setStyle("-fx-text-fill: " + Constants.Values.ERROR_COLOR + ";");
            messageLabel.setText("There is no user with the entered data.");
            return;
        }

        boolean loginAuth = userService.userAuth(username, password);

        if (!loginAuth) {
            messageLabel.setStyle("-fx-text-fill: " + Constants.Values.ERROR_COLOR + ";");
            messageLabel.setText("Incorrect username or password.");
        } else {
            User user = userService.authenticate(username, password);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carhiring/carhiring/DashboardView.fxml"));
            Parent dashboardRoot = loader.load();
            DashboardController dashboardController = loader.getController();
            dashboardController.loggedInUser = user;
            dashboardController.configureButtons();

            Operator operator = OperatorService.getInstance().getOperatorByUser(user);

            Context.getInstance().setOperator(operator);

            Stage currentStage = (Stage) usernameTextField.getScene().getWindow();

            Scene dashboardScene = new Scene(dashboardRoot);
            currentStage.setScene(dashboardScene);
            currentStage.show();
        }
    }

}
