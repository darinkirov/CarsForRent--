package com.carhiring.carhiring.data.repositories;

import com.carhiring.carhiring.data.entities.Client;
import com.carhiring.carhiring.data.postgresql.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements DAORepository<Client> {

    public static ClientRepository getInstance() { return ClientRepository.ClientRepositoryHolder.INSTANCE;}

    private static class ClientRepositoryHolder {
        public static final ClientRepository INSTANCE = new ClientRepository();
    }

    @Override
    public void save(Client obj) {
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
    public void update(Client obj) {
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
    public void delete(Client obj) {
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
    public Optional<Client> getById(int id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<Client> client = Optional.empty();

        try {
            client = Optional.ofNullable(session.get(Client.class, id));
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return client;
    }
    public Client getByName(String name) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Client client = null;

        try {
            String hql = "FROM Client c WHERE c.name = :name";
            Query<Client> query = session.createQuery(hql, Client.class);
            query.setParameter("name", name);
            client = query.uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return client;
    }

    @Override
    public List<Client> getAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Client> clients = new LinkedList<>();

        try {
            String jpql = "FROM Client";
            clients.addAll(session.createQuery(jpql, Client.class).getResultList());
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return clients;
    }
}
