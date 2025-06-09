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
        String username = "root";
        String password = "Yearup";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();

            // Query for full product info
            String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";
            ResultSet rs = stmt.executeQuery(query);

            // Display each product in stacked format
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                double price = rs.getDouble("UnitPrice");
                int stock = rs.getInt("UnitsInStock");

                System.out.println("Product Id: " + id);
                System.out.println("Name: " + name);
                System.out.printf("Price: %.2f\n", price);
                System.out.println("Stock: " + stock);
                System.out.println("-----------------------------");
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