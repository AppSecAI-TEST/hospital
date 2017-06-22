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
		setLayout(new GridLayout(1, 1, 20, 20));
	}

	private void initComponents() {
		chargeRecordReportBtn = new JButton(ConstMessagesEN.Labels.ChargeRecord);

		add(chargeRecordReportBtn);
	}

	public JButton getChargeRecordReportBtn() {
		return chargeRecordReportBtn;
	}

}
