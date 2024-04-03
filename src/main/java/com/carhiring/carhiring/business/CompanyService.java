package com.carhiring.carhiring.business;

import com.carhiring.carhiring.data.entities.Company;
import com.carhiring.carhiring.data.repositories.CompanyRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CompanyService {

    private final CompanyRepository repository = CompanyRepository.getInstance();

    private static class CompanyServiceHolder {
        public static final CompanyService INSTANCE = new CompanyService();
    }

    public static CompanyService getInstance() {
        return CompanyServiceHolder.INSTANCE;
    }

    public Company getCompanyByName(String name) {
        return repository.getByName(name);
    }

    public boolean isCompanyNameExists(String name) {
        return getCompanyByName(name) != null;
    }

    public void addCompany(Company company) {
        try {
            repository.save(company);
        } catch (Exception ex) {
            System.out.println("Error creating company: " + ex);
        }
    }

    public ObservableList<Company> getAllCompanies() {
        List<Company> companies = repository.getAll();
        return FXCollections.observableArrayList(companies);
    }
}
