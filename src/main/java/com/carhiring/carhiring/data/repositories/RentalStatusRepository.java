package com.carhiring.carhiring.data.repositories;

import com.carhiring.carhiring.data.entities.RentalStatus;
import com.carhiring.carhiring.data.postgresql.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RentalStatusRepository implements DAORepository<RentalStatus> {

    public static RentalStatusRepository getInstance() { return RentalStatusRepository.RentalStatusRepositoryHolder.INSTANCE;}

    private static class RentalStatusRepositoryHolder {
        public static final RentalStatusRepository INSTANCE = new RentalStatusRepository();
    }

    @Override
    public void save(RentalStatus obj) {
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
    public void update(RentalStatus obj) {
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
    public void delete(RentalStatus obj) {
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
    public Optional<RentalStatus> getById(int id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<RentalStatus> rentalStatus = Optional.empty();

        try {
            rentalStatus = Optional.ofNullable(session.get(RentalStatus.class, id));
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return rentalStatus;
    }

    @Override
    public List<RentalStatus> getAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<RentalStatus> rentalStatuses = new LinkedList<>();

        try {
            String jpql = "FROM RentalStatus";
            rentalStatuses.addAll(session.createQuery(jpql, RentalStatus.class).getResultList());
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

        return rentalStatuses;
    }
}
