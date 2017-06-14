package com.neusoft.hs.portal.swing.ui.forms.register.view.modal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

import javax.swing.*;
import java.awt.*;

@Component
public class AddVisitFrame extends JDialog {

    private VisitFormPanel formPanel;
    private VisitFormBtnPanel formBtnPanel;

    @Autowired
    public AddVisitFrame(VisitFormPanel formPanel, VisitFormBtnPanel formBtnPanel) {
        this.formPanel = formPanel;
        this.formBtnPanel = formBtnPanel;
        setFrameUp();
        initComponents();
        pack();
    }

    private void setFrameUp() {
        setTitle(ConstMessagesEN.DialogTitles.Register_MODAL);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void initComponents() {
        add(formPanel, BorderLayout.CENTER);
        add(formBtnPanel, BorderLayout.SOUTH);
    }

    public VisitFormPanel getFormPanel() {
        return formPanel;
    }

    public VisitFormBtnPanel getFormBtnPanel() {
        return formBtnPanel;
    }
}
