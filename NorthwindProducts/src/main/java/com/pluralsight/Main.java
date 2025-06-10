package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to DB
            String url = "jdbc:mysql://localhost:3306/northwind";
            String username = "root";
            String password = "Yearup";
            conn = DriverManager.getConnection(url, username, password);

            int choice;
            do {
                // Show menu
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        displayProducts(conn);
                        break;
                    case 2:
                        displayCustomers(conn);
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } while (choice != 0);

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database error.");
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Failed to close the connection.");
            }
        }
    }

    // Method to display products (stacked layout)
    private static void displayProducts(Connection conn) {
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Product Id: " + rs.getInt("ProductID"));
                System.out.println("Name: " + rs.getString("ProductName"));
                System.out.printf("Price: %.2f\n", rs.getDouble("UnitPrice"));
                System.out.println("Stock: " + rs.getInt("UnitsInStock"));
                System.out.println("-----------------------------");
            }

        } catch (SQLException e) {
            System.err.println("Failed to retrieve products.");
            e.printStackTrace();
        }
    }

    // Method to display customers
    private static void displayCustomers(Connection conn) {
        String query = """
            SELECT ContactName, CompanyName, City, Country, Phone
            FROM customers
            ORDER BY Country, CompanyName
            """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Contact: " + rs.getString("ContactName"));
                System.out.println("Company: " + rs.getString("CompanyName"));
                System.out.println("City: " + rs.getString("City"));
                System.out.println("Country: " + rs.getString("Country"));
                System.out.println("Phone: " + rs.getString("Phone"));
                System.out.println("-----------------------------");
            }

        } catch (SQLException e) {
            System.err.println("Failed to retrieve customers.");
            e.printStackTrace();
        }
    }
}