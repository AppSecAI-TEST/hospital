package com.neusoft.hs.portal.swing.ui.forms.register.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.ui.shared.model.VisitTableModel;

@Component
public class VisitTablePanel extends JPanel {

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
		this.tableModel = new VisitTableModel();
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);
		add(paneWithTable, BorderLayout.CENTER);
	}

	public JTable getTable() {
		return table;
	}

	public VisitTableModel getTableModel() {
		return tableModel;
	}
}
