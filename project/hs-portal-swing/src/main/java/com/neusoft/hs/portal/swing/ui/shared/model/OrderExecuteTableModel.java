package com.neusoft.hs.portal.swing.ui.shared.model;

import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

@Component
public class OrderExecuteTableModel extends DefaultTableModel<OrderExecute> {

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		OrderExecute orderExecute = entities.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return orderExecute.getId();
		case 1:
			return orderExecute.getVisit().getName();
		case 2:
			return orderExecute.getType();
		case 3:
			return orderExecute.getState();
		case 4:
			return orderExecute.getExecuteDept().getName();
		case 5:
			return DateFormatter
					.formatDateTime(orderExecute.getPlanStartDate());
		default:
			return "";
		}
	}

	public OrderExecute getOrderExecute(int rowIndex) {
		return entities.get(rowIndex);
	}

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesEN.Labels.ID,
				ConstMessagesEN.Labels.VisitName, ConstMessagesEN.Labels.Type,
				ConstMessagesEN.Labels.State,
				ConstMessagesEN.Labels.ExecuteDept,
				ConstMessagesEN.Labels.PlanStartDate };
	}
}
