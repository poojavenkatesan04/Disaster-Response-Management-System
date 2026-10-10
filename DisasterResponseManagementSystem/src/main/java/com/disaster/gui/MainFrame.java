package com.disaster.gui;

import com.disaster.model.DisasterType;
import com.disaster.model.EmergencyReport;
import com.disaster.service.DisasterManagementService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {
    private final DisasterManagementService s;
    private final JTabbedPane tabs = new JTabbedPane();

    public MainFrame(DisasterManagementService x) {
        s = x;
        UITheme.install();
        setTitle("Disaster Response Management System");
        setSize(1240, 780);
        setMinimumSize(new Dimension(1050, 680));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) { s.close(); }
        });
        build();
    }

    private void build() {
        tabs.removeAll();
        tabs.addTab("Dashboard", new DashboardPanel(s, this::refresh));
        tabs.addTab("Emergencies", new EmergencyPanel(s, this::refresh));
        tabs.addTab("Volunteers & Teams", new PeoplePanel(s, this::refresh));
        tabs.addTab("Resources", new ResourcePanel(s, this::refresh));
        tabs.addTab("Tracking & Logs", new TrackingPanel(s, this::refresh));
        tabs.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG);

        JPanel header = new JPanel(new BorderLayout(18, 0));
        header.setBackground(UITheme.NAVY);
        header.setBorder(new EmptyBorder(16, 24, 16, 24));

        JPanel branding = new JPanel();
        branding.setOpaque(false);
        branding.setLayout(new BoxLayout(branding, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("DISASTER RESPONSE");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        JLabel subtitle = new JLabel("Management System");
        subtitle.setForeground(new Color(204, 216, 226));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        branding.add(title);
        branding.add(Box.createVerticalStrut(2));
        branding.add(subtitle);
        header.add(branding, BorderLayout.WEST);

        JButton report = new JButton("Report Emergency");
        report.setFont(new Font("Segoe UI", Font.BOLD, 13));
        UITheme.styleButton(report, UITheme.RED);
        report.addActionListener(e -> reportEmergency());
        header.add(report, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);
        root.add(tabs, BorderLayout.CENTER);
        setContentPane(root);
    }

    private void reportEmergency() {
        JTextField reporter = new JTextField();
        JTextField location = new JTextField();
        JTextField affected = new JTextField();
        JComboBox<DisasterType> type = new JComboBox<>(DisasterType.values());
        JCheckBox medical = new JCheckBox("Medical assistance needed");
        JCheckBox vulnerable = new JCheckBox("Children / elderly affected");
        JCheckBox trapped = new JCheckBox("People trapped");

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(8, 8, 4, 8));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        String[] labels = {"Your name", "Location", "Emergency type", "People affected"};
        JComponent[] fields = {reporter, location, type, affected};
        for (int i = 0; i < labels.length; i++) {
            c.gridx = 0; c.gridy = i;
            form.add(new JLabel(labels[i]), c);
            c.gridx = 1; c.weightx = 1;
            form.add(fields[i], c);
        }
        c.gridx = 0; c.gridy = 4; c.gridwidth = 2; c.weightx = 1;
        form.add(medical, c);
        c.gridy = 5; form.add(vulnerable, c);
        c.gridy = 6; form.add(trapped, c);

        int result = JOptionPane.showConfirmDialog(this, form, "Report Emergency",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            EmergencyReport reportData = s.createReport(
                    reporter.getText().trim(), location.getText().trim(),
                    (DisasterType) type.getSelectedItem(),
                    Integer.parseInt(affected.getText().trim()),
                    medical.isSelected(), vulnerable.isSelected(), trapped.isSelected());

            JOptionPane.showMessageDialog(this,
                    "Emergency reported successfully.\n\n"
                            + "Report ID: " + reportData.getId() + "\n"
                            + "Priority: " + reportData.getPriority() + "\n"
                            + "Status: " + reportData.getStatus(),
                    "Report Received", JOptionPane.INFORMATION_MESSAGE);
            refresh();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "People affected must be a positive whole number.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Unable to Report",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refresh() {
        int selected = tabs.getSelectedIndex();
        build();
        tabs.setSelectedIndex(Math.max(0, Math.min(selected, tabs.getTabCount() - 1)));
        revalidate();
        repaint();
    }
}
