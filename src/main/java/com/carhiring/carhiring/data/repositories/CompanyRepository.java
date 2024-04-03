package com.carhiring.carhiring.data.repositories;

import com.carhiring.carhiring.data.entities.Company;
import com.carhiring.carhiring.data.postgresql.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CompanyRepository implements DAORepository<Company> {

    public static CompanyRepository getInstance() {
        return CompanyRepository.CompanyRepositoryHolder.INSTANCE;
    }

    private static class CompanyRepositoryHolder {
        public static final CompanyRepository INSTANCE = new CompanyRepository();
    }

    @Override
    public void save(Company obj) {
        Session session = Connection.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(obj);
            transaction.commit();
            System.out.println("Company saved successfully.");
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Company save error: " + ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void update(Company obj) {
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
    public void delete(Company obj) {
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
    public Optional<Company> getById(int id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Company> company = Optional.empty();

        try {
            company = Optional.ofNullable(session.get(Company.class, id));
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return company;
    }

    @Override
    public List<Company> getAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Company> companies = new LinkedList<>();

        try {
            String jpql = "FROM Company";
            companies.addAll(session.createQuery(jpql, Company.class).getResultList());
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return companies;
    }

    public Company getByName(String name) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Company company = null;

        try {
            String jpql = "SELECT c FROM Company c WHERE name = '" + name + "'";
            company = (Company) session.createQuery(jpql).getSingleResult();
            System.out.println("Get Company by name successfully.");
        } catch(Exception ex) {
            System.out.println("Get company by name error: " + ex.getMessage());
        } finally {
            transaction.commit();
        }

        return company;
    }

}
