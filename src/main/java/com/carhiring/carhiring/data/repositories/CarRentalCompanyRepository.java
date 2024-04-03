package com.carhiring.carhiring.data.repositories;

import com.carhiring.carhiring.data.entities.CarRentalCompany;
import com.carhiring.carhiring.data.postgresql.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CarRentalCompanyRepository implements DAORepository<CarRentalCompany> {

    public static CarRentalCompanyRepository getInstance() { return CarRentalCompanyRepository.CarRentalCompanyRepositoryHolder.INSTANCE;}

    private static class CarRentalCompanyRepositoryHolder {
        public static final CarRentalCompanyRepository INSTANCE = new CarRentalCompanyRepository();
    }

    @Override
    public void save(CarRentalCompany obj) {
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
    public void update(CarRentalCompany obj) {
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
    public void delete(CarRentalCompany obj) {
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
    public Optional<CarRentalCompany> getById(int id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<CarRentalCompany> carRentalCompany = Optional.empty();

        try {
            carRentalCompany = Optional.ofNullable(session.get(CarRentalCompany.class, id));
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return carRentalCompany;
    }

    @Override
    public List<CarRentalCompany> getAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<CarRentalCompany> carRentalCompanies = new LinkedList<>();

        try {
            String jpql = "FROM CarRentalCompany";
            carRentalCompanies.addAll(session.createQuery(jpql, CarRentalCompany.class).getResultList());
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return carRentalCompanies;
    }
}
