package com.neusoft.hs.portal.swing.ui.reports.cost.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.shared.model.ChargeRecordTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class ChargeRecordReportFrame extends JFrame {

	JComboBox<Visit> visitCB;
	VisitComboBoxModel visitComboBoxModel;

	ChargeRecordTableModel chargeRecordTableModel;
	JTable table;

	private JLabel chargeBillLbl;

	private static final int DEFAULT_WIDTH = 800;

	private static final int DEFAULT_HEIGHT = 300;

	@Autowired
	public ChargeRecordReportFrame() {
		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.ChargeRecord);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		this.chargeRecordTableModel = new ChargeRecordTableModel();
		table = new JTable(this.chargeRecordTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		add(paneWithTable, BorderLayout.CENTER);

		JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel visitLbl = new JLabel(ConstMessagesCN.Labels.Visit);

		this.visitComboBoxModel = new VisitComboBoxModel();
		visitCB = new JComboBox<>(visitComboBoxModel);

		operationPanel.add(visitLbl);
		operationPanel.add(visitCB);

		chargeBillLbl = new JLabel();
		operationPanel.add(chargeBillLbl);

		add(operationPanel, BorderLayout.NORTH);

	}

	public VisitComboBoxModel getVisitComboBoxModel() {
		return visitComboBoxModel;
	}

	public JComboBox<Visit> getVisitCB() {
		return visitCB;
	}

	public ChargeRecordTableModel getChargeRecordTableModel() {
		return chargeRecordTableModel;
	}

	public JLabel getChargeBillLbl() {
		return chargeBillLbl;
	}

}
