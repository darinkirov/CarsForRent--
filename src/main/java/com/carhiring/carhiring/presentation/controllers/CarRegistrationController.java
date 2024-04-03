package com.carhiring.carhiring.presentation.controllers;

import com.carhiring.carhiring.business.CarService;
import com.carhiring.carhiring.data.entities.Car;
import com.carhiring.carhiring.data.entities.CarCategory;
import com.carhiring.carhiring.data.entities.CarClass;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CarRegistrationController implements Initializable {
    @FXML
    private TableView<Car> carTable;
    @FXML
    private TableColumn<Car, String> makeColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, Boolean> isSmokingAllowedColumn;
    @FXML
    private TableColumn<Car, CarClass> carClassColumn;
    @FXML
    private TableColumn<Car, CarCategory> carCategoryColumn;
    @FXML
    private TextField carMakeTextField;
    @FXML
    private TextField carModelTextField;
    @FXML
    private CheckBox isSmokingAllowedCheckBox;
    @FXML
    private ComboBox<CarClass> carClassComboBox;
    @FXML
    private ComboBox<CarCategory> carCategoryComboBox;
    @FXML
    private Button registerCarButton;
    @FXML
    private Button cancelButton;

    private CarService carService;
    private ObservableList<Car> carList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carService = CarService.getInstance();
        carList = FXCollections.observableArrayList(carService.getAllCars());

        makeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMake()));
        modelColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getModel()));
        isSmokingAllowedColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isSmokingAllowed()));
        carClassColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCarClass()));
        carCategoryColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCarCategory()));

        // Populate the ComboBoxes
        carClassComboBox.setItems(FXCollections.observableArrayList(carService.getAllCarClasses()));
        carCategoryComboBox.setItems(FXCollections.observableArrayList(carService.getAllCarCategories()));

        carTable.setItems(carList);
    }


    public void handleRegisterCar(ActionEvent actionEvent) {
        String carMake = carMakeTextField.getText();
        String carModel = carModelTextField.getText();
        boolean isSmokingAllowed = isSmokingAllowedCheckBox.isSelected();
        CarClass carClass = carService.getCarClassById(carClassComboBox.getSelectionModel().getSelectedItem().getId());
        CarCategory carCategory = carService.getCarCategoryById(carCategoryComboBox.getSelectionModel().getSelectedItem().getId());

        if (carMake.isEmpty() || carModel.isEmpty() || carClass == null || carCategory == null) {
            // Show an error message or alert to indicate missing information
            System.out.println("Please fill in all fields.");
            return;
        }

        Car car = new Car();
        car.setMake(carMake);
        car.setModel(carModel);
        car.setSmokingAllowed(isSmokingAllowed);
        car.setCarClass(carClass);
        car.setCarCategory(carService.getCarCategoryByName(carCategory.getName()));

        System.out.println("Car Category: " + carCategory.getId());

        carService.addCar(car);
        carList.add(car);

        System.out.println("Car registered successfully.");
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
        carMakeTextField.clear();
        carModelTextField.clear();
        isSmokingAllowedCheckBox.setSelected(false);
        carClassComboBox.getSelectionModel().clearSelection();
        carCategoryComboBox.getSelectionModel().clearSelection();

    }
}