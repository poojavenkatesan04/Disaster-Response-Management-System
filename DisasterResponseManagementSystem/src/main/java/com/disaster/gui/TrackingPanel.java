package com.disaster.gui;

import com.disaster.model.*;
import com.disaster.service.DisasterManagementService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TrackingPanel extends JPanel {
    final DisasterManagementService s;
    final Runnable r;
    final DefaultTableModel m = new DefaultTableModel();
    final JTable table = new JTable(m);

    public TrackingPanel(DisasterManagementService x, Runnable y) {
        s = x;
        r = y;
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(18, 20, 18, 20));
        setBackground(UITheme.BG);

        JPanel p = new JPanel();
        JButton in = new JButton("Mark In Progress");
        JButton res = new JButton("Resolve");
        JButton track = new JButton("Track by Report ID");
        JButton log = new JButton("Activity / Notifications");
        p.setOpaque(false);
        UITheme.styleSecondaryButton(in);
        UITheme.styleButton(res, UITheme.RED);
        UITheme.styleSecondaryButton(track);
        UITheme.styleSecondaryButton(log);
        p.add(in);
        p.add(res);
        p.add(track);
        p.add(log);
        add(p, BorderLayout.NORTH);
        UITheme.styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        in.addActionListener(e -> change(true));
        res.addActionListener(e -> change(false));
        track.addActionListener(e -> trackById());
        log.addActionListener(e -> showLog());

        refreshTable();
    }

    private void refreshTable() {
        m.setRowCount(0);
        m.setColumnIdentifiers(new Object[]{
                "ID", "Location", "Priority", "Status", "Volunteer", "Team", "Vehicle", "Shelter"
        });
        for (EmergencyReport z : s.reports()) {
            m.addRow(new Object[]{
                    z.getId(), z.getLocation(), z.getPriority(), z.getStatus(), z.getVolunteer(),
                    z.getTeam(), z.getVehicle(), z.getShelter()
            });
        }
    }

    void change(boolean in) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an emergency");
            return;
        }
        try {
            String id = m.getValueAt(table.convertRowIndexToModel(row), 0).toString();
            if (in) {
                s.progress(id);
            } else {
                s.resolve(id);
            }
            r.run();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void trackById() {
        String id = JOptionPane.showInputDialog(this, "Enter your Report ID:", "Track Emergency", JOptionPane.QUESTION_MESSAGE);
        if (id == null || id.isBlank()) {
            return;
        }

        String wanted = id.trim();
        for (EmergencyReport report : s.reports()) {
            if (report.getId().equalsIgnoreCase(wanted)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Report ID: " + report.getId() + "\n"
                                + "Location: " + report.getLocation() + "\n"
                                + "Emergency: " + report.getDisasterType() + "\n"
                                + "Priority: " + report.getPriority() + "\n"
                                + "People affected: " + report.getPeopleAffected() + "\n"
                                + "Current status: " + report.getStatus(),
                        "Emergency Status",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "No emergency was found with Report ID: " + wanted,
                "Report Not Found",
                JOptionPane.WARNING_MESSAGE
        );
    }

    void showLog() {
        StringBuilder b = new StringBuilder("ACTIVITY LOG\n\n");
        for (String x : s.log()) {
            b.append(x).append("\n");
        }
        b.append("\nNOTIFICATIONS\n");
        for (String x : s.notifications()) {
            b.append(x).append("\n");
        }
        JTextArea a = new JTextArea(b.toString());
        a.setEditable(false);
        a.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, new JScrollPane(a), "Activity Log", JOptionPane.INFORMATION_MESSAGE);
    }
}
