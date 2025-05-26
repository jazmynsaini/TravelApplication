package dao;

import util.DatabaseConnection;
import java.sql.*;

public class UserDAO {
    public static boolean login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
                return false;
            }
            stmt.setString(1, email.trim());
            stmt.setString(2, password); // In production, use password hashing
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Database error during login: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error during login: " + e.getMessage());
            return false;
        }
    }

    public static boolean register(String name, String email, String password) {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, 'user')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, name.trim());
            stmt.setString(2, email.trim());
            stmt.setString(3, password); // In production, use password hashing
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLIntegrityConstraintViolationException e) {
            // This occurs when email already exists
            System.err.println("Email already exists: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.err.println("Database error during registration: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error during registration: " + e.getMessage());
            return false;
        }
    }
}
