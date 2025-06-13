package com.pluralsight.data;

import com.pluralsight.models.Shipper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipperDAO {
    private DataSource dataSource;

    public ShipperDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int addShipper(Shipper shipper) throws SQLException {
        String sql = "INSERT INTO shippers (CompanyName, Phone) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, shipper.getName());
            stmt.setString(2, shipper.getPhone());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }
        return -1;
    }

    public List<Shipper> getAllShippers() throws SQLException {
        List<Shipper> shippers = new ArrayList<>();
        String sql = "SELECT ShipperID, CompanyName, Phone FROM shippers";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                shippers.add(new Shipper(
                        rs.getInt("ShipperID"),
                        rs.getString("CompanyName"),
                        rs.getString("Phone")
                ));
            }
        }
        return shippers;
    }

    public void updateShipperPhone(int id, String phone) throws SQLException {
        String sql = "UPDATE shippers SET Phone = ? WHERE ShipperID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void deleteShipper(int id) throws SQLException {
        String sql = "DELETE FROM shippers WHERE ShipperID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}