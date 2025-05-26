package view;

import dao.BookingDAO;
import model.Booking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;

public class BookingForm extends JFrame {
    private final JTextField userField;
    private final JTextField packageField;
    private final JButton bookBtn;
    
    public BookingForm() {
        setTitle("Book Travel Package");
        setSize(500, 400);
        setLocationRelativeTo(null);
        
        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(245, 245, 245));
        JLabel titleLabel = new JLabel("Book Your Travel Package");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 33, 33));
        titlePanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create styled components
        userField = createStyledTextField();
        packageField = createStyledTextField();
        bookBtn = createStyledButton("Book Now");

        // Add components to form
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createStyledLabel("User ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createStyledLabel("Package ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(packageField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(bookBtn);

        // Add action listener
        bookBtn.addActionListener(e -> handleBooking());

        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(33, 33, 33));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(15);
        field.setPreferredSize(new Dimension(200, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(204, 204, 204)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(25, 118, 210));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private boolean validateInputs() {
        String userIdText = userField.getText().trim();
        String packageIdText = packageField.getText().trim();

        if (userIdText.isEmpty() || packageIdText.isEmpty()) {
            showError("Please fill all fields");
            return false;
        }

        try {
            int userId = Integer.parseInt(userIdText);
            int packageId = Integer.parseInt(packageIdText);

            if (userId <= 0 || packageId <= 0) {
                showError("IDs must be positive numbers");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Please enter valid numeric IDs (numbers only)");
            return false;
        }

        return true;
    }
    
    private void handleBooking() {
        if (!validateInputs()) {
            return;
        }
        
        try {
            int userId = Integer.parseInt(userField.getText().trim());
            int packageId = Integer.parseInt(packageField.getText().trim());
            
            Booking booking = new Booking(0, userId, packageId, new Date());
            boolean success = new BookingDAO().bookPackage(booking);
            
            if (success) {
                showSuccess("Booking successful!");
                dispose();
            } else {
                showError("Booking failed. Please verify that both User ID and Package ID exist in the database.");
            }
        } catch (Exception ex) {
            showError("An error occurred: " + ex.getMessage());
        }
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
