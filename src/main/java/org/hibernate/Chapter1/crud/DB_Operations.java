package org.hibernate.Chapter1.crud;

import org.hibernate.Chapter1.pojo.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB_Operations {
    private static final Logger LOGGER = Logger.getLogger(DB_Operations.class.getName());

    // SessionFactory is expensive to create — build it once and reuse across the application
    private static volatile SessionFactory sessionFactory = null;

    private final Configuration configuration;

    /**
     * Initialize configuration and register annotated entity classes.
     */
    public DB_Operations() {
        configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        // ensure Hibernate picks up our annotated entity
        configuration.addAnnotatedClass(Student.class);
    }

    /**
     * Lazily build and return a singleton SessionFactory. Thread-safe.
     */
    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (DB_Operations.class) {
                if (sessionFactory == null) {
                    sessionFactory = configuration.buildSessionFactory();
                }
            }
        }
        return sessionFactory;
    }

    public Session getSession() {
        return getSessionFactory().openSession();
    }

    public void createStudent(Student student) {
        Objects.requireNonNull(student, "student must not be null");

        try (Session session = getSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.persist(student);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null) {
                    try {
                        tx.rollback();
                    } catch (RuntimeException re) {
                        LOGGER.log(Level.WARNING, "Could not rollback transaction", re);
                    }
                }
                LOGGER.log(Level.SEVERE, "Error creating student", e);
                throw e;
            }
        }
    }

    public void updateStudent(Student student) {
        Objects.requireNonNull(student, "student must not be null");

        try (Session session = getSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.merge(student);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null) {
                    try {
                        tx.rollback();
                    } catch (RuntimeException re) {
                        LOGGER.log(Level.WARNING, "Could not rollback transaction", re);
                    }
                }
                LOGGER.log(Level.SEVERE, "Error updating student", e);
                throw e;
            }
        }
    }

    public Student getStudentById(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        try (Session session = getSession()) {
            return session.find(Student.class, id);
        }
    }
    /**
     * Close the shared SessionFactory. After calling this method, getSessionFactory() will create a new one on demand.
     * Call this on application shutdown.
     */
    public void closeSessionFactory() {
        synchronized (DB_Operations.class) {
            if (sessionFactory != null) {
                try {
                    sessionFactory.close();
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Error closing SessionFactory", e);
                } finally {
                    sessionFactory = null;
                }
            }
        }
    }
    public void deleteStudent(Student student) {
        try (Session session = getSession()) {
            session.remove(student);
        }
    }

    public List<Student> getAllStudents() {
       return getSession().createQuery("from Student", Student.class).list();
    }
}
