package com.carhiring.carhiring.data.repositories;

import com.carhiring.carhiring.data.entities.Car;
import com.carhiring.carhiring.data.entities.CarCategory;
import com.carhiring.carhiring.data.entities.CarClass;
import com.carhiring.carhiring.data.postgresql.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CarRepository implements DAORepository<Car> {

    public static CarRepository getInstance() { return CarRepository.CarRepositoryHolder.INSTANCE;}

    public List<CarClass> getAllCarClasses() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<CarClass> carClasses = new LinkedList<>();

        try {
            String jpql = "FROM CarClass";
            carClasses.addAll(session.createQuery(jpql, CarClass.class).getResultList());
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return carClasses;
    }

    public List<CarCategory> getAllCarCategories() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<CarCategory> carCategories = new LinkedList<>();

        try {
            String jpql = "FROM CarCategory";
            carCategories.addAll(session.createQuery(jpql, CarCategory.class).getResultList());
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return carCategories;
    }


    private static class CarRepositoryHolder {
        public static final CarRepository INSTANCE = new CarRepository();
    }

    @Override
    public void save(Car obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(obj);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();

    } finally {
            session.close();
        }
    }

    @Override
    public void update(Car obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(obj);
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Car obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(obj);
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Car> getById(int id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Car> car = Optional.empty();

        try {
            car = Optional.ofNullable(session.get(Car.class, id));
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return car;
    }

    public CarClass getCarClassById(int id) {
        Session session = Connection.openSession();
        CarClass carClass = session.find(CarClass.class, id);
        session.close();
        return carClass;
    }

    public CarCategory getCarCategoryById(int id) {
        Session session = Connection.openSession();
        // Fetch the CarCategory from DB
        CarCategory carCategory = session.find(CarCategory.class, id);
        session.close();
        return carCategory;
    }

    @Override
    public List<Car> getAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Car> cars = new LinkedList<>();

        try {
            String jpql = "FROM Car";
            cars.addAll(session.createQuery(jpql, Car.class).getResultList());
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return cars;
    }
}
