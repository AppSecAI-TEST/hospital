package com.neusoft.hs.portal.swing.ui.forms.register.view.modal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.forms.register.model.DoctorComboBoxModel;
import com.neusoft.hs.portal.swing.ui.forms.register.model.InPatientDeptComboBoxModel;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

import javax.swing.*;

import java.awt.*;

@Component
public class VisitFormPanel extends JPanel {

	private static final int LAYOUT_ROWS = 3;
	private static final int LAYOUT_COLS = 2;
	private static final int HORIZONTAL_GAP = 0;
	private static final int VERTICAL_GAP = 20;
	private static final int TEXT_FIELD_COLUMNS = 20;

	private JTextField nameTF;

	private JComboBox<InPatientDept> respDeptCB;
	private InPatientDeptComboBoxModel respDeptComboBoxModel;

	private JComboBox<Doctor> respDoctorCB;
	private DoctorComboBoxModel respDoctorComboBoxModel;

	@Autowired
	public VisitFormPanel(InPatientDeptComboBoxModel respDeptComboBoxModel,
			DoctorComboBoxModel respDoctorComboBoxModel) {
		this.respDeptComboBoxModel = respDeptComboBoxModel;
		this.respDoctorComboBoxModel = respDoctorComboBoxModel;
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(Borders.createEmptyBorder());
		setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS, HORIZONTAL_GAP,
				VERTICAL_GAP));
	}

	private void initComponents() {
		JLabel nameLbl = new JLabel(ConstMessagesEN.Labels.NAME);
		JLabel respDeptLbl = new JLabel(ConstMessagesEN.Labels.InPatientDept);
		JLabel respDoctorLbl = new JLabel(ConstMessagesEN.Labels.RespDoctor);

		nameTF = new JTextField(TEXT_FIELD_COLUMNS);
		respDeptCB = new JComboBox<>(respDeptComboBoxModel);
		respDoctorCB = new JComboBox<>(respDoctorComboBoxModel);

		add(nameLbl);
		add(nameTF);
		add(respDeptLbl);
		add(respDeptCB);
		add(respDoctorLbl);
		add(respDoctorCB);
	}

	public Visit getEntityFromForm() {

		Visit visit = new Visit();
		visit.setName(nameTF.getText());
		visit.setRespDept(respDeptComboBoxModel.getSelectedItem());
		visit.setRespDoctor(respDoctorComboBoxModel.getSelectedItem());

		return visit;
	}

	public void clearForm() {
		nameTF.setText("");
		respDeptCB.setSelectedIndex(0);
		respDoctorCB.setSelectedIndex(0);
	}

}
