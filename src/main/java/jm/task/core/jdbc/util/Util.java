package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {

    private static final String HIBERNATE_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
    private static final String HIBERNATE_SHOW_SQL= "true";
    private static final String HIBERNATE_FORMAT_SQL= "true";
    private static final String HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS= "thread";
    private static final String HIBERNATE_HBM2DDL_AUTO= "update";

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/kata_task1_1_3");
                settings.put(Environment.USER, "postgres");
                settings.put(Environment.PASS, "postgres");

                settings.put(Environment.DIALECT, HIBERNATE_DIALECT);
                settings.put(Environment.SHOW_SQL, HIBERNATE_SHOW_SQL);
                settings.put(Environment.FORMAT_SQL, HIBERNATE_FORMAT_SQL);
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS);
                settings.put(Environment.HBM2DDL_AUTO, HIBERNATE_HBM2DDL_AUTO);

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return sessionFactory;
    }
        public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        }


    private static final String URL = "jdbc:postgresql://localhost:5432/kata_task1_1_3";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
