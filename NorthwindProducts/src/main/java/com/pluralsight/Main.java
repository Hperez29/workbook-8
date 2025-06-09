package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // DB credentials
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";           // Replace with your username
        String password = "Yearup";  // Replace with your password

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();

            // Execute query
            String query = "SELECT ProductName FROM products";
            ResultSet rs = stmt.executeQuery(query);

            // Display product names
            while (rs.next()) {
                System.out.println(rs.getString("ProductName"));
            }

            // Close connection
            conn.close();

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database error.");
            e.printStackTrace();
        }
    }
}