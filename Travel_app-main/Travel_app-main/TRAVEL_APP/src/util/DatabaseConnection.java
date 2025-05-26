package util;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/travel_app";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Jazmyn@2006";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
