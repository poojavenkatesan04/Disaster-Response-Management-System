package com.disaster.gui;

import com.disaster.model.*;
import com.disaster.service.DisasterManagementService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EmergencyPanel extends JPanel {
    final DisasterManagementService s;
    final Runnable refresh;
    final DefaultTableModel m = new DefaultTableModel();
    final JTable emergencyTable = new JTable(m);
    final JTextField search = new JTextField(18);
    final JComboBox<String> statusFilter = new JComboBox<>(new String[]{"All Statuses", "REPORTED", "ASSIGNED", "IN_PROGRESS", "RESOLVED"});
    final JComboBox<String> priorityFilter = new JComboBox<>(new String[]{"All Priorities", "CRITICAL", "HIGH", "MEDIUM", "LOW"});

    public EmergencyPanel(DisasterManagementService x, Runnable r) {
        s = x;
        refresh = r;
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(18, 20, 18, 20));
        setBackground(UITheme.BG);

        JButton add = new JButton("Create Emergency");
        JButton proc = new JButton("Process Selected / Assign Resources");
        JButton clear = new JButton("Clear Filters");
        add.addActionListener(e -> create());
        proc.addActionListener(e -> process());
        clear.addActionListener(e -> {
            search.setText("");
            statusFilter.setSelectedIndex(0);
            priorityFilter.setSelectedIndex(0);
            refreshTable();
        });

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        UITheme.styleButton(add, UITheme.BLUE);
        UITheme.styleSecondaryButton(proc);
        UITheme.styleSecondaryButton(clear);
        actions.setOpaque(false);
        actions.setBorder(new EmptyBorder(0, 0, 4, 0));
        actions.add(add);
        actions.add(proc);
        actions.add(new JLabel("Search:"));
        actions.add(search);
        actions.add(new JLabel("Status:"));
        actions.add(statusFilter);
        actions.add(new JLabel("Priority:"));
        actions.add(priorityFilter);
        actions.add(clear);
        add(actions, BorderLayout.NORTH);

        emergencyTable.setAutoCreateRowSorter(true);
        UITheme.styleTable(emergencyTable);
        add(new JScrollPane(emergencyTable), BorderLayout.CENTER);

        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { refreshTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { refreshTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { refreshTable(); }
        });
        statusFilter.addActionListener(e -> refreshTable());
        priorityFilter.addActionListener(e -> refreshTable());

        refreshTable();
    }

    private void refreshTable() {
        m.setRowCount(0);
        m.setColumnIdentifiers(new Object[]{
                "ID", "Reporter", "Location", "Type", "Affected", "Priority", "Status", "Volunteer", "Team", "Vehicle", "Shelter"
        });

        String text = search.getText().trim().toLowerCase();
        String status = statusFilter.getSelectedItem().toString();
        String priority = priorityFilter.getSelectedItem().toString();

        for (EmergencyReport r : s.reports()) {
            boolean matchesText = text.isEmpty()
                    || r.getId().toLowerCase().contains(text)
                    || r.getReporterName().toLowerCase().contains(text)
                    || r.getLocation().toLowerCase().contains(text)
                    || r.getDisasterType().toString().toLowerCase().contains(text);
            boolean matchesStatus = status.equals("All Statuses") || r.getStatus().toString().equals(status);
            boolean matchesPriority = priority.equals("All Priorities") || r.getPriority().toString().equals(priority);

            if (matchesText && matchesStatus && matchesPriority) {
                m.addRow(new Object[]{
                        r.getId(), r.getReporterName(), r.getLocation(), r.getDisasterType(),
                        r.getPeopleAffected(), r.getPriority(), r.getStatus(), r.getVolunteer(),
                        r.getTeam(), r.getVehicle(), r.getShelter()
                });
            }
        }
    }

    void create() {
        JTextField n = new JTextField();
        JTextField l = new JTextField();
        JTextField a = new JTextField();
        JComboBox<DisasterType> d = new JComboBox<>(DisasterType.values());
        JCheckBox med = new JCheckBox("Medical");
        JCheckBox v = new JCheckBox("Children/Elderly");
        JCheckBox tr = new JCheckBox("Trapped");
        JPanel p = new JPanel(new GridLayout(0, 2));
        p.add(new JLabel("Reporter"));
        p.add(n);
        p.add(new JLabel("Location"));
        p.add(l);
        p.add(new JLabel("Disaster"));
        p.add(d);
        p.add(new JLabel("People affected"));
        p.add(a);
        p.add(med);
        p.add(v);
        p.add(tr);
        p.add(new JLabel(""));
        if (JOptionPane.showConfirmDialog(this, p, "Emergency Report", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                EmergencyReport z = s.createReport(n.getText(), l.getText(), (DisasterType) d.getSelectedItem(),
                        Integer.parseInt(a.getText()), med.isSelected(), v.isSelected(), tr.isSelected());
                JOptionPane.showMessageDialog(this, "Created " + z.getId() + " | Priority: " + z.getPriority());
                refresh.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    void process() {
        int row = emergencyTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an emergency");
            return;
        }
        int modelRow = emergencyTable.convertRowIndexToModel(row);
        try {
            s.process(m.getValueAt(modelRow, 0).toString());
            JOptionPane.showMessageDialog(this, "Resources assigned and notifications queued.");
            refresh.run();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Assignment", JOptionPane.ERROR_MESSAGE);
        }
    }
}
