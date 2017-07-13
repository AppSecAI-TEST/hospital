package com.neusoft.hs.portal.swing.ui.reports.outpatientoffice.view;

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

import com.neusoft.hs.portal.swing.ui.shared.model.OutPatientPlanRecordTableModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class OutPatientPlanRecordFrame extends JFrame {

	private OutPatientPlanRecordTableModel recordTableModel;

	private JTable table;

	private JButton addBtn;

	private JButton closeBtn;

	private static final int DEFAULT_WIDTH = 800;

	private static final int DEFAULT_HEIGHT = 300;

	@Autowired
	public OutPatientPlanRecordFrame() {
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

		this.recordTableModel = new OutPatientPlanRecordTableModel();
		table = new JTable(this.recordTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		add(paneWithTable, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		addBtn = new JButton(ConstMessagesCN.Labels.ADD_BTN);
		buttonPanel.add(addBtn);

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public OutPatientPlanRecordTableModel getRecordTableModel() {
		return recordTableModel;
	}

	public JTable getTable() {
		return table;
	}

	public JButton getAddBtn() {
		return addBtn;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}
}
