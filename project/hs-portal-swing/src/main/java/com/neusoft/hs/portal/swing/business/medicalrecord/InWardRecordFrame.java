package com.neusoft.hs.portal.swing.business.medicalrecord;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordItem;
import com.neusoft.hs.domain.medicalrecord.SimpleMedicalRecordItemValue;
import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

public class InWardRecordFrame extends JFrame {

	private static final int DEFAULT_WIDTH = 700;

	private static final int DEFAULT_HEIGHT = 600;

	private MedicalRecord medicalRecord;

	private JButton signBtn;

	private JButton closeBtn;

	public InWardRecordFrame(MedicalRecord medicalRecord) {

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
		try {
			subtitlePanel.add(new JLabel(DateUtil.toString(medicalRecord
					.getCreateDate())));
		} catch (HsException e) {
			e.printStackTrace();
		}

		titlePanel.add(subtitlePanel);

		contentPanel.add(titlePanel, BorderLayout.NORTH);
		
		Itemable item = medicalRecord.getDatas().get(
				TreatmentItemSpec.MainDescribe);

		JLabel mainDescribeJL = new JLabel();
		mainDescribeJL.setText(item.getValues().get(0).toString());

//		MedicalRecordItem item = medicalRecord
//				.getTheItem(TreatmentItemSpec.MainDescribe);
//
//		JLabel mainDescribeJL = new JLabel();
//		mainDescribeJL.setText(((SimpleMedicalRecordItemValue) item.getValues()
//				.get(0)).getInfo());

		contentPanel.add(mainDescribeJL, BorderLayout.CENTER);

		add(contentPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		if (medicalRecord.getType().isNeedSign()
				&& !medicalRecord.getState().equals(MedicalRecord.State_Signed)) {
			signBtn = new JButton(ConstMessagesCN.Labels.Sign_BTN);
			buttonPanel.add(signBtn);
		}

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public JButton getSignBtn() {
		return signBtn;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}
}
