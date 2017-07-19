package com.neusoft.hs.portal.swing.ui.forms.forms.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class InPatientFrame extends JFrame {

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

	private final static int Width = 225;
	private final static int Height = 650;

	public InPatientFrame() {
		setFrameUp();
		initComponents();
		pack();
	}

	private void setFrameUp() {
		getRootPane().setBorder(Borders.createEmptyBorder());
		setTitle(ConstMessagesCN.Labels.InPatient);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {

		setLayout(new BorderLayout());

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(13, 2, 20, 20));

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
}
