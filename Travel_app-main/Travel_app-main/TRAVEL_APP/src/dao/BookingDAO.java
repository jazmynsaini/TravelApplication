package dao;

import model.Booking;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date; // Use java.util.Date specifically

public class BookingDAO {
    public boolean bookPackage(Booking booking) {
        if (booking == null || booking.getUserId() <= 0 || booking.getPackageId() <= 0) {
            return false;
        }

        String sql = "INSERT INTO bookings (user_id, package_id, booking_date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getPackageId());
            stmt.setTimestamp(3, new Timestamp(booking.getBookingDate().getTime()));
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating booking failed, no rows affected.");
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Database error during booking: " + e.getMessage());
            return false;
        }
    }

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY booking_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                list.add(new Booking(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("package_id"),
                    new Date(rs.getTimestamp("booking_date").getTime())
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving bookings: " + e.getMessage());
        }
        return list;
    }
}
