package com.carhiring.carhiring.business;

import com.carhiring.carhiring.data.entities.CarRentalCompany;
import com.carhiring.carhiring.data.entities.Company;
import com.carhiring.carhiring.data.entities.Operator;
import com.carhiring.carhiring.data.entities.User;
import com.carhiring.carhiring.data.repositories.OperatorRepository;
import com.carhiring.carhiring.data.repositories.UserRepository;
import com.carhiring.carhiring.presentation.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    private final UserRepository repository = UserRepository.getInstance();

    private static class UserServiceHolder {
        public static final UserService INSTANCE = new UserService();
    }

    public static UserService getInstance() {
        return UserServiceHolder.INSTANCE;
    }


    public User getUserByUsername(String username) {
        return repository.getByUsername(username);
    }

    public boolean isUsernameExists(String username) {
        return getUserByUsername(username) != null;
    }

    public void updateUser(User user) {
        repository.update(user);
    }

    public boolean userAuth(String username, String password) {
        System.out.println("Attempting to authenticate user: " + username);
        User userTmp = repository.getByUsernameAndPassword(username, password);

        if (userTmp != null) {
            System.out.println("User authentication successful for: " + username);
            return true;
        } else {
            System.out.println("User authentication failed for: " + username);
            return false;
        }
    }

    public void addUser(User user) {
        repository.save(user);

        // If the User is an Operator, create an Operator entity
        if (user.getRole().getName().equalsIgnoreCase("operator")) {
            Operator operator = new Operator();
            operator.setUser(user);

            // Save the Operator
            OperatorRepository.getInstance().save(operator);
        }
    }


    public User authenticate(String username, String password) {
        return repository.getByUsernameAndPassword(username, password);
    }

    public boolean usernameValidate(String username) {
        String regex = "^[A-Za-z]\\w{3,255}$";
        Pattern p = Pattern.compile(regex);
        if (username == null) {
            return false;
        }
        Matcher m = p.matcher(username);
        return m.matches();
    }

    public boolean passwordValidate(String password) {
        String regex = "^[A-Za-z]\\w{4,29}$";
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }
}



