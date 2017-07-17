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

	private JButton medicalrecordBtn;

	private JButton visitLogBtn;

	private JButton createOutPatientPlanRecordBtn;

	private JButton runTestBtn;

	private JLabel tipLbl;

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
	}

	private void initComponents() {

		setLayout(new BorderLayout());

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(8, 2, 20, 20));

		visitLogBtn = new JButton(ConstMessagesCN.Labels.VisitLog);
		chargeRecordReportBtn = new JButton(ConstMessagesCN.Labels.ChargeRecord);
		orderBtn = new JButton(ConstMessagesCN.Labels.OrderList);
		orderExecuteBtn = new JButton(ConstMessagesCN.Labels.OrderExecuteList);
		treatmentBtn = new JButton(ConstMessagesCN.Labels.Treatment);
		medicalrecordBtn = new JButton(ConstMessagesCN.Labels.MedicalRecords);
		createOutPatientPlanRecordBtn = new JButton(
				ConstMessagesCN.Labels.CreateOutPatientPlanRecord);
		runTestBtn = new JButton(ConstMessagesCN.Labels.RunTest);

		menuPanel.add(visitLogBtn);
		menuPanel.add(chargeRecordReportBtn);
		menuPanel.add(orderBtn);
		menuPanel.add(orderExecuteBtn);
		menuPanel.add(treatmentBtn);
		menuPanel.add(medicalrecordBtn);
		menuPanel.add(createOutPatientPlanRecordBtn);

		menuPanel.add(runTestBtn);

		add(menuPanel, BorderLayout.CENTER);

		JPanel statePanel = new JPanel();
		tipLbl = new JLabel("");
		statePanel.add(tipLbl);

		add(statePanel, BorderLayout.SOUTH);
	}

	public JLabel getTipLbl() {
		return tipLbl;
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

	public JButton getMedicalrecordBtn() {
		return medicalrecordBtn;
	}

	public JButton getCreateOutPatientPlanRecordBtn() {
		return createOutPatientPlanRecordBtn;
	}

	public JButton getRunTestBtn() {
		return runTestBtn;
	}
}
