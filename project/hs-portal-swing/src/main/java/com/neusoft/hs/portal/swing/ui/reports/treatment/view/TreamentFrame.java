package com.neusoft.hs.portal.swing.ui.reports.treatment.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class TreamentFrame extends JFrame {

	JComboBox<Visit> visitCB;
	VisitComboBoxModel visitComboBoxModel;

	private JPanel workspacePanel;

	private JButton closeBtn;

	private static final int DEFAULT_WIDTH = 800;

	private static final int DEFAULT_HEIGHT = 300;

	@Autowired
	public TreamentFrame() {
		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.OrderExecuteList);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		workspacePanel = new JPanel(new BorderLayout());

		JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel visitLbl = new JLabel(ConstMessagesCN.Labels.Visit);

		this.visitComboBoxModel = new VisitComboBoxModel();
		visitCB = new JComboBox<>(visitComboBoxModel);

		operationPanel.add(visitLbl);
		operationPanel.add(visitCB);

		workspacePanel.add(operationPanel, BorderLayout.NORTH);

		add(workspacePanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void showTreatment(List<TreatmentItemSpec> specs) {

		JPanel specPanel = new JPanel(new GridLayout(specs.size(), 2));
		for (TreatmentItemSpec spec : specs) {
			specPanel.add(new JLabel(spec.getName()));
			specPanel.add(new JTextField());
		}

		workspacePanel.add(specPanel, BorderLayout.CENTER);
	}

	public JComboBox<Visit> getVisitCB() {
		return visitCB;
	}

	public VisitComboBoxModel getVisitComboBoxModel() {
		return visitComboBoxModel;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}

}
