package com.carhiring.carhiring.presentation.controllers;

import com.carhiring.carhiring.data.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    static User loggedInUser;

    @FXML
    private AnchorPane contentPane;
    @FXML
    private Button userManagementButton;
    @FXML
    private Button createCompanyButton;
    @FXML
    private Button carRegistrationButton;
    @FXML
    private Button carRentalOperationsButton;

    @FXML
    private Button clientRegistrationButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureButtons();
    }

    public void handleUserManagement() {
        loadView("/com/carhiring/carhiring/UserManagementView.fxml");
    }

    public void handleCreateCompany() { loadView("/com/carhiring/carhiring/CreateCompanyView.fxml");}

    public void handleCarRegistration() { loadView("/com/carhiring/carhiring/CarRegistrationView.fxml");}

    public void handleClientRegistration() { loadView("/com/carhiring/carhiring/ClientRegistrationView.fxml");}

    public void handleCarRentalOperations() {
        loadView("/com/carhiring/carhiring/CarRentalOperationsView.fxml");
    }

    private void loadView(String viewFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewFileName));
            Node view = loader.load();
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    public void configureButtons() {
        if (loggedInUser != null) {
            if (loggedInUser.hasRole("Administrator")) {
                userManagementButton.setVisible(true);
                createCompanyButton.setVisible(true);
                carRegistrationButton.setVisible(false);
                carRentalOperationsButton.setVisible(false);
                clientRegistrationButton.setVisible(false);

            } else if (loggedInUser.hasRole("Operator")) {
                userManagementButton.setVisible(false);
                createCompanyButton.setVisible(false);
            }
        }
    }
}
