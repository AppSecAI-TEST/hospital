package com.neusoft.hs.portal.swing.ui.reports.reports.view;

import org.springframework.stereotype.Component;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.LookAndFeelUtils;

import javax.swing.*;
import java.awt.*;

@Component
public class ReportsFrame extends JFrame {

	private JButton chargeRecordReportBtn;

	private JButton orderBtn;

	private JButton orderExecuteBtn;

	private JButton treatmentBtn;

	private JButton visitLogBtn;

	private JButton runTestBtn;

	public ReportsFrame() {
		setFrameUp();
		initComponents();
		pack();
	}

	private void setFrameUp() {
		getRootPane().setBorder(Borders.createEmptyBorder());
		setTitle(ConstMessagesCN.Labels.REPORTS);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		LookAndFeelUtils.setWindowsLookAndFeel();
		setLayout(new GridLayout(6, 1, 20, 20));
	}

	private void initComponents() {
		visitLogBtn = new JButton(ConstMessagesCN.Labels.VisitLog);
		chargeRecordReportBtn = new JButton(ConstMessagesCN.Labels.ChargeRecord);
		orderBtn = new JButton(ConstMessagesCN.Labels.OrderList);
		orderExecuteBtn = new JButton(ConstMessagesCN.Labels.OrderExecuteList);
		treatmentBtn = new JButton(ConstMessagesCN.Labels.Treatment);
		runTestBtn = new JButton(ConstMessagesCN.Labels.RunTest);

		add(visitLogBtn);
		add(chargeRecordReportBtn);
		add(orderBtn);
		add(orderExecuteBtn);
		add(treatmentBtn);

		add(runTestBtn);
	}

	public JButton getVisitLogBtn() {
		return visitLogBtn;
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

	public JButton getTreatmentBtn() {
		return treatmentBtn;
	}

	public void setTreatmentBtn(JButton treatmentBtn) {
		this.treatmentBtn = treatmentBtn;
	}

	public JButton getRunTestBtn() {
		return runTestBtn;
	}
}
