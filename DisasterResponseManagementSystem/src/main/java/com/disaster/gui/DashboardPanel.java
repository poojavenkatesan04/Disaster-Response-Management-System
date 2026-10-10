package com.disaster.gui;

import com.disaster.model.EmergencyStatus;
import com.disaster.service.DisasterManagementService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private final DisasterManagementService s;
    private final Runnable refresh;

    public DashboardPanel(DisasterManagementService s, Runnable r) {
        this.s = s;
        this.refresh = r;
        setLayout(new BorderLayout(0, 18));
        setBorder(new EmptyBorder(22, 24, 22, 24));
        setBackground(UITheme.BG);

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);
        JLabel title = new JLabel("Response Overview");
        title.setFont(new Font("Segoe UI", Font.BOLD, 25));
        JLabel sub = new JLabel("Current status of reported emergencies and response resources");
        sub.setForeground(UITheme.MUTED);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(title); text.add(Box.createVerticalStrut(4)); text.add(sub);
        heading.add(text, BorderLayout.WEST);
        JButton refreshButton = new JButton("Refresh");
        UITheme.styleSecondaryButton(refreshButton);
        refreshButton.addActionListener(e -> refresh.run());
        heading.add(refreshButton, BorderLayout.EAST);
        add(heading, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);
        JPanel cards = new JPanel(new GridLayout(2, 4, 12, 12));
        cards.setOpaque(false);
        addCard(cards, "Total Emergencies", String.valueOf(s.reports().size()), UITheme.BLUE);
        addCard(cards, "Critical", String.valueOf(s.reports().stream().filter(x -> x.getPriority().toString().equals("CRITICAL")).count()), UITheme.RED);
        addCard(cards, "Reported", String.valueOf(s.count(EmergencyStatus.REPORTED)), new Color(125, 91, 36));
        addCard(cards, "Assigned", String.valueOf(s.count(EmergencyStatus.ASSIGNED)), new Color(78, 102, 125));
        addCard(cards, "In Progress", String.valueOf(s.count(EmergencyStatus.IN_PROGRESS)), new Color(61, 119, 93));
        addCard(cards, "Resolved", String.valueOf(s.count(EmergencyStatus.RESOLVED)), new Color(72, 112, 82));
        addCard(cards, "Available Volunteers", String.valueOf(s.getVolunteers().stream().filter(x -> x.isAvailable()).count()), UITheme.BLUE);
        addCard(cards, "Available Vehicles", String.valueOf(s.getVehicles().stream().filter(x -> x.isAvailable()).count()), UITheme.BLUE);
        content.add(cards, BorderLayout.NORTH);

        JPanel queue = UITheme.cardLayoutPanel();
        JLabel q1 = new JLabel("Priority Queue");
        q1.setFont(new Font("Segoe UI", Font.BOLD, 15));
        String highest = s.next() == null ? "No waiting emergencies" :
                s.next().getId() + "  •  " + s.next().getPriority();
        JLabel q2 = new JLabel("Next response: " + highest);
        q2.setForeground(UITheme.MUTED);
        queue.add(q1, BorderLayout.NORTH);
        queue.add(q2, BorderLayout.CENTER);
        content.add(queue, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    private void addCard(JPanel panel, String title, String value, Color accent) {
        JPanel card = UITheme.cardLayoutPanel();
        JPanel stripe = new JPanel(); stripe.setBackground(accent); stripe.setPreferredSize(new Dimension(5, 1));
        card.add(stripe, BorderLayout.WEST);
        JPanel text = new JPanel(new BorderLayout()); text.setOpaque(false);
        JLabel t = new JLabel(title); t.setForeground(UITheme.MUTED); t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel v = new JLabel(value); v.setForeground(UITheme.TEXT); v.setFont(new Font("Segoe UI", Font.BOLD, 25));
        text.add(t, BorderLayout.NORTH); text.add(v, BorderLayout.CENTER);
        card.add(text, BorderLayout.CENTER);
        panel.add(card);
    }
}
