package com.neusoft.hs.portal.swing.ui.forms.order.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.ui.shared.model.OrderTableModel;
import com.neusoft.hs.portal.swing.util.Borders;

@Component
public class OrderListPanel extends JPanel {

	private OrderTableModel orderTableModel;

	protected JTable table;

	@Autowired
	public OrderListPanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(Borders.createEmptyBorder());
		setLayout(new BorderLayout());
	}

	private void initComponents() {

		this.orderTableModel = new OrderTableModel();
		table = new JTable(this.orderTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		add(paneWithTable, BorderLayout.CENTER);
	}

	public OrderTableModel getOrderTableModel() {
		return orderTableModel;
	}
}
