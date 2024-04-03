package com.carhiring.carhiring.data.repositories;

import com.carhiring.carhiring.data.entities.Operator;
import com.carhiring.carhiring.data.entities.User;
import com.carhiring.carhiring.data.postgresql.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class OperatorRepository implements DAORepository<Operator> {

    public static OperatorRepository getInstance() { return OperatorRepository.OperatorRepositoryHolder.INSTANCE;}

    private static class OperatorRepositoryHolder {
        public static final OperatorRepository INSTANCE = new OperatorRepository();
    }

    @Override
    public void save(Operator obj) {
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
    public void update(Operator obj) {
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
    public void delete(Operator obj) {
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
    public Optional<Operator> getById(int id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Operator> operator = Optional.empty();

        try {
            operator = Optional.ofNullable(session.get(Operator.class, id));
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return operator;
    }

    @Override
    public List<Operator> getAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Operator> operators = new LinkedList<>();

        try {
            String jpql = "FROM Operator";
            operators.addAll(session.createQuery(jpql, Operator.class).getResultList());
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return operators;
    }

    public Operator getByUser(User user) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Operator operator = null;

        try {
            String jpql = "SELECT o FROM Operator o WHERE o.user = :user";
            operator = (Operator) session.createQuery(jpql).setParameter("user", user).getSingleResult();
            System.out.println("Get Operator by User successfully.");
        } catch(Exception ex) {
            System.out.println("Get Operator by User error: " + ex.getMessage());
        } finally {
            transaction.commit();
        }

        return operator;
    }

}
