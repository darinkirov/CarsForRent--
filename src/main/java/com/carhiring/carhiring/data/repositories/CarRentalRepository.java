package com.carhiring.carhiring.data.repositories;

import com.carhiring.carhiring.data.entities.CarRental;
import com.carhiring.carhiring.data.postgresql.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CarRentalRepository implements DAORepository<CarRental> {

    public static CarRentalRepository getInstance() { return CarRentalRepository.CarRentalRepositoryHolder.INSTANCE;}

    private static class CarRentalRepositoryHolder {
        public static final CarRentalRepository INSTANCE = new CarRentalRepository();
    }

    @Override
    public void save(CarRental obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(obj);
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void update(CarRental obj) {
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
    public void delete(CarRental obj) {
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
    public Optional<CarRental> getById(int id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<CarRental> carRental = Optional.empty();

        try {
            carRental = Optional.ofNullable(session.get(CarRental.class, id));
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return carRental;
    }

    @Override
    public List<CarRental> getAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<CarRental> carRentals = new LinkedList<>();

        try {
            String jpql = "FROM CarRental";
            carRentals.addAll(session.createQuery(jpql, CarRental.class).getResultList());
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return carRentals;
    }
}
