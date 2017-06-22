package com.neusoft.hs.portal.swing.ui.reports.cost.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

@Component
public class ChargeRecordReportFrame extends JFrame {

	JComboBox<Visit> visitCB;
	VisitComboBoxModel visitComboBoxModel;

	ChargeRecordTableModel chargeRecordTableModel;
	JTable table;

	private static final int DEFAULT_WIDTH = 800;

	private static final int DEFAULT_HEIGHT = 300;

	@Autowired
	public ChargeRecordReportFrame(VisitComboBoxModel visitComboBoxModel,
			ChargeRecordTableModel chargeRecordTableModel) {
		this.visitComboBoxModel = visitComboBoxModel;
		this.chargeRecordTableModel = chargeRecordTableModel;

		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesEN.Labels.Order);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		table = new JTable(this.chargeRecordTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		add(paneWithTable, BorderLayout.CENTER);

		JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel visitLbl = new JLabel(ConstMessagesEN.Labels.Visit);
		visitCB = new JComboBox<>(visitComboBoxModel);
		
		operationPanel.add(visitLbl);
		operationPanel.add(visitCB);

		add(operationPanel, BorderLayout.NORTH);

	}

	public JComboBox<Visit> getVisitCB() {
		return visitCB;
	}
}
