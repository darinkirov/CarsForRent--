package com.carhiring.carhiring.presentation.controllers;

import com.carhiring.carhiring.business.UserService;
import com.carhiring.carhiring.data.entities.Company;
import com.carhiring.carhiring.data.entities.Role;
import com.carhiring.carhiring.data.entities.User;
import com.carhiring.carhiring.data.repositories.CompanyRepository;
import com.carhiring.carhiring.data.repositories.RoleRepository;
import com.carhiring.carhiring.data.repositories.UserRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<Role> roleComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleComboBox.setItems(FXCollections.observableArrayList(RoleRepository.getInstance().getAll()));
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        Role role = roleComboBox.getValue();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);

        UserService.getInstance().addUser(newUser);
        closeWindow();
    }


    @FXML
    private void handleCancel(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
