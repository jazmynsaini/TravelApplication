package dao;

import model.TravelPackage;
import util.DatabaseConnection;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class PackageDAO {
    private static final Logger logger = Logger.getLogger(PackageDAO.class.getName());
    
    public List<TravelPackage> getAllPackages() {
        List<TravelPackage> packages = new ArrayList<>();
        String sql = "SELECT * FROM packages ORDER BY price ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                TravelPackage travelPackage = new TravelPackage(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description")
                );
                packages.add(travelPackage);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving packages: " + e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve packages", e);
        }
        return packages;
    }
}
