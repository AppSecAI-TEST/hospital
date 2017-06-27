package com.neusoft.hs.portal.swing.ui.forms.inpatientdept.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class ArrangementMedicalRecordFrame extends JFrame {

	JComboBox<Visit> visitCB;
	VisitComboBoxModel visitComboBoxModel;

	private JButton createTemporaryOrderListBtn;

	private JButton createInspectResultBtn;

	private static final int DEFAULT_WIDTH = 800;

	private static final int DEFAULT_HEIGHT = 300;

	@Autowired
	public ArrangementMedicalRecordFrame() {
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

		JPanel visitPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		visitComboBoxModel = new VisitComboBoxModel();
		visitCB = new JComboBox<>(visitComboBoxModel);

		visitPanel.add(visitCB);

		add(visitPanel, BorderLayout.NORTH);

		JPanel operationPanel = new JPanel();

		createTemporaryOrderListBtn = new JButton(
				ConstMessagesCN.Labels.createTemporaryOrderListMR);
		createInspectResultBtn = new JButton(
				ConstMessagesCN.Labels.createInspectResultMR);

		operationPanel.add(createTemporaryOrderListBtn);
		operationPanel.add(createInspectResultBtn);

		add(operationPanel, BorderLayout.CENTER);

	}

	public JComboBox<Visit> getVisitCB() {
		return visitCB;
	}

	public VisitComboBoxModel getVisitComboBoxModel() {
		return visitComboBoxModel;
	}

	public JButton getCreateTemporaryOrderListBtn() {
		return createTemporaryOrderListBtn;
	}

	public JButton getCreateInspectResultBtn() {
		return createInspectResultBtn;
	}

}
