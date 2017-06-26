package com.neusoft.hs.portal.swing.ui.reports.order.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class OrderExecuteFrame extends JFrame {

	JComboBox<Visit> visitCB;
	VisitComboBoxModel visitComboBoxModel;

	private OrderExecuteAdminListPanel orderExecuteAdminListPanel;

	private static final int DEFAULT_WIDTH = 800;

	private static final int DEFAULT_HEIGHT = 300;

	@Autowired
	public OrderExecuteFrame(VisitComboBoxModel visitComboBoxModel,
			OrderExecuteAdminListPanel orderExecuteFinishListPanel) {
		
		this.visitComboBoxModel = visitComboBoxModel;
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

		JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel visitLbl = new JLabel(ConstMessagesCN.Labels.Visit);
		visitCB = new JComboBox<>(visitComboBoxModel);

		operationPanel.add(visitLbl);
		operationPanel.add(visitCB);

		add(operationPanel, BorderLayout.NORTH);

		add(orderExecuteAdminListPanel, BorderLayout.CENTER);
	}

	public JComboBox<Visit> getVisitCB() {
		return visitCB;
	}
}
