package com.neusoft.hs.portal.swing.ui.forms.register.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.shared.model.StringComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitLogTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitTableModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class VisitTablePanel extends JPanel {

	JComboBox<String> visitStateCB;
	private StringComboBoxModel visitStateComboBoxModel;

	private VisitTableModel tableModel;

	private JTable table;

	@Autowired
	VisitTablePanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setLayout(new BorderLayout());
	}

	private void initComponents() {

		setLayout(new BorderLayout());

		JPanel workspacePanel = new JPanel(new BorderLayout());

		this.tableModel = new VisitTableModel();
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);
		add(paneWithTable, BorderLayout.CENTER);

		workspacePanel.add(paneWithTable, BorderLayout.CENTER);

		JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel stateLbl = new JLabel(ConstMessagesCN.Labels.State);

		this.visitStateComboBoxModel = new StringComboBoxModel();
		visitStateCB = new JComboBox<>(visitStateComboBoxModel);

		operationPanel.add(stateLbl);
		operationPanel.add(visitStateCB);

		workspacePanel.add(operationPanel, BorderLayout.NORTH);

		add(workspacePanel, BorderLayout.CENTER);
	}

	public JTable getTable() {
		return table;
	}

	public VisitTableModel getTableModel() {
		return tableModel;
	}

	public StringComboBoxModel getVisitStateComboBoxModel() {
		return visitStateComboBoxModel;
	}

	public JComboBox<String> getVisitStateCB() {
		return visitStateCB;
	}

}
