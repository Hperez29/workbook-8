package com.pluralsight;

package com.northwind.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcedureDAO {
    private final String URL = "jdbc:mysql://localhost:3306/northwind";
    private final String USER = "root";
    private final String PASSWORD = "your_password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<String[]> getCustOrderHistory(String customerId) throws SQLException {
        List<String[]> result = new ArrayList<>();
        String sql = "{call CustOrderHistory(?)}";

        try (Connection conn = getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.add(new String[]{rs.getString("ProductName"), rs.getString("Total")});
            }
        }

        return result;
    }

    public List<String[]> getSalesByYear(String startDate, String endDate) throws SQLException {
        List<String[]> result = new ArrayList<>();
        String sql = "{call `Sales by Year`(?, ?)}";

        try (Connection conn = getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.add(new String[]{
                        rs.getString("OrderID"),
                        rs.getString("Subtotal"),
                        rs.getString("Year")
                });
            }
        }

        return result;
    }

    public List<String[]> getSalesByCategory(String categoryName, String year) throws SQLException {
        List<String[]> result = new ArrayList<>();
        String sql = "{call SalesByCategory(?, ?)}";

        try (Connection conn = getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, categoryName);
            stmt.setString(2, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.add(new String[]{
                        rs.getString("ProductName"),
                        rs.getString("TotalPurchase")
                });
            }
        }

        return result;
    }
}