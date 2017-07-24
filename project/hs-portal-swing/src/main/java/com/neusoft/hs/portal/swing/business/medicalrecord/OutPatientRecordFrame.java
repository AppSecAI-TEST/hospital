package com.neusoft.hs.portal.swing.business.medicalrecord;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

public class OutPatientRecordFrame extends JFrame {

	private static final int DEFAULT_WIDTH = 700;

	private static final int DEFAULT_HEIGHT = 600;

	private MedicalRecord medicalRecord;

	private JTextField mainDescribeTF;

	private JButton saveBtn;

	private JButton fixBtn;

	private JButton signBtn;

	private JButton closeBtn;

	public OutPatientRecordFrame(MedicalRecord medicalRecord) {

		this.medicalRecord = medicalRecord;

		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.MedicalRecord);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {

		JPanel contentPanel = new JPanel(new BorderLayout());

		JPanel titlePanel = new JPanel(new GridLayout(2, 1));

		JPanel namePanel = new JPanel();
		JLabel name = new JLabel(medicalRecord.getType().getName());
		name.setFont(new Font("宋体", Font.BOLD, 20));
		namePanel.add(name);

		titlePanel.add(namePanel);

		JPanel subtitlePanel = new JPanel(new GridLayout(1, 2));

		subtitlePanel.add(new JLabel(medicalRecord.getVisitName()));
		subtitlePanel.add(new JLabel(DateUtil.toString(medicalRecord
				.getCreateDate())));

		titlePanel.add(subtitlePanel);

		contentPanel.add(titlePanel, BorderLayout.NORTH);

		Itemable item = medicalRecord.getDatas().get(
				TreatmentItemSpec.MainDescribe);

		mainDescribeTF = new JTextField();
		if (item != null && item.getValues() != null) {
			mainDescribeTF.setText(item.getValues().get(0).toString());
		}

		contentPanel.add(mainDescribeTF, BorderLayout.CENTER);

		add(contentPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		if (medicalRecord.getState() == null
				|| medicalRecord.getState().equals(MedicalRecord.State_Created)) {
			saveBtn = new JButton(ConstMessagesCN.Labels.SAVE_BTN);
			buttonPanel.add(saveBtn);
		}

		if (medicalRecord.getState().equals(MedicalRecord.State_Created)) {
			fixBtn = new JButton(ConstMessagesCN.Labels.FIX_BTN);
			buttonPanel.add(fixBtn);
		}

		if (medicalRecord.getType().isNeedSign()
				&& (medicalRecord.getState()
						.equals(MedicalRecord.State_Created) || medicalRecord
						.getState().equals(MedicalRecord.State_Fixed))) {
			signBtn = new JButton(ConstMessagesCN.Labels.SIGN_BTN);
			buttonPanel.add(signBtn);
		}

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public JTextField getMainDescribeTF() {
		return mainDescribeTF;
	}

	public JButton getSaveBtn() {
		return saveBtn;
	}

	public JButton getFixBtn() {
		return fixBtn;
	}

	public JButton getSignBtn() {
		return signBtn;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}
}
