package com.neusoft.hs.portal.swing.ui.forms.inpatientdept.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTableModel;
import com.neusoft.hs.portal.swing.util.Borders;

@Component
public class OrderVerifyListPanel extends JPanel {

	private OrderTableModel orderTableModel;

	protected JTable table;

	@Autowired
	public OrderVerifyListPanel() {
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

		JScrollPane paneWithTable = new JScrollPane(table);

		add(paneWithTable, BorderLayout.CENTER);
	}

	public List<Order> getSelectedOrders() throws UIException {
		int[] rows = this.table.getSelectedRows();
		if (rows == null || rows.length == 0) {
			throw new UIException("请选择要核对的医嘱");
		}
		List<Order> orders = new ArrayList<Order>();
		for (int row : rows) {
			orders.add(orderTableModel.getEntityByRow(row));
		}

		return orders;
	}

	public OrderTableModel getOrderTableModel() {
		return orderTableModel;
	}

}
