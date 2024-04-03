package com.carhiring.carhiring.data.repositories;

import com.carhiring.carhiring.data.entities.User;
import com.carhiring.carhiring.data.postgresql.Connection;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements DAORepository<User> {

    private static final Logger log = Logger.getLogger(UserRepository.class);

    public static UserRepository getInstance() { return UserRepository.UserRepositoryHolder.INSTANCE;}

    public User getByUsernameAndPassword(String username, String password) {
        Session session = Connection.openSession();
        if (session == null) {
            System.out.println("Session is null. Cannot get user by username and password.");
            return null;
        }

        Transaction transaction = session.beginTransaction();
        User user = null;

        try{
            String jpql = "SELECT u FROM User u WHERE username = '" + username + "' AND password = '" + password + "'";
            user = (User) session.createQuery(jpql).getSingleResult();
            System.out.println("Get User by username & password successfully.");
        } catch (Exception ex) {
            System.out.println("Get user by username & password error: " + ex.getMessage());
        } finally {
            transaction.commit();
        }

        return user;
    }

    public User getByUsername(String username) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        User user = null;

        try {
            String jpql = "SELECT u FROM User u WHERE username = '" + username + "'";
            user = (User) session.createQuery(jpql).getSingleResult();
            log.info("Get User by username successfully.");
        } catch(Exception ex) {
            log.error("Get user by username error: " + ex.getMessage());
        } finally {
            transaction.commit();
        }

        return user;
    }

    private static class UserRepositoryHolder {
        public static final UserRepository INSTANCE = new UserRepository();
    }

    @Override
    public void save(User obj) {
        Session session = Connection.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(obj);
            transaction.commit();
            log.info("User saved successfully.");
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("User save error: " + ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void update(User obj) {
        Session session = Connection.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(obj);
            transaction.commit();
            log.info("User updated successfully.");
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("User update error: " + ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(User obj) {

    }

    @Override
    public Optional<User> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> users = new LinkedList<>();

        try{
            String jpql = "FROM User";
            users.addAll(session.createQuery(jpql, User.class).getResultList());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            transaction.commit();
        }
        return users;
    }
}