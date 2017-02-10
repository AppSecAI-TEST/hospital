package com.neusoft.hs.portal.swing.ui.forms.register.view;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

@Component
public class VisitTableBtnPanel extends JPanel {

	private JButton addBtn;

	public VisitTableBtnPanel() {
		initComponents();
	}

	private void initComponents() {
		addBtn = new JButton(ConstMessagesEN.Labels.ADD_BTN);
		add(addBtn);

	}

	public JButton getAddBtn() {
		return addBtn;
	}

}
