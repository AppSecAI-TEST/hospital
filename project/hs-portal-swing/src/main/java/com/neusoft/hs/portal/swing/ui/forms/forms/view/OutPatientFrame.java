package com.neusoft.hs.portal.swing.ui.forms.forms.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.portal.framework.security.LoginEvent;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class OutPatientFrame extends JFrame {

	private JButton loginBtn;

	private JButton createVoucherBtn;

	private JButton nextVoucherBtn;

	private JButton createOrderBtn;

	private JButton createMedicalRecordBtn;

	private JButton finishOrderExecuteBtn;

	private JButton maintainTreatmentBtn;

	private JLabel loginLbl;

	private final static int Width = 225;
	private final static int Height = 450;

	public OutPatientFrame() {
		setFrameUp();
		initComponents();
		pack();
	}

	private void setFrameUp() {
		getRootPane().setBorder(Borders.createEmptyBorder());
		setTitle(ConstMessagesCN.Labels.OutPatient);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {

		setLayout(new BorderLayout());

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(7, 2, 20, 20));

		loginBtn = new JButton(ConstMessagesCN.Labels.Login);
		createVoucherBtn = new JButton(ConstMessagesCN.Labels.CreateVoucher);
		nextVoucherBtn = new JButton(ConstMessagesCN.Labels.NextVoucher);
		createOrderBtn = new JButton(ConstMessagesCN.Labels.CreateOrder);
		createMedicalRecordBtn = new JButton(
				ConstMessagesCN.Labels.CreateMedicalRecord);
		finishOrderExecuteBtn = new JButton(
				ConstMessagesCN.Labels.FinishOrderExecute);
		maintainTreatmentBtn = new JButton(
				ConstMessagesCN.Labels.MaintainTreatment);

		menuPanel.add(loginBtn);
		menuPanel.add(createVoucherBtn);
		menuPanel.add(nextVoucherBtn);
		menuPanel.add(createOrderBtn);
		menuPanel.add(maintainTreatmentBtn);
		menuPanel.add(createMedicalRecordBtn);
		menuPanel.add(finishOrderExecuteBtn);

		add(menuPanel, BorderLayout.CENTER);

		JPanel statePanel = new JPanel();
		loginLbl = new JLabel(ConstMessagesCN.Labels.LogoutState);
		statePanel.add(loginLbl);

		add(statePanel, BorderLayout.SOUTH);

	}

	public JLabel getLoginLbl() {
		return loginLbl;
	}

	public JButton getLoginBtn() {
		return loginBtn;
	}

	public JButton getCreateVoucherBtn() {
		return createVoucherBtn;
	}

	public JButton getNextVoucherBtn() {
		return nextVoucherBtn;
	}

	public JButton getCreateOrderBtn() {
		return createOrderBtn;
	}

	public JButton getCreateMedicalRecordBtn() {
		return createMedicalRecordBtn;
	}

	public JButton getFinishOrderExecuteBtn() {
		return finishOrderExecuteBtn;
	}

	public JButton getMaintainTreatmentBtn() {
		return maintainTreatmentBtn;
	}
}
