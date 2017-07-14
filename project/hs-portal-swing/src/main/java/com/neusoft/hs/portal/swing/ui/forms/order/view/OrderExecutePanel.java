package com.neusoft.hs.portal.swing.ui.forms.order.view;

import java.util.Map;

import javax.swing.JPanel;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.portal.framework.exception.UIException;

public abstract class OrderExecutePanel extends JPanel {

	private OrderExecute orderExecute;

	public OrderExecutePanel(OrderExecute orderExecute) {
		this.orderExecute = orderExecute;
	}

	public OrderExecute getOrderExecute() {
		return orderExecute;
	}

	public abstract Map<String, Object> getParams() throws UIException;

}
