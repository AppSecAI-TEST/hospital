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
public class FormsFrame extends JFrame implements
		ApplicationListener<LoginEvent> {

	private JButton loginBtn;

	private JButton createVoucherBtn;

	private JButton nextVoucherBtn;

	private JButton registerBtn;

	private JButton cashierBtn;

	private JButton receiveBtn;

	private JButton createOrderBtn;

	private JButton createMedicalRecordBtn;

	private JButton verifyOrderBtn;

	private JButton sendOrderExecuteBtn;

	private JButton finishOrderExecuteBtn;

	private JButton maintainTreatmentBtn;

	private JButton arrangementMedicalRecordBtn;

	private JButton transferMedicalRecordBtn;

	private JButton qualityControlBtn;

	private JButton archiveMedicalRecordBtn;

	private JLabel loginLbl;

	private final static int Width = 225;
	private final static int Height = 650;

	public FormsFrame() {
		setFrameUp();
		initComponents();
		pack();
	}

	private void setFrameUp() {
		getRootPane().setBorder(Borders.createEmptyBorder());
		setTitle(ConstMessagesCN.Labels.FORMS);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {

		setLayout(new BorderLayout());

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(16, 2, 20, 20));

		loginBtn = new JButton(ConstMessagesCN.Labels.Login);
		createVoucherBtn = new JButton(ConstMessagesCN.Labels.CreateVoucher);
		nextVoucherBtn = new JButton(ConstMessagesCN.Labels.NextVoucher);
		registerBtn = new JButton(ConstMessagesCN.Labels.Register);
		cashierBtn = new JButton(ConstMessagesCN.Labels.InitAccount);
		receiveBtn = new JButton(ConstMessagesCN.Labels.ReceiveVisit);
		createOrderBtn = new JButton(ConstMessagesCN.Labels.CreateOrder);
		createMedicalRecordBtn = new JButton(
				ConstMessagesCN.Labels.CreateMedicalRecord);
		verifyOrderBtn = new JButton(ConstMessagesCN.Labels.VerifyOrder);
		sendOrderExecuteBtn = new JButton(
				ConstMessagesCN.Labels.SendOrderExecute);
		finishOrderExecuteBtn = new JButton(
				ConstMessagesCN.Labels.FinishOrderExecute);
		maintainTreatmentBtn = new JButton(
				ConstMessagesCN.Labels.MaintainTreatment);
		arrangementMedicalRecordBtn = new JButton(
				ConstMessagesCN.Labels.ArrangementMedicalRecord);
		transferMedicalRecordBtn = new JButton(
				ConstMessagesCN.Labels.TransferMedicalRecord);
		qualityControlBtn = new JButton(ConstMessagesCN.Labels.QualityControl);
		archiveMedicalRecordBtn = new JButton(
				ConstMessagesCN.Labels.ArchiveMedicalRecord);

		menuPanel.add(loginBtn);
		menuPanel.add(createVoucherBtn);
		menuPanel.add(nextVoucherBtn);
		menuPanel.add(registerBtn);
		menuPanel.add(cashierBtn);
		menuPanel.add(receiveBtn);
		menuPanel.add(createOrderBtn);
		menuPanel.add(maintainTreatmentBtn);
		menuPanel.add(createMedicalRecordBtn);
		menuPanel.add(verifyOrderBtn);
		menuPanel.add(sendOrderExecuteBtn);
		menuPanel.add(finishOrderExecuteBtn);
		menuPanel.add(arrangementMedicalRecordBtn);
		menuPanel.add(transferMedicalRecordBtn);
		menuPanel.add(qualityControlBtn);
		menuPanel.add(archiveMedicalRecordBtn);

		add(menuPanel, BorderLayout.CENTER);

		JPanel statePanel = new JPanel();
		loginLbl = new JLabel(ConstMessagesCN.Labels.LogoutState);
		statePanel.add(loginLbl);

		add(statePanel, BorderLayout.SOUTH);

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

	public JButton getRegisterBtn() {
		return registerBtn;
	}

	public JButton getCashierBtn() {
		return cashierBtn;
	}

	public JButton getReceiveBtn() {
		return receiveBtn;
	}

	public JButton getCreateOrderBtn() {
		return createOrderBtn;
	}

	public JButton getCreateMedicalRecordBtn() {
		return createMedicalRecordBtn;
	}

	public JButton getVerifyOrderBtn() {
		return verifyOrderBtn;
	}

	public JButton getSendOrderExecuteBtn() {
		return sendOrderExecuteBtn;
	}

	public JButton getFinishOrderExecuteBtn() {
		return finishOrderExecuteBtn;
	}

	public JButton getMaintainTreatmentBtn() {
		return maintainTreatmentBtn;
	}

	public JButton getArrangementMedicalRecordBtn() {
		return arrangementMedicalRecordBtn;
	}

	public JButton getTransferMedicalRecordBtn() {
		return transferMedicalRecordBtn;
	}

	public JButton getQualityControlBtn() {
		return qualityControlBtn;
	}

	public JButton getArchiveMedicalRecordBtn() {
		return archiveMedicalRecordBtn;
	}

	@Override
	public void onApplicationEvent(LoginEvent event) {
		AbstractUser user = (AbstractUser) event.getSource();
		if (user != null) {
			loginLbl.setText(user.getName());
		} else {
			loginLbl.setText(ConstMessagesCN.Labels.LogoutState);
		}
	}
}
