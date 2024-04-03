package com.carhiring.carhiring.data.repositories;

import com.carhiring.carhiring.data.entities.CarClass;
import com.carhiring.carhiring.data.postgresql.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CarClassRepository implements DAORepository<CarClass> {

    public static CarClassRepository getInstance() { return CarClassRepository.CarClassRepositoryHolder.INSTANCE;}

    private static class CarClassRepositoryHolder {
        public static final CarClassRepository INSTANCE = new CarClassRepository();
    }

    @Override
    public void save(CarClass obj) {
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
    public void update(CarClass obj) {
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
    public void delete(CarClass obj) {
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
    public Optional<CarClass> getById(int id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<CarClass> carClass = Optional.empty();

        try {
            carClass = Optional.ofNullable(session.get(CarClass.class, id));
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return carClass;
    }

    @Override
    public List<CarClass> getAll() {
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
}
