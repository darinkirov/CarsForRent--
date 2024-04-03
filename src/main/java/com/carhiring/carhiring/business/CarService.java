package com.carhiring.carhiring.business;

import com.carhiring.carhiring.data.entities.Car;
import com.carhiring.carhiring.data.entities.CarCategory;
import com.carhiring.carhiring.data.entities.CarClass;
import com.carhiring.carhiring.data.repositories.CarCategoryRepository;
import com.carhiring.carhiring.data.repositories.CarRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CarService {

    private final CarRepository carRepository = CarRepository.getInstance();
    private final CarCategoryRepository carCategoryRepository = CarCategoryRepository.getInstance();

    public List<CarClass> getAllCarClasses() {
        return carRepository.getAllCarClasses();
    }

    public List<CarCategory> getAllCarCategories() {
        return carRepository.getAllCarCategories();
    }

    public CarCategory getCarCategoryByName(String name) {
        CarCategory category = carCategoryRepository.getByName(name);
        if (category == null) {
            category = new CarCategory();
            category.setName(name);
            carCategoryRepository.save(category);
        }
        return category;
    }

    private static class CarServiceHolder {
        public static final CarService INSTANCE = new CarService();
    }

    public static CarService getInstance() {
        return CarServiceHolder.INSTANCE;
    }

    public Car getCarByMakeAndModel(String make, String model) {
        List<Car> allCars = carRepository.getAll();
        for (Car car : allCars) {
            if (car.getMake().equalsIgnoreCase(make) && car.getModel().equalsIgnoreCase(model)) {
                return car;
            }
        }
        return null;
    }

    public CarClass getCarClassById(int id) {
        return carRepository.getCarClassById(id);
    }

    public CarCategory getCarCategoryById(int id) {
        return carRepository.getCarCategoryById(id);
    }

    public void addCar(Car car) {
        try {
            carRepository.save(car);
        } catch (Exception ex) {
            System.out.println("Error registering car: " + ex);
        }
    }

    public void updateCar(Car car) {
        carRepository.update(car);
    }

    public ObservableList<Car> getAllCars() {
        List<Car> cars = carRepository.getAll();
        return FXCollections.observableArrayList(cars);
    }
}
