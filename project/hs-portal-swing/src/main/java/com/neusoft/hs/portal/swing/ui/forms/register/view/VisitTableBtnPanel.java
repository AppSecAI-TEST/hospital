package com.neusoft.hs.portal.swing.ui.forms.register.view;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class VisitTableBtnPanel extends JPanel {

	private JButton addBtn;
	private JButton closeBtn;

	public VisitTableBtnPanel() {
		initComponents();
	}

	private void initComponents() {
		addBtn = new JButton(ConstMessagesCN.Labels.ADD_BTN);
		add(addBtn);

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		add(closeBtn);

	}

	public JButton getAddBtn() {
		return addBtn;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}
}
