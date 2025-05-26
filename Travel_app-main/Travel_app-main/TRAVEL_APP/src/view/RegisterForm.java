package view;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterForm extends JFrame {
    private final JTextField nameField;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    
    public RegisterForm() {
        setTitle("Travel App - Register");
        setSize(600, 500);  // Increased window size
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
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 33, 33));
        titlePanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);  // Increased padding
        gbc.weightx = 1.0; // Allow components to expand horizontally

        // Create styled components
        nameField = createStyledTextField();
        emailField = createStyledTextField();
        passwordField = createStyledPasswordField();
        JButton registerButton = createStyledButton("Register");
        JButton backButton = createStyledButton("Back to Login");
        backButton.setBackground(new Color(158, 158, 158));

        // Add components to form with proper spacing
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3; // Less space for labels
        formPanel.add(createStyledLabel("Name:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7; // More space for text fields
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(createStyledLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(createStyledLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(passwordField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        // Add action listeners
        registerButton.addActionListener(e -> handleRegistration());
        backButton.addActionListener(e -> {
            dispose();
            new LoginForm();
        });

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
        JTextField field = new JTextField(20);
        field.setPreferredSize(new Dimension(300, 40));  // Increased size
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(204, 204, 204)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)  // Increased padding
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setPreferredSize(new Dimension(300, 40));  // Increased size
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(204, 204, 204)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)  // Increased padding
        ));
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(25, 118, 210));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private void handleRegistration() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showError("Please fill all fields");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            showError("Invalid email format");
            return;
        }

        if (password.length() < 6) {
            showError("Password must be at least 6 characters long");
            return;
        }

        try {
            if (UserDAO.register(name, email, password)) {
                showSuccess("Registration successful! Please login.");
                dispose();
                new LoginForm();
            } else {
                showError("Registration failed. Email might already be in use.");
            }
        } catch (Exception ex) {
            showError("Error during registration: " + ex.getMessage());
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