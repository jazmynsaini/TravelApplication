package util;

import dao.BookingDAO;
import model.Booking;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class BookingExporter {
    public static boolean exportBookingsToFile(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return false;
        }

        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("ID,User ID,Package ID,Booking Date\n");

            List<Booking> bookings = new BookingDAO().getAllBookings();

            if (bookings.isEmpty()) {
                fw.write("No bookings found\n");
                return true;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (Booking b : bookings) {
                fw.write(String.format("%d,%d,%d,%s\n",
                        b.getId(),
                        b.getUserId(),
                        b.getPackageId(),
                        dateFormat.format(b.getBookingDate())
                ));
            }

            return true;

        } catch (IOException e) {
            System.err.println("Error exporting bookings: " + e.getMessage());
            return false;
        }
    }
}
