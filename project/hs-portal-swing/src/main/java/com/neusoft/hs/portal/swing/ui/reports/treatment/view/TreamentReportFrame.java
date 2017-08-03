package com.neusoft.hs.portal.swing.ui.reports.treatment.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.reports.reports.view.VisitComboBoxFrame;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class TreamentReportFrame extends VisitComboBoxFrame {

	private JPanel workspacePanel;

	private Map<TreatmentItemSpec, JTextField> treatments;
	
	@Autowired
	public TreamentReportFrame() {
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
		
		nextPageBtn = new JButton(ConstMessagesCN.Labels.NextPage_BTN);
		operationPanel.add(nextPageBtn);

		workspacePanel.add(operationPanel, BorderLayout.NORTH);

		add(workspacePanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void showTreatment(List<TreatmentItemSpec> specs) {

		treatments = new HashMap<TreatmentItemSpec, JTextField>();
		JPanel specPanel = new JPanel(new GridLayout(specs.size(), 2));
		for (TreatmentItemSpec spec : specs) {
			specPanel.add(new JLabel(spec.getName()));

			JTextField valueTF = new JTextField();
			specPanel.add(valueTF);

			treatments.put(spec, valueTF);
		}

		workspacePanel.add(specPanel, BorderLayout.CENTER);
	}

	public void showTheTreatment(TreatmentItemSpec spec, TreatmentItem item) {
		if (item != null) {
			treatments.get(spec).setText(item.getValueInfo());
		} else {
			treatments.get(spec).setText("");
		}
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
