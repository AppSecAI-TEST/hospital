package com.neusoft.hs.portal.swing.ui.forms.pharmacy.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.domain.pharmacy.DispensingDrugBatch;
import com.neusoft.hs.portal.swing.ui.shared.model.DispensingDrugBatchComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.DispensingDrugOrderTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.InPatientAreaDeptComboBoxModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class PharmacyExecuteFrame extends JFrame {

	JComboBox<DispensingDrugBatch> dispensingDrugBatchCB;
	DispensingDrugBatchComboBoxModel dispensingDrugBatchComboBoxModel;

	JComboBox<InPatientAreaDept> inPatientAreaDeptCB;
	InPatientAreaDeptComboBoxModel inPatientAreaDeptComboBoxModel;

	private DispensingDrugOrderTableModel dispensingDrugOrderTableModel;

	protected JTable table;

	private JButton closeBtn;

	private static final int DEFAULT_WIDTH = 800;

	private static final int DEFAULT_HEIGHT = 300;

	@Autowired
	public PharmacyExecuteFrame() {
		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.PharmacyExecute);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		JPanel workspacePanel = new JPanel(new BorderLayout());

		this.dispensingDrugOrderTableModel = new DispensingDrugOrderTableModel();
		table = new JTable(this.dispensingDrugOrderTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		workspacePanel.add(paneWithTable, BorderLayout.CENTER);

		JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel dispensingDrugBatchLbl = new JLabel(
				ConstMessagesCN.Labels.DispensingDrugBatch);

		this.dispensingDrugBatchComboBoxModel = new DispensingDrugBatchComboBoxModel();
		dispensingDrugBatchCB = new JComboBox<>(
				dispensingDrugBatchComboBoxModel);

		operationPanel.add(dispensingDrugBatchLbl);
		operationPanel.add(dispensingDrugBatchCB);

		JLabel inPatientAreaDeptLbl = new JLabel(
				ConstMessagesCN.Labels.InPatientAreaDept);

		this.inPatientAreaDeptComboBoxModel = new InPatientAreaDeptComboBoxModel();
		inPatientAreaDeptCB = new JComboBox<>(inPatientAreaDeptComboBoxModel);

		operationPanel.add(inPatientAreaDeptLbl);
		operationPanel.add(inPatientAreaDeptCB);

		workspacePanel.add(operationPanel, BorderLayout.NORTH);

		add(workspacePanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);

	}

	public JComboBox<DispensingDrugBatch> getDispensingDrugBatchCB() {
		return dispensingDrugBatchCB;
	}

	public DispensingDrugBatchComboBoxModel getDispensingDrugBatchComboBoxModel() {
		return dispensingDrugBatchComboBoxModel;
	}

	public JComboBox<InPatientAreaDept> getInPatientAreaDeptCB() {
		return inPatientAreaDeptCB;
	}

	public InPatientAreaDeptComboBoxModel getInPatientAreaDeptComboBoxModel() {
		return inPatientAreaDeptComboBoxModel;
	}

	public DispensingDrugOrderTableModel getDispensingDrugOrderTableModel() {
		return dispensingDrugOrderTableModel;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}

}
