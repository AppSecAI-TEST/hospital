package com.neusoft.hs.portal.swing.ui.reports.medicalrecord.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.reports.reports.view.VisitComboBoxFrame;
import com.neusoft.hs.portal.swing.ui.shared.model.MedicalRecordTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.view.MedicalRecordListPanel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class MedicalRecordReportFrame extends VisitComboBoxFrame {

	
	private MedicalRecordListPanel medicalRecordListPanel;
	
	@Autowired
	public MedicalRecordReportFrame() {
		this.medicalRecordListPanel = new MedicalRecordListPanel();

		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.MedicalRecords);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		JPanel workspacePanel = new JPanel(new BorderLayout());

		JPanel interactivePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		visitComboBoxModel = new VisitComboBoxModel();
		visitCB = new JComboBox<>(visitComboBoxModel);

		interactivePanel.add(visitCB);
		
		nextPageBtn = new JButton(ConstMessagesCN.Labels.NextPage_BTN);
		interactivePanel.add(nextPageBtn);

		workspacePanel.add(interactivePanel, BorderLayout.NORTH);

		workspacePanel.add(medicalRecordListPanel, BorderLayout.CENTER);

		add(workspacePanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);

	}

	public JComboBox<Visit> getVisitCB() {
		return visitCB;
	}

	public VisitComboBoxModel getVisitComboBoxModel() {
		return visitComboBoxModel;
	}

	public MedicalRecordTableModel getMedicalRecordTableModel() {
		return medicalRecordListPanel.getMedicalRecordTableModel();
	}

	public JTable getTable() {
		return medicalRecordListPanel.getTable();
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}
}
