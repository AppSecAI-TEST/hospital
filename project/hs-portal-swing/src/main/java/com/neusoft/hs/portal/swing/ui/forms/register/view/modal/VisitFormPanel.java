package com.neusoft.hs.portal.swing.ui.forms.register.view.modal;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.shared.model.DoctorComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.InPatientDeptComboBoxModel;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

@Component
public class VisitFormPanel extends JPanel {

	private static final int LAYOUT_ROWS = 3;
	private static final int LAYOUT_COLS = 2;
	private static final int HORIZONTAL_GAP = 0;
	private static final int VERTICAL_GAP = 20;
	private static final int TEXT_FIELD_COLUMNS = 20;

	private JTextField nameTF;
	
	private JTextField carNumberTF;
	
	private JTextField birthdayTF;
	
	private JTextField sexTF;

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
		JLabel carNumberLbl = new JLabel(ConstMessagesEN.Labels.CarNumber);
		JLabel nameLbl = new JLabel(ConstMessagesEN.Labels.NAME);
		JLabel birthdayLbl = new JLabel(ConstMessagesEN.Labels.Birthday);
		JLabel sexLbl = new JLabel(ConstMessagesEN.Labels.Sex);
		JLabel respDeptLbl = new JLabel(ConstMessagesEN.Labels.InPatientDept);
		JLabel respDoctorLbl = new JLabel(ConstMessagesEN.Labels.RespDoctor);

		carNumberTF = new JTextField(TEXT_FIELD_COLUMNS);
		nameTF = new JTextField(TEXT_FIELD_COLUMNS);
		birthdayTF = new JTextField(TEXT_FIELD_COLUMNS);
		sexTF = new JTextField(TEXT_FIELD_COLUMNS);
		
		respDeptCB = new JComboBox<>(respDeptComboBoxModel);
		respDoctorCB = new JComboBox<>(respDoctorComboBoxModel);

		add(carNumberLbl);
		add(carNumberTF);
		add(nameLbl);
		add(nameTF);
		add(birthdayLbl);
		add(birthdayTF);
		add(sexLbl);
		add(sexTF);
		
		add(respDeptLbl);
		add(respDeptCB);
		add(respDoctorLbl);
		add(respDoctorCB);
	}

	public CreateVisitVO getEntityFromForm() throws HsException {

		CreateVisitVO createVisitVO = new CreateVisitVO();
		createVisitVO.setName(nameTF.getText());
		createVisitVO.setDept(respDeptComboBoxModel.getSelectedItem());
		createVisitVO.setRespDoctor(respDoctorComboBoxModel.getSelectedItem());
		createVisitVO.setOperator(UserUtil.getUser());
		
		return createVisitVO;
	}

	public void clearForm() {
		nameTF.setText("");
		respDeptCB.setSelectedIndex(0);
		respDoctorCB.setSelectedIndex(0);
	}

}
