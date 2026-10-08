package org.example.company;
import java.io.IOException;
import java.sql.*;

public class DBConnection{
    private static String URL="jdbc:mysql://localhost:3306/jobscheduling";
    private static String USER="root";
    private static String PASS="praveen";
    public static Connection getConnection() throws SQLException
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(URL,USER,PASS);
    }
}
