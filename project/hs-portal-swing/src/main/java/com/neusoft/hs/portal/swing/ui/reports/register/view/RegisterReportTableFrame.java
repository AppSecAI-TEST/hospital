package com.neusoft.hs.portal.swing.ui.reports.register.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

import javax.swing.*;
import java.awt.*;

@Component
public class RegisterReportTableFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 300;

    private RegisterReportTablePanel tablePanel;

    @Autowired
    public RegisterReportTableFrame(RegisterReportTablePanel tablePanel) {
        this.tablePanel = tablePanel;
        setFrameUp();
        initComponents();
    }

    private void setFrameUp() {
        setTitle(ConstMessagesEN.Labels.PAYMENT_METHODS);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        add(tablePanel, BorderLayout.CENTER);
    }

    public RegisterReportTablePanel getTablePanel() {
        return tablePanel;
    }
}
