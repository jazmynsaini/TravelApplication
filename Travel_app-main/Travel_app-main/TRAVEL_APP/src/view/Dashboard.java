package view;

import util.BookingExporter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("Travel App - Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(66, 134, 244);
                Color color2 = new Color(45, 85, 155);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        // Welcome panel with modern design
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setOpaque(false);
        JLabel welcomeLabel = new JLabel("Welcome to Travel App", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.WHITE);
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        // Button panel with card layout
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setOpaque(false);

        // Create modern card-style buttons
        JPanel[] cards = new JPanel[4];
        String[] buttonTexts = {"View Packages", "Book Package", "Export Bookings", "Logout"};
        Color[] buttonColors = {
            new Color(76, 175, 80),  // Green
            new Color(255, 152, 0),  // Orange
            new Color(156, 39, 176), // Purple
            new Color(244, 67, 54)   // Red
        };

        for (int i = 0; i < 4; i++) {
            cards[i] = createCardButton(buttonTexts[i], buttonColors[i]);
            buttonPanel.add(cards[i]);
        }

        // Add action listeners
        cards[0].addMouseListener(createMouseListener(() -> {
            try {
                new PackageViewer();
            } catch (Exception ex) {
                showError("Error loading packages", ex);
            }
        }));

        cards[1].addMouseListener(createMouseListener(() -> {
            try {
                new BookingForm();
            } catch (Exception ex) {
                showError("Error opening booking form", ex);
            }
        }));

        cards[2].addMouseListener(createMouseListener(() -> {
            try {
                String fileName = "bookings_" + System.currentTimeMillis() + ".csv";
                boolean success = BookingExporter.exportBookingsToFile(fileName);
                if (success) {
                    showSuccess("Bookings exported to " + fileName);
                } else {
                    showError("Export failed", new Exception("Export operation failed"));
                }
            } catch (Exception ex) {
                showError("Error exporting bookings", ex);
            }
        }));

        cards[3].addMouseListener(createMouseListener(() -> {
            dispose();
            new LoginForm();
        }));

        mainPanel.add(welcomePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createCardButton(String text, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(200, 150));
        card.setBackground(color);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel iconLabel = new JLabel(new ImageIcon()); // Add icons if available
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Arial", Font.BOLD, 18));
        textLabel.setForeground(Color.WHITE);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(iconLabel, BorderLayout.CENTER);
        card.add(textLabel, BorderLayout.SOUTH);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return card;
    }

    private MouseAdapter createMouseListener(Runnable action) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JPanel panel = (JPanel) e.getSource();
                panel.setBackground(panel.getBackground().brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JPanel panel = (JPanel) e.getSource();
                panel.setBackground(panel.getBackground().darker());
            }
        };
    }

    private void showError(String message, Exception ex) {
        JOptionPane.showMessageDialog(this,
            message + ": " + ex.getMessage(),
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
