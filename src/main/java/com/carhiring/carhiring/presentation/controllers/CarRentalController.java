package com.carhiring.carhiring.presentation.controllers;

import com.carhiring.carhiring.business.CarService;
import com.carhiring.carhiring.business.ClientService;
import com.carhiring.carhiring.data.entities.Car;
import com.carhiring.carhiring.data.entities.Client;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.Initializable;


import java.net.URL;
import java.util.ResourceBundle;

public class CarRentalController implements Initializable {
    @FXML
    private TableView<Car> carTable;
    @FXML
    private TableColumn<Car, String> makeColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, String> clientColumn;
    @FXML
    private TableColumn<Car, String> currentConditionColumn;
    @FXML
    private TextField clientTextField;
    @FXML
    private TextField currentConditionTextField;
    @FXML
    private Button rentCarButton;
    @FXML
    private Button cancelButton;

    private CarService carService;
    private ClientService clientService;
    private ObservableList<Car> carList;
    private Car selectedCar;

    @FXML
    private Button returnCarButton;

    public void handleReturnCar(ActionEvent actionEvent) {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();

        if(selectedCar != null && selectedCar.getClient() != null) {
            selectedCar.setClient(null);
            selectedCar.setCurrentCondition("Returned");  // or any other default condition

            carService.updateCar(selectedCar);
            carList.set(carList.indexOf(selectedCar), selectedCar);

            System.out.println("Car returned successfully.");
        } else {
            System.out.println("No rented car is selected.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carService = CarService.getInstance();
        clientService = ClientService.getInstance();
        carList = FXCollections.observableArrayList(carService.getAllCars());

        makeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMake()));
        modelColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getModel()));
        clientColumn.setCellValueFactory(cellData -> {
            Client client = cellData.getValue().getClient();
            if (client != null) {
                return new ReadOnlyStringWrapper(client.getName());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        currentConditionColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCurrentCondition()));

        carTable.setItems(carList);
    }

    public void handleCarSelection(MouseEvent event) {
        selectedCar = carTable.getSelectionModel().getSelectedItem();
    }

    public void handleRentCar(ActionEvent actionEvent) {
        String clientName = clientTextField.getText();
        String currentCondition = currentConditionTextField.getText();

        if (selectedCar == null || clientName.isEmpty() || currentCondition.isEmpty()) {
            // Show an error message or alert to indicate missing information
            System.out.println("Please fill in all fields and select a car.");
            return;
        }

        Client client = clientService.getClientByName(clientName);
        if(client == null) {
            System.out.println("Client not found.");
            return;
        }

        selectedCar.setClient(client);
        selectedCar.setCurrentCondition(currentCondition);

        carService.updateCar(selectedCar);
        carList.set(carList.indexOf(selectedCar), selectedCar);

        System.out.println("Car rented successfully.");
        clearFields();
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void clearFields() {
        clientTextField.clear();
        currentConditionTextField.clear();
    }
}
