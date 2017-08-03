package com.neusoft.hs.portal.swing.ui.reports.visit.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.ui.reports.reports.view.VisitComboBoxFrame;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitLogTableModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class VisitLogFrame extends VisitComboBoxFrame {

	VisitLogTableModel visitLogTableModel;
	JTable table;

	JLabel stateJL;

	@Autowired
	public VisitLogFrame() {
		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.VisitLog);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		JPanel workspacePanel = new JPanel(new BorderLayout());

		this.visitLogTableModel = new VisitLogTableModel();
		table = new JTable(this.visitLogTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		workspacePanel.add(paneWithTable, BorderLayout.CENTER);

		JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel visitLbl = new JLabel(ConstMessagesCN.Labels.Visit);

		this.visitComboBoxModel = new VisitComboBoxModel();
		visitCB = new JComboBox<>(visitComboBoxModel);

		operationPanel.add(visitLbl);
		operationPanel.add(visitCB);

		stateJL = new JLabel();
		operationPanel.add(stateJL);

		nextPageBtn = new JButton(ConstMessagesCN.Labels.NextPage_BTN);
		operationPanel.add(nextPageBtn);

		workspacePanel.add(operationPanel, BorderLayout.NORTH);

		add(workspacePanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public VisitLogTableModel getVisitLogTableModel() {
		return visitLogTableModel;
	}

	public JLabel getStateJL() {
		return stateJL;
	}
}
