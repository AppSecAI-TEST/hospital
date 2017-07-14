package com.neusoft.hs.portal.swing.ui.forms.order.view;

import javax.swing.JFrame;

import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.order.OrderExecute;

@Component
public class OrderExecuteOpenFrame extends JFrame {

	private OrderExecute orderExecute;

	public void init(OrderExecute orderExecute) {
		this.orderExecute = orderExecute;
	}
}
