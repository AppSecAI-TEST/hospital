package com.neusoft.hs.portal.swing.ui.forms.register.view.modal;

import org.springframework.stereotype.Component;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

import javax.swing.*;

@Component
public class VisitFormBtnPanel extends JPanel {

    private JButton saveBtn;
    private JButton cancelBtn;

    public VisitFormBtnPanel() {
        initComponents();
    }

    private void initComponents() {
        saveBtn = new JButton(ConstMessagesCN.Labels.ADD_BTN);
        add(saveBtn);

        cancelBtn = new JButton(ConstMessagesCN.Labels.CANCEL_BTN);
        add(cancelBtn);
    }

    public JButton getSaveBtn() {
        return saveBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }

}
