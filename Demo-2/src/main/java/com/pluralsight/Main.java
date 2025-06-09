package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        // MySQL credentials
        String username = "yourUsername";  // <-- Replace with your actual username
        String password = "yourPassword";  // <-- Replace with your actual password

        try {
            // Load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection to the database
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/world", username, password);

            // Create a statement
            Statement statement = connection.createStatement();

            // Define your query
            String query = "SELECT Name FROM city WHERE CountryCode = 'USA'";

            // Execute your query
            ResultSet results = statement.executeQuery(query);

            // Process the results
            while (results.next()) {
                String city = results.getString("Name");
                System.out.println(city);
            }

            // Close the connection
            connection.close();

        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database error.");
            e.printStackTrace();
        }
    }
}