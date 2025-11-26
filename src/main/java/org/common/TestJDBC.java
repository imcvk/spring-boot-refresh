package org.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDBC {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/hibernatepractice";
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
             // check using lowercase name (Postgres folds unquoted identifiers to lowercase)
             ResultSet rs = s.executeQuery("SELECT datname FROM pg_database WHERE datname='hibernatepractice'")) {
            if (rs.next()) System.out.println("Database found: " + rs.getString(1));
            else System.out.println("Database not found");
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }
    }
}
