-- Create the database
CREATE DATABASE IF NOT EXISTS travel_app;
USE travel_app;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'user'
);

-- Packages table
CREATE TABLE IF NOT EXISTS packages (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    destination VARCHAR(100) NOT NULL,
    duration INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    available_seats INT NOT NULL DEFAULT 20
);

-- Bookings table
CREATE TABLE IF NOT EXISTS bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    package_id INT NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (package_id) REFERENCES packages(id)
);

-- Insert admin user
INSERT INTO users (name, email, password, role) 
VALUES ('Admin', 'admin@travel.com', 'admin123', 'admin');

-- Insert sample packages
INSERT INTO packages (name, description, destination, duration, price, available_seats) VALUES
('Paris Adventure', 'Explore the city of love with guided tours', 'Paris, France', 7, 1499.99, 15),
('Tokyo Explorer', 'Experience Japanese culture and modern city life', 'Tokyo, Japan', 10, 2499.99, 20),
('Beach Paradise', 'Relax at pristine beaches with luxury accommodation', 'Bali, Indonesia', 5, 999.99, 25);