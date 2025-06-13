package com.pluralsight;

package com.northwind;

import com.northwind.dao.ProcedureDAO;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ProcedureDAO dao = new ProcedureDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Northwind Stored Procedures ---");
            System.out.println("1. Search Customer Order History");
            System.out.println("2. Search Sales by Year");
            System.out.println("3. Search Sales by Category");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter Customer ID: ");
                        String customerId = scanner.nextLine();
                        List<String[]> history = dao.getCustOrderHistory(customerId);
                        history.forEach(row -> System.out.println("Product: " + row[0] + " | Total: " + row[1]));
                    }
                    case 2 -> {
                        System.out.print("Enter start date (yyyy-mm-dd): ");
                        String start = scanner.nextLine();
                        System.out.print("Enter end date (yyyy-mm-dd): ");
                        String end = scanner.nextLine();
                        List<String[]> sales = dao.getSalesByYear(start, end);
                        sales.forEach(row -> System.out.println("Order ID: " + row[0] + " | Subtotal: " + row[1] + " | Year: " + row[2]));
                    }
                    case 3 -> {
                        System.out.print("Enter Category Name: ");
                        String category = scanner.nextLine();
                        System.out.print("Enter Year (e.g., 1997): ");
                        String year = scanner.nextLine();
                        List<String[]> salesCat = dao.getSalesByCategory(category, year);
                        salesCat.forEach(row -> System.out.println("Product: " + row[0] + " | Total Purchase: " + row[1]));
                    }
                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}