package com.carhiring.carhiring.data.repositories;

import com.carhiring.carhiring.data.entities.Role;
import com.carhiring.carhiring.data.postgresql.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RoleRepository implements DAORepository<Role> {

    public static RoleRepository getInstance() { return RoleRepository.RoleRepositoryHolder.INSTANCE; }

    private static class RoleRepositoryHolder {
        public static final RoleRepository INSTANCE = new RoleRepository();
    }

    @Override
    public void save(Role obj) {
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
    public void update(Role obj) {
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
    public void delete(Role obj) {
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
    public Optional<Role> getById(int id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Role> role = Optional.empty();

        try {
            role = Optional.ofNullable(session.get(Role.class, id));
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return role;
    }

    @Override
    public List<Role> getAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Role> roles = new LinkedList<>();

        try {
            String jpql = "FROM Role";
            roles.addAll(session.createQuery(jpql, Role.class).getResultList());
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return roles;
    }
}
