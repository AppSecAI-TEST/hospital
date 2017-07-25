package com.neusoft.hs.portal.swing.ui.forms.order.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class CreateOrderFrame extends JFrame {

	private OrderListPanel orderListPanel;

	private CreateOrderPanel createOrderPanel;

	JButton confirmBtn;

	JButton closeBtn;

	private static final int DEFAULT_WIDTH = 1000;

	private static final int DEFAULT_HEIGHT = 600;

	@Autowired
	public CreateOrderFrame(OrderListPanel orderListPanel,
			CreateOrderPanel createOrderPanel) {
		this.orderListPanel = orderListPanel;
		this.createOrderPanel = createOrderPanel;

		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.CreateOrder);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {

		setLayout(new BorderLayout());

		JPanel workspacePanel = new JPanel(new GridLayout(2, 1));

		workspacePanel.add(orderListPanel);
		workspacePanel.add(createOrderPanel);

		add(workspacePanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		confirmBtn = new JButton(ConstMessagesCN.Labels.CONFIRM_BTN);
		buttonPanel.add(confirmBtn);

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public Date getPlanStartDate() throws HsException {
		return this.createOrderPanel.planStartDateSD.getDate();

	}

	public Integer getExecuteDay() {
		Integer executeDay = this.createOrderPanel.executeDaySN.getInteger();
		if (executeDay == null || executeDay == 0) {
			return null;
		} else {
			return executeDay;
		}
	}

	public Integer getCount() {
		Integer count = this.createOrderPanel.countSN.getInteger();
		if (count == null || count == 0) {
			return null;
		} else {
			return count;
		}
	}

	public OrderListPanel getOrderListPanel() {
		return orderListPanel;
	}

	public CreateOrderPanel getCreateOrderPanel() {
		return createOrderPanel;
	}

	public JButton getConfirmBtn() {
		return this.confirmBtn;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}

}
