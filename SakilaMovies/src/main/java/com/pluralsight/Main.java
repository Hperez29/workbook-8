package com.pluralsight;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = SakilaDataSource.getDataSource();

        try (Scanner scanner = new Scanner(System.in);
             Connection conn = dataSource.getConnection()) {

            // Step 1: Ask for last name
            System.out.print("Enter an actor's last name: ");
            String lastName = scanner.nextLine();

            String actorQuery = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(actorQuery)) {
                stmt.setString(1, lastName);
                try (ResultSet rs = stmt.executeQuery()) {
                    boolean found = false;
                    System.out.println("\nMatching Actors:");
                    while (rs.next()) {
                        found = true;
                        System.out.printf("- ID: %d, Name: %s %s%n",
                                rs.getInt("actor_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"));
                    }
                    if (!found) {
                        System.out.println("No actors found with that last name.");
                    }
                }
            }

            // Step 2: Ask for full name
            System.out.print("\nEnter the actor's first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter the actor's last name: ");
            lastName = scanner.nextLine();

            String filmQuery = """
                SELECT f.title
                FROM film f
                JOIN film_actor fa ON f.film_id = fa.film_id
                JOIN actor a ON a.actor_id = fa.actor_id
                WHERE a.first_name = ? AND a.last_name = ?
                ORDER BY f.title;
                """;

            try (PreparedStatement stmt = conn.prepareStatement(filmQuery)) {
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                try (ResultSet rs = stmt.executeQuery()) {
                    boolean found = false;
                    System.out.println("\nMovies featuring " + firstName + " " + lastName + ":");
                    while (rs.next()) {
                        found = true;
                        System.out.println("- " + rs.getString("title"));
                    }
                    if (!found) {
                        System.out.println("No movies found for that actor.");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}