package com.carhiring.carhiring.presentation.controllers;

import com.carhiring.carhiring.business.CompanyService;
import com.carhiring.carhiring.data.entities.Company;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateCompanyController implements Initializable {
    @FXML
    private TableView<Company> companyTable;
    @FXML
    private TableColumn<Company, String> nameColumn;
    @FXML
    private TableColumn<Company, String> addressColumn;
    @FXML
    private TableColumn<Company, String> phoneColumn;
    @FXML
    private TextField companyNameTextField;
    @FXML
    private TextField companyAddressTextField;
    @FXML
    private TextField companyPhoneTextField;
    @FXML
    private Button createCompanyButton;
    @FXML
    private Button cancelButton;

    private CompanyService companyService;
    private ObservableList<Company> companyList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        companyService = CompanyService.getInstance();
        companyList = FXCollections.observableArrayList(companyService.getAllCompanies());

        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        addressColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress()));
        phoneColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPhone()));

        companyTable.setItems(companyList);
    }

    public void handleCreateCompany(ActionEvent actionEvent) {
        String companyName = companyNameTextField.getText();
        String companyAddress = companyAddressTextField.getText();
        String companyPhone = companyPhoneTextField.getText();

        if (companyName.isEmpty() || companyAddress.isEmpty() || companyPhone.isEmpty()) {
            // Show an error message or alert to indicate missing information
            System.out.println("Please fill in all fields.");
            return;
        }

        Company company = new Company(companyName, companyAddress, companyPhone);
        companyService.addCompany(company);
        companyList.add(company);

        System.out.println("Company created successfully.");
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
        companyNameTextField.clear();
        companyAddressTextField.clear();
        companyPhoneTextField.clear();
    }
}
