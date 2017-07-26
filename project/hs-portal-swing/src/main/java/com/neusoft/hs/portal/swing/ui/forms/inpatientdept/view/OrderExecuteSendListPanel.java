package com.neusoft.hs.portal.swing.ui.forms.inpatientdept.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderExecuteTableModel;
import com.neusoft.hs.portal.swing.util.Borders;

@Component
public class OrderExecuteSendListPanel extends JPanel {

	private OrderExecuteTableModel orderExecuteTableModel;

	protected JTable table;

	@Autowired
	public OrderExecuteSendListPanel() {
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
		
		JScrollPane paneWithTable = new JScrollPane(table);

		add(paneWithTable, BorderLayout.CENTER);
	}

	public List<OrderExecute> getSelectedOrderExecutes() throws UIException {
		int[] rows = this.table.getSelectedRows();
		if (rows == null || rows.length == 0) {
			throw new UIException("请选择要发送的医嘱执行条目");
		}
		List<OrderExecute> orderExecutes = new ArrayList<OrderExecute>();
		for (int row : rows) {
			orderExecutes.add(orderExecuteTableModel.getEntityByRow(row));
		}

		return orderExecutes;
	}

	public OrderExecuteTableModel getOrderExecuteTableModel() {
		return orderExecuteTableModel;
	}
}
