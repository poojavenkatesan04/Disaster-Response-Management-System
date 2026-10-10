package com.disaster;

import com.disaster.gui.MainFrame;
import com.disaster.gui.UITheme;
import com.disaster.service.DisasterManagementService;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        UITheme.install();
        DisasterManagementService service = new DisasterManagementService();
        try {
            service.demo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new MainFrame(service).setVisible(true));
    }
}
