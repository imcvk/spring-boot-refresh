package org.jdbc;

import com.github.javafaker.Faker;
import org.common.ConnectionSetup;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CRUD_Operation {
    static String insert = "insert into student values(?,?,?,?,?)";
    static String delete = "delete  from student where id=?;";
    static String update = "update student set firstname=?,lastname=?,emailid=? where id=?";

    public static ArrayList<String> mockStudentDetail() {
        String id, fname, lname, emailid, rollno;
        Faker faker = new Faker();
        id = faker.number().numberBetween(1, 100) + "";
        fname = faker.name().firstName();
        lname = faker.name().lastName();
        emailid = faker.internet().emailAddress();
        rollno = id;
        return new ArrayList<>(Arrays.asList(id, fname, lname, emailid, rollno));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionSetup.getConnection();
        ArrayList<String> data = CRUD_Operation.mockStudentDetail();
        if (CRUD_Operation.insert(conn, data) == 1) {
            System.out.println("RECORD INSERTED");
            printMap(CRUD_Operation.get(data.get(0)));
        } else {
            System.out.println("Not Inserted");
        }
        if (CRUD_Operation.update(conn, data) == 1) {
            System.out.println("RECORD UPDATED");
            printMap(CRUD_Operation.get(data.get(0)));
        } else {
            System.out.println("Not Updated");
        }
        System.out.println("RECORD DELETING");
        printMap(CRUD_Operation.get(data.get(0)));
        if (CRUD_Operation.delete(conn, Integer.parseInt(data.get(0))) == 1) {
            System.out.println("Deleted");
        } else {
            System.out.println("Not Deleted");
        }
    }

    public static int insert(Connection connection, ArrayList<String> data) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = connection.prepareStatement(insert);
        statement.setInt(1, Integer.parseInt(data.get(0)));
        statement.setString(2, data.get(1));
        statement.setString(3, data.get(2));
        statement.setString(4, data.get(3));
        statement.setInt(5, Integer.parseInt(data.get(4)));
        int result = statement.executeUpdate();
        return result;
    }

    public static int delete(Connection connection, int id) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = connection.prepareStatement(delete);
        statement.setInt(1, id);
        return statement.executeUpdate();

    }

    public static int update(Connection connection, ArrayList<String> data) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = connection.prepareStatement(update);
        statement.setString(1, data.get(1));
        statement.setString(2, data.get(2));
        statement.setString(3, data.get(3));
        statement.setInt(4, Integer.parseInt(data.get(4)));
        return statement.executeUpdate();
    }

    //    public Map<String, Object> getAll() throws SQLException, ClassNotFoundException {
//    }
//
    public static Map<String, Object> get(String id) throws SQLException, ClassNotFoundException {
        Statement statement = ConnectionSetup.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from student where id=" + id);
        Map<String, Object> map = new HashMap<>();
        while (resultSet.next()) {
            map.put("id", resultSet.getString("id"));
            map.put("firstname", resultSet.getString("firstname"));
            map.put("lastname", resultSet.getString("lastname"));
            map.put("emailid", resultSet.getString("emailid"));
            map.put("rollno", resultSet.getString("rollno"));
        }
        return map;
    }

    public static void printMap(Map<String, Object> map) {
        System.out.println("------------------------------------------------------------------------------------------");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }
}

