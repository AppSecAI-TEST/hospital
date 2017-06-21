package com.neusoft.hs.portal.swing.ui.forms.order.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

@Component
public class OrderExecuteFinishFrame extends JFrame {

	private OrderExecuteFinishListPanel orderExecuteFinishListPanel;

	JButton confirmBtn;

	private static final int DEFAULT_WIDTH = 800;

	private static final int DEFAULT_HEIGHT = 300;

	@Autowired
	public OrderExecuteFinishFrame(OrderExecuteFinishListPanel orderExecuteFinishListPanel) {
		this.orderExecuteFinishListPanel = orderExecuteFinishListPanel;

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
		setLayout(new BorderLayout());

		add(orderExecuteFinishListPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		confirmBtn = new JButton(ConstMessagesEN.Labels.CONFIRM_BTN);
		buttonPanel.add(confirmBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public JButton getConfirmBtn() {
		return this.confirmBtn;
	}
	
	public OrderExecute getSelectedOrderExecute() throws UIException {
		return orderExecuteFinishListPanel.getSelectedOrderExecute();
	}

}
