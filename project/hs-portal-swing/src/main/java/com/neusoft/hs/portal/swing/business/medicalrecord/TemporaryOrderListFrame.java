package com.neusoft.hs.portal.swing.business.medicalrecord;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.neusoft.hs.domain.medicalrecord.ListMedicalRecordItemValue;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordItem;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordItemValue;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

public class TemporaryOrderListFrame extends JFrame {

	private static final int DEFAULT_WIDTH = 700;

	private static final int DEFAULT_HEIGHT = 600;

	private MedicalRecord medicalRecord;

	private JButton closeBtn;

	public TemporaryOrderListFrame(MedicalRecord medicalRecord) {

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

		MedicalRecordItem item = medicalRecord
				.getTheItem(TreatmentItemSpec.TemporaryOrderList);

		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn(ConstMessagesCN.Labels.Name);
		tableModel.addColumn(ConstMessagesCN.Labels.Count);
		tableModel.addColumn(ConstMessagesCN.Labels.CreateDate);
		tableModel.addColumn(ConstMessagesCN.Labels.ExecuteDate);
		tableModel.addColumn(ConstMessagesCN.Labels.Doctor);

		ListMedicalRecordItemValue list;
		for (MedicalRecordItemValue value : item.getValues()) {
			list = (ListMedicalRecordItemValue) value;

			Object[] rowData = new Object[5];
			rowData[0] = list.getData().get("name");
			rowData[1] = list.getData().get("count");
			rowData[2] = list.getData().get("createDate");
			rowData[3] = list.getData().get("executeDate");
			rowData[4] = list.getData().get("creator");

			tableModel.addRow(rowData);
		}
		JTable table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		contentPanel.add(paneWithTable, BorderLayout.CENTER);

		add(contentPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}
}
