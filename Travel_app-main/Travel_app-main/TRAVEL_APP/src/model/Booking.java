package model;

import java.util.Date;

public class Booking {
    private int id, userId, packageId;
    private Date bookingDate;

    public Booking(int id, int userId, int packageId, Date bookingDate) {
        this.id = id;
        this.userId = userId;
        this.packageId = packageId;
        this.bookingDate = bookingDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getPackageId() { return packageId; }
    public void setPackageId(int packageId) { this.packageId = packageId; }

    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
}
