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
import com.neusoft.hs.domain.pharmacy.ConfigureFluidBatch;
import com.neusoft.hs.domain.pharmacy.ConfigureFluidOrder;
import com.neusoft.hs.portal.swing.ui.shared.model.ConfigureFluidBatchComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.ConfigureFluidOrderTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.InPatientAreaDeptComboBoxModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.Notifications;

@Component
public class ConfigureFluidExecuteFrame extends JFrame {

	JComboBox<ConfigureFluidBatch> configureFluidBatchCB;
	ConfigureFluidBatchComboBoxModel configureFluidBatchComboBoxModel;

	JComboBox<InPatientAreaDept> inPatientAreaDeptCB;
	InPatientAreaDeptComboBoxModel inPatientAreaDeptComboBoxModel;

	private ConfigureFluidOrderTableModel configureFluidOrderTableModel;

	protected JTable table;

	private JButton createBtn;

	private JButton finishBtn;

	private JButton closeBtn;

	private static final int DEFAULT_WIDTH = 800;

	private static final int DEFAULT_HEIGHT = 300;

	@Autowired
	public ConfigureFluidExecuteFrame() {
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

		this.configureFluidOrderTableModel = new ConfigureFluidOrderTableModel();
		table = new JTable(this.configureFluidOrderTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		workspacePanel.add(paneWithTable, BorderLayout.CENTER);

		JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel dispensingDrugBatchLbl = new JLabel(
				ConstMessagesCN.Labels.DispensingDrugBatch);

		this.configureFluidBatchComboBoxModel = new ConfigureFluidBatchComboBoxModel();
		configureFluidBatchCB = new JComboBox<>(
				configureFluidBatchComboBoxModel);

		operationPanel.add(dispensingDrugBatchLbl);
		operationPanel.add(configureFluidBatchCB);

		JLabel inPatientAreaDeptLbl = new JLabel(
				ConstMessagesCN.Labels.InPatientAreaDept);

		this.inPatientAreaDeptComboBoxModel = new InPatientAreaDeptComboBoxModel();
		inPatientAreaDeptCB = new JComboBox<>(inPatientAreaDeptComboBoxModel);

		operationPanel.add(inPatientAreaDeptLbl);
		operationPanel.add(inPatientAreaDeptCB);

		createBtn = new JButton(ConstMessagesCN.Labels.Create_BTN);
		operationPanel.add(createBtn);

		finishBtn = new JButton(ConstMessagesCN.Labels.FINISH_BTN);
		operationPanel.add(finishBtn);

		workspacePanel.add(operationPanel, BorderLayout.NORTH);

		add(workspacePanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);

	}

	public JComboBox<ConfigureFluidBatch> getConfigureFluidBatchCB() {
		return configureFluidBatchCB;
	}

	public ConfigureFluidBatchComboBoxModel getConfigureFluidBatchComboBoxModel() {
		return configureFluidBatchComboBoxModel;
	}

	public JComboBox<InPatientAreaDept> getInPatientAreaDeptCB() {
		return inPatientAreaDeptCB;
	}

	public InPatientAreaDeptComboBoxModel getInPatientAreaDeptComboBoxModel() {
		return inPatientAreaDeptComboBoxModel;
	}

	public ConfigureFluidOrderTableModel getConfigureFluidOrderTableModel() {
		return configureFluidOrderTableModel;
	}

	public ConfigureFluidOrder getConfigureFluidOrder() {
		if (this.table.getSelectedRow() == -1) {
			Notifications.showFormValidationAlert("请选择配液单");
		}
		return configureFluidOrderTableModel.getEntityByRow(this.table
				.getSelectedRow());
	}

	public JButton getCreateBtn() {
		return createBtn;
	}

	public JButton getFinishBtn() {
		return finishBtn;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}

}
