package com.neusoft.hs.portal.swing.ui.shared.model;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

public class OrderTableModel extends DefaultTableModel<Order> {

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Order order = entities.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return order.getId();
		case 1:
			return order.getVisitName();
		case 2:
			return order.getName();
		case 3:
			return order.getState();
		case 4:
			return order.getOrderType();
		case 5:
			return DateFormatter.formatDate(order.getCreateDate());
		default:
			return "";
		}
	}

	public Order getOrder(int rowIndex) {
		return entities.get(rowIndex);
	}

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesCN.Labels.ID,
				ConstMessagesCN.Labels.VisitName, ConstMessagesCN.Labels.Name,
				ConstMessagesCN.Labels.State, ConstMessagesCN.Labels.Type,
				ConstMessagesCN.Labels.CreateDate };
	}
}
