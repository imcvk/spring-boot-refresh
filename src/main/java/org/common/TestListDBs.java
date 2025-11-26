package org.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestListDBs {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "root";
        System.out.println("Connecting to: " + url + " as " + user);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Postgres driver not found on classpath: " + e.getMessage());
            System.exit(1);
        }

        try (Connection c = DriverManager.getConnection(url, user, pass);
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT datname FROM pg_database ORDER BY datname")) {
            System.out.println("Databases on server:");
            while (rs.next()) System.out.println(" - " + rs.getString(1));
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }
    }
}

