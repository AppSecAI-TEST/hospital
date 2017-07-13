package com.neusoft.hs.portal.swing.ui.forms.outpatientdept.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.ui.shared.model.VoucherTableModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class OutPatientDeptFrame extends JFrame {

	private VoucherTableModel voucherTableModel;

	private JTable table;

	private JButton nextBtn;

	private JButton closeBtn;

	private static final int DEFAULT_WIDTH = 800;

	private static final int DEFAULT_HEIGHT = 300;

	@Autowired
	public OutPatientDeptFrame() {
		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.OutPatientPlanRecord);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {

		this.voucherTableModel = new VoucherTableModel();
		table = new JTable(this.voucherTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		add(paneWithTable, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		nextBtn = new JButton(ConstMessagesCN.Labels.NEXT_BTN);
		buttonPanel.add(nextBtn);

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public VoucherTableModel getVoucherTableModel() {
		return voucherTableModel;
	}

	public JTable getTable() {
		return table;
	}

	public JButton getNextBtn() {
		return nextBtn;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}
}