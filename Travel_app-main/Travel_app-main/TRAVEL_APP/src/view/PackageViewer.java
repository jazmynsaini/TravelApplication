package view;

import dao.PackageDAO;
import model.TravelPackage;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PackageViewer extends JFrame {
    public PackageViewer() {
        setTitle("Available Travel Packages");
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(new JLabel("Available Packages", SwingConstants.CENTER));

        // Create table model
        String[] columns = {"ID", "Package Name", "Price", "Description"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(66, 134, 244));
        table.getTableHeader().setForeground(Color.WHITE);

        try {
            List<TravelPackage> list = new PackageDAO().getAllPackages();
            for (TravelPackage p : list) {
                model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    String.format("₹%.2f", p.getPrice()),
                    p.getDescription()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading packages: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(mainPanel);
        pack();
        setVisible(true);
    }
}
