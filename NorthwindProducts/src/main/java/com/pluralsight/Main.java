package com.pluralsight;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = NorthwindDataSource.getDataSource();

        try (Scanner scanner = new Scanner(System.in);
             Connection conn = dataSource.getConnection()) {

            int choice;
            do {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("3) Display all categories");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> displayProducts(conn);
                    case 2 -> displayCustomers(conn);
                    case 3 -> displayCategoriesAndProducts(conn, scanner);
                    case 0 -> System.out.println("Goodbye!");
                    default -> System.out.println("Invalid option. Try again.");
                }
            } while (choice != 0);

        } catch (SQLException e) {
            System.err.println("Database error.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Startup error.");
            e.printStackTrace();
        }
    }

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

    private static void displayCategoriesAndProducts(Connection conn, Scanner scanner) {
        String categoryQuery = "SELECT CategoryID, CategoryName FROM categories ORDER BY CategoryID";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(categoryQuery)) {

            System.out.println("\nAvailable Categories:");
            while (rs.next()) {
                System.out.println("Category ID: " + rs.getInt("CategoryID"));
                System.out.println("Name: " + rs.getString("CategoryName"));
                System.out.println("-----------------------------");
            }

            System.out.print("Enter a Category ID to view its products: ");
            int selectedId = Integer.parseInt(scanner.nextLine());

            String productQuery = """
                SELECT ProductID, ProductName, UnitPrice, UnitsInStock
                FROM products
                WHERE CategoryID = ?
                ORDER BY ProductName
                """;

            try (PreparedStatement pstmt = conn.prepareStatement(productQuery)) {
                pstmt.setInt(1, selectedId);
                try (ResultSet prodRs = pstmt.executeQuery()) {
                    boolean found = false;
                    while (prodRs.next()) {
                        found = true;
                        System.out.println("Product Id: " + prodRs.getInt("ProductID"));
                        System.out.println("Name: " + prodRs.getString("ProductName"));
                        System.out.printf("Price: %.2f\n", prodRs.getDouble("UnitPrice"));
                        System.out.println("Stock: " + prodRs.getInt("UnitsInStock"));
                        System.out.println("-----------------------------");
                    }
                    if (!found) {
                        System.out.println("No products found for category ID: " + selectedId);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Failed to retrieve categories or products.");
            e.printStackTrace();
        }
    }
}