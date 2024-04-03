package com.carhiring.carhiring.presentation.controllers;

import com.carhiring.carhiring.business.ClientService;
import com.carhiring.carhiring.data.entities.Client;
import com.carhiring.carhiring.data.entities.Operator;
import com.carhiring.carhiring.common.Context;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientRegistrationController implements Initializable {

    @FXML
    private TableView<Client> clientTable;
    @FXML
    private TableColumn<Client, String> idColumn;
    @FXML
    private TableColumn<Client, String> nameColumn;
    @FXML
    private TableColumn<Client, String> phoneColumn;
    @FXML
    private TableColumn<Client, String> emailColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private Button registerClientButton;

    private ClientService clientService;
    private ObservableList<Client> clientList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientService = ClientService.getInstance();
        clientList = FXCollections.observableArrayList(clientService.getAllClients());

        idColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getId())));
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        phoneColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPhone()));
        emailColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEmail()));

        clientTable.setItems(clientList);
    }

    @FXML
    private void registerClient() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            // Show an error message or alert to indicate missing information
            System.out.println("Please fill in all fields.");
            return;
        }

        Client client = new Client();
        System.out.println("New client: " + client);
        client.setName(name);
        client.setPhone(phone);
        client.setEmail(email);
        Operator loggedInOperator = Context.getInstance().getOperator();
        System.out.println(loggedInOperator);
        client.setOperator(loggedInOperator);

        clientService.addClient(client);
        clientList.add(client);

        System.out.println("Client registered successfully.");
        clearFields();
    }

    private void clearFields() {
        nameField.clear();
        phoneField.clear();
        emailField.clear();
    }
}
