package com.carhiring.carhiring.data.repositories;

import com.carhiring.carhiring.data.entities.CarCategory;
import com.carhiring.carhiring.data.postgresql.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CarCategoryRepository implements DAORepository<CarCategory> {

    public static CarCategoryRepository getInstance() { return CarCategoryRepository.CarCategoryRepositoryHolder.INSTANCE;}

    private static class CarCategoryRepositoryHolder {
        public static final CarCategoryRepository INSTANCE = new CarCategoryRepository();
    }

    @Override
    public void save(CarCategory obj) {
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
    public void update(CarCategory obj) {
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
    public void delete(CarCategory obj) {
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
    public Optional<CarCategory> getById(int id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<CarCategory> carCategory = Optional.empty();

        try {
            carCategory = Optional.ofNullable(session.get(CarCategory.class, id));
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return carCategory;
    }

    public CarCategory getByName(String name) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        CarCategory carCategory = null;

        try {
            String jpql = "FROM CarCategory WHERE name = :name";
            carCategory = session.createQuery(jpql, CarCategory.class)
                    .setParameter("name", name)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return carCategory;
    }

    @Override
    public List<CarCategory> getAll() {
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
}
