package view;

import dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class LoginForm extends JFrame {
    public LoginForm() {
        setTitle("Travel App - Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Main panel with padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title label
        JLabel titleLabel = new JLabel("Welcome to Travel App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 33, 33));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Input fields
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        emailField.setPreferredSize(new Dimension(200, 30));
        passwordField.setPreferredSize(new Dimension(200, 30));

        // Layout components
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        mainPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Style buttons
        loginButton.setPreferredSize(new Dimension(100, 35));
        registerButton.setPreferredSize(new Dimension(100, 35));

        loginButton.setBackground(new Color(25, 118, 210));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);

        registerButton.setBackground(new Color(67, 160, 71));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        mainPanel.add(buttonPanel, gbc);

        // Add validation and error handling
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = String.valueOf(passwordField.getPassword());
            
            if (email.isEmpty() || password.isEmpty()) {
                showError("Please fill all fields");
                return;
            }
            
            if (!email.contains("@") || !email.contains(".")) {
                showError("Invalid email format");
                return;
            }

            if (UserDAO.login(email, password)) {
                dispose();
                new Dashboard();
            } else {
                showError("Invalid credentials");
            }
        });

        registerButton.addActionListener(e -> {
            dispose();
            new RegisterForm();
        });
        add(mainPanel);
        pack();
        setVisible(true);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
