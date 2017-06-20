package com.neusoft.hs.portal.swing.ui.forms.register.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

import javax.swing.*;
import java.awt.*;

@Component
public class VisitTableFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 300;

    private VisitTablePanel tablePanel;
    private VisitTableBtnPanel tableBtnPanel;

    @Autowired
    public VisitTableFrame(VisitTableBtnPanel tableBtnPanel, VisitTablePanel tablePanel) {
        this.tablePanel = tablePanel;
        this.tableBtnPanel = tableBtnPanel;
        setFrameUp();
        initComponents();
    }

    private void setFrameUp() {
        setTitle(ConstMessagesEN.Labels.Register);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        add(tablePanel, BorderLayout.CENTER);
        add(tableBtnPanel, BorderLayout.SOUTH);
    }

    public VisitTableBtnPanel getTableBtnPanel() {
        return tableBtnPanel;
    }

    public VisitTablePanel getTablePanel() {
        return tablePanel;
    }
}
