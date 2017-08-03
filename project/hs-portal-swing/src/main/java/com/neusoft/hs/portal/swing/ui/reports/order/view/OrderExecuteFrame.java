package com.neusoft.hs.portal.swing.ui.reports.order.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.reports.reports.view.VisitComboBoxFrame;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderExecuteTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class OrderExecuteFrame extends VisitComboBoxFrame {

	private OrderExecuteAdminListPanel orderExecuteAdminListPanel;
	
	private static final int DEFAULT_WIDTH = 1000;

	private static final int DEFAULT_HEIGHT = 600;

	@Autowired
	public OrderExecuteFrame(
			OrderExecuteAdminListPanel orderExecuteFinishListPanel) {
		this.orderExecuteAdminListPanel = orderExecuteFinishListPanel;
		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.OrderExecuteList);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		
		JPanel workspacePanel = new JPanel(new BorderLayout());

		JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel visitLbl = new JLabel(ConstMessagesCN.Labels.Visit);

		this.visitComboBoxModel = new VisitComboBoxModel();
		visitCB = new JComboBox<>(visitComboBoxModel);

		operationPanel.add(visitLbl);
		operationPanel.add(visitCB);
		
		nextPageBtn = new JButton(ConstMessagesCN.Labels.NextPage_BTN);
		operationPanel.add(nextPageBtn);

		workspacePanel.add(operationPanel, BorderLayout.NORTH);

		workspacePanel.add(orderExecuteAdminListPanel, BorderLayout.CENTER);
		
		add(workspacePanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public JComboBox<Visit> getVisitCB() {
		return visitCB;
	}

	public VisitComboBoxModel getVisitComboBoxModel() {
		return visitComboBoxModel;
	}
	
	public OrderExecuteTableModel getOrderExecuteTableModel() {
		return this.orderExecuteAdminListPanel.getOrderExecuteTableModel();
	}
	
	public JButton getCloseBtn() {
		return closeBtn;
	}

}
