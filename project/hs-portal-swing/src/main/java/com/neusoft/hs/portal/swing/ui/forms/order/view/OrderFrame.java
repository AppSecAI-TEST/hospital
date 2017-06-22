package com.neusoft.hs.portal.swing.ui.forms.order.view;

import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

@Component
public class OrderFrame extends JFrame {

	private OrderListPanel orderListPanel;

	private CreateOrderPanel createOrderPanel;

	private static final int DEFAULT_WIDTH = 1000;

	private static final int DEFAULT_HEIGHT = 600;

	@Autowired
	public OrderFrame(OrderListPanel orderListPanel,
			CreateOrderPanel createOrderPanel) {
		this.orderListPanel = orderListPanel;
		this.createOrderPanel = createOrderPanel;

		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesEN.Labels.Order);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {
		setLayout(new GridLayout(2, 1));

		add(orderListPanel);
		add(createOrderPanel);
	}

	public JButton getConfirmBtn() {
		return this.createOrderPanel.confirmBtn;
	}

	public Date getPlanStartDate() throws HsException {
		return (Date) this.createOrderPanel.planStartDateSD.getValue();

	}

	public Integer getCount() {
		Integer count = (Integer) this.createOrderPanel.countSN.getValue();
		if (count == null || count == 0) {
			return null;
		} else {
			return count;
		}
	}

}
