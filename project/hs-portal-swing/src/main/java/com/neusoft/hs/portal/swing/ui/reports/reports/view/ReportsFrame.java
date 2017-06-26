package com.neusoft.hs.portal.swing.ui.reports.reports.view;

import org.springframework.stereotype.Component;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;
import com.neusoft.hs.portal.swing.util.LookAndFeelUtils;

import javax.swing.*;
import java.awt.*;

@Component
public class ReportsFrame extends JFrame {

	private JButton chargeRecordReportBtn;

	private JButton orderBtn;

	private JButton orderExecuteBtn;

	public ReportsFrame() {
		setFrameUp();
		initComponents();
		pack();
	}

	private void setFrameUp() {
		getRootPane().setBorder(Borders.createEmptyBorder());
		setTitle(ConstMessagesEN.Labels.REPORTS);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		LookAndFeelUtils.setWindowsLookAndFeel();
		setLayout(new GridLayout(3, 1, 20, 20));
	}

	private void initComponents() {
		chargeRecordReportBtn = new JButton(ConstMessagesEN.Labels.ChargeRecord);
		orderBtn = new JButton(ConstMessagesEN.Labels.OrderList);
		orderExecuteBtn = new JButton(ConstMessagesEN.Labels.OrderExecuteList);

		add(chargeRecordReportBtn);
		add(orderBtn);
		add(orderExecuteBtn);
	}

	public JButton getChargeRecordReportBtn() {
		return chargeRecordReportBtn;
	}

	public JButton getOrderBtn() {
		return orderBtn;
	}

	public JButton getOrderExecuteBtn() {
		return orderExecuteBtn;
	}

}
