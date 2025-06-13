package com.pluralsight;

import com.pluralsight.data.ShipperDAO;
import com.pluralsight.models.Shipper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = NorthwindDataSource.getDataSource();
        ShipperDAO dao = new ShipperDAO(dataSource);
        Scanner scanner = new Scanner(System.in);

        try {
            // 1. Add new shipper
            System.out.print("Enter new shipper name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new shipper phone: ");
            String phone = scanner.nextLine();
            int newId = dao.addShipper(new Shipper(name, phone));
            System.out.println("New shipper added with ID: " + newId);

            // 2. Display all shippers
            displayAllShippers(dao);

            // 3. Update shipper phone
            System.out.print("Enter the ID of shipper to update phone: ");
            int idToUpdate = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter new phone number: ");
            String newPhone = scanner.nextLine();
            dao.updateShipperPhone(idToUpdate, newPhone);

            // 4. Display all shippers again
            displayAllShippers(dao);

            // 5. Delete a shipper (not ID 1-3)
            System.out.print("Enter the ID of the shipper to delete (not 1-3): ");
            int idToDelete = Integer.parseInt(scanner.nextLine());
            if (idToDelete > 3) {
                dao.deleteShipper(idToDelete);
                System.out.println("Shipper with ID " + idToDelete + " deleted.");
            } else {
                System.out.println("Cannot delete shipper with ID 1-3.");
            }

            // 6. Display all shippers final
            displayAllShippers(dao);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayAllShippers(ShipperDAO dao) throws SQLException {
        System.out.println("\nCurrent Shippers:");
        List<Shipper> shippers = dao.getAllShippers();
        for (Shipper s : shippers) {
            System.out.println(s);
        }
    }
}