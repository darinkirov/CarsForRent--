package com.carhiring.carhiring.presentation.controllers;

import com.carhiring.carhiring.data.entities.User;
import com.carhiring.carhiring.data.repositories.UserRepository;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserManagementController implements Initializable {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> roleColumn;

    private ObservableList<User> users;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getRole().getName()));

        users = FXCollections.observableArrayList(UserRepository.getInstance().getAll());
        userTable.setItems(users);
    }

    @FXML
    private void handleAddUser(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/carhiring/carhiring/AddUserView.fxml"));
            Parent addUserRoot = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(addUserRoot));
            stage.setTitle("Add User");

            stage.showAndWait();

            refreshUserTable();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshUserTable() {
        users.clear();
        users.addAll(UserRepository.getInstance().getAll());
    }


    @FXML
    private void handleEditUser(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carhiring/carhiring/EditUserView.fxml"));
                Parent editUserRoot = loader.load();

                Stage editUserStage = new Stage();
                editUserStage.initModality(Modality.APPLICATION_MODAL);
                editUserStage.initOwner(userTable.getScene().getWindow());
                editUserStage.setTitle("Edit User");

                Scene scene = new Scene(editUserRoot);
                editUserStage.setScene(scene);

                EditUserController editUserController = loader.getController();
                editUserController.setUserToEdit(selectedUser);

                editUserStage.showAndWait();

                refreshUserTable();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void handleDeleteUser(ActionEvent event) {
        // Todo
    }

}
