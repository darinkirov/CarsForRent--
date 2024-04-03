package com.carhiring.carhiring.data.postgresql;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connection {
    private static final Logger log = Logger.getLogger(Connection.class);
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception ex) {
            System.out.println("Initial SessionFactory creation failed with error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static Session openSession() {
        if (sessionFactory == null) {
            System.out.println("SessionFactory is null. Cannot open session.");
            return null;
        }
        return sessionFactory.openSession();
    }

    public static void closeSession() {
        sessionFactory.close();
    }
}
