package org.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Chapter1 {
    public static void main(String[] args) throws Exception {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        /*String query = "select * from student";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String name = resultSet.getString(1);
            String lname = resultSet.getString(2);
            String email = resultSet.getString(3);
            String rollNo = resultSet.getString(4);
            System.out.println("Name:" + name + " Lname:" + lname + " Email:" + email + " RollNo:" + rollNo);
        }*/
    String query="select * from student where id=1";
    ResultSet resultSet = statement.executeQuery(query);
    while (resultSet.next()) {
        System.out.println(resultSet.getInt(1));
        System.out.println(resultSet.getString(2));
        System.out.println(resultSet.getString(3));
        System.out.println(resultSet.getString(4));
        System.out.println(resultSet.getInt(5));
    }
    }
}
