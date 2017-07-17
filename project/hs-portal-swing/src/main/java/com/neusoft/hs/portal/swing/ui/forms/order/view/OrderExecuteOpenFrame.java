package com.neusoft.hs.portal.swing.ui.forms.order.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.order.EnterHospitalIntoWardOrderExecute;
import com.neusoft.hs.domain.order.EnterHospitalSupplyCostOrderExecute;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class OrderExecuteOpenFrame extends JFrame {

	private JPanel workspacePanel;

	private OrderExecutePanel panel;

	private JButton finishBtn;

	private JButton closeBtn;

	private static final int DEFAULT_WIDTH = 800;

	private static final int DEFAULT_HEIGHT = 300;

	public void init(OrderExecute orderExecute) throws HsException {
		if (orderExecute instanceof EnterHospitalSupplyCostOrderExecute) {
			panel = new EnterHospitalSupplyCostOrderExecutePanel(orderExecute);
		} else if (orderExecute instanceof EnterHospitalIntoWardOrderExecute) {
			panel = new EnterHospitalIntoWardOrderExecutePanel(orderExecute);
		} else {
			throw new HsException("该执行条目可以直接完成");
		}
		this.workspacePanel.removeAll();
		this.workspacePanel.add(panel, BorderLayout.CENTER);
	}

	@Autowired
	public OrderExecuteOpenFrame() {
		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.FinishOrderExecute);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {

		this.setLayout(new BorderLayout());

		workspacePanel = new JPanel(new BorderLayout());

		add(workspacePanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		finishBtn = new JButton(ConstMessagesCN.Labels.FINISH_BTN);
		buttonPanel.add(finishBtn);

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public OrderExecutePanel getPanel() {
		return panel;
	}

	public JButton getFinishBtn() {
		return finishBtn;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}
}
