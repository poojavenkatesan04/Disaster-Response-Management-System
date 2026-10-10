package com.disaster.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;

public final class UITheme {
    public static final Color NAVY = new Color(28, 43, 58);
    public static final Color NAVY_LIGHT = new Color(43, 61, 79);
    public static final Color BLUE = new Color(42, 111, 151);
    public static final Color BG = new Color(244, 246, 248);
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT = new Color(39, 48, 56);
    public static final Color MUTED = new Color(104, 115, 124);
    public static final Color BORDER = new Color(218, 223, 227);
    public static final Color RED = new Color(178, 54, 54);

    private UITheme() {}

    public static void install() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        UIManager.put("Panel.background", BG);
        UIManager.put("Viewport.background", CARD);
        UIManager.put("Label.foreground", TEXT);
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 12));
        UIManager.put("TabbedPane.font", new Font("Segoe UI", Font.BOLD, 13));
    }

    public static void styleButton(AbstractButton b, Color color) {
        // Use the basic Swing button UI so the background/foreground are
        // consistently respected across Windows Look & Feel variants.
        b.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(true);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker()),
                new EmptyBorder(8, 14, 8, 14)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleSecondaryButton(AbstractButton b) {
        b.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(true);
        b.setBackground(Color.WHITE);
        b.setForeground(TEXT);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(8, 14, 8, 14)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(224, 236, 244));
        table.setSelectionForeground(TEXT);
        table.setFillsViewportHeight(true);
        JTableHeader h = table.getTableHeader();
        h.setPreferredSize(new Dimension(h.getPreferredSize().width, 36));
        h.setReorderingAllowed(false);
    }

    public static JPanel cardLayoutPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBackground(CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(16, 16, 16, 16)));
        return p;
    }
}
