package com.carhiring.carhiring.presentation.controllers;

import com.carhiring.carhiring.business.UserService;
import com.carhiring.carhiring.data.entities.Role;
import com.carhiring.carhiring.data.entities.User;
import com.carhiring.carhiring.data.repositories.RoleRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditUserController {

    private UserService userService = UserService.getInstance();

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private ComboBox<Role> roleComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private User userToEdit;

    public void setUserToEdit(User user) {
        this.userToEdit = user;
        populateFields();    }

    private void populateFields() {
        if (userToEdit != null) {
            usernameTextField.setText(userToEdit.getUsername());
            passwordTextField.setText(userToEdit.getPassword());
            roleComboBox.setValue(userToEdit.getRole());
        }
    }

    public void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList(RoleRepository.getInstance().getAll()));
    }

    @FXML
    private void handleSave() {
        if (validateInput()) {
            userToEdit.setUsername(usernameTextField.getText());
            userToEdit.setPassword(passwordTextField.getText());
            userToEdit.setRole(roleComboBox.getValue());

            userService.updateUser(userToEdit);

            closeDialog();
        }
    }

    @FXML
    private void handleCancel() {
        closeDialog();
    }

    private boolean validateInput() {
        // Add input validation logic here
        return true;
    }

    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
