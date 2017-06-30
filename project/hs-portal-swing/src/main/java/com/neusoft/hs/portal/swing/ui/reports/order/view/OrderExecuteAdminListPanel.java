package com.neusoft.hs.portal.swing.ui.reports.order.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderExecuteTableModel;
import com.neusoft.hs.portal.swing.util.Borders;

@Component
public class OrderExecuteAdminListPanel extends JPanel {

	private OrderExecuteTableModel orderExecuteTableModel;

	protected JTable table;

	@Autowired
	public OrderExecuteAdminListPanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(Borders.createEmptyBorder());
		setLayout(new BorderLayout());
	}

	private void initComponents() {

		this.orderExecuteTableModel = new OrderExecuteTableModel();
		table = new JTable(this.orderExecuteTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		add(paneWithTable, BorderLayout.CENTER);
	}

	public OrderExecute getSelectedOrderExecute() throws UIException {
		if (this.table.getSelectedRow() == -1) {
			throw new UIException("请选择要完成的医嘱执行条目");
		}
		return orderExecuteTableModel.getEntityByRow(this.table
				.getSelectedRow());
	}

	public OrderExecuteTableModel getOrderExecuteTableModel() {
		return orderExecuteTableModel;
	}

}
