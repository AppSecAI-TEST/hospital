package com.neusoft.hs.portal.swing.ui.forms.register.view.modal;

import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.shared.model.DoctorComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.InPatientAreaComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.InPatientDeptComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.SexComboBoxModel;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.widget.SpinnerDate;

@Component
public class VisitFormPanel extends JPanel {

	private static final int LAYOUT_ROWS = 4;
	private static final int LAYOUT_COLS = 2;
	private static final int HORIZONTAL_GAP = 0;
	private static final int VERTICAL_GAP = 20;
	private static final int TEXT_FIELD_COLUMNS = 20;

	private JTextField nameTF;

	private JTextField carNumberTF;

	private SpinnerDate birthdaySD;

	private JComboBox<String> sexCB;
	private SexComboBoxModel sexComboBoxModel;

	private JComboBox<InPatientDept> respDeptCB;
	private InPatientDeptComboBoxModel respDeptComboBoxModel;

	private JComboBox<InPatientAreaDept> respAreaCB;
	private InPatientAreaComboBoxModel respAreaComboBoxModel;

	private JComboBox<Doctor> respDoctorCB;
	private DoctorComboBoxModel respDoctorComboBoxModel;

	@Autowired
	public VisitFormPanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(Borders.createEmptyBorder());
		setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS, HORIZONTAL_GAP,
				VERTICAL_GAP));
	}

	private void initComponents() {
		JLabel carNumberLbl = new JLabel(ConstMessagesCN.Labels.CarNumber);
		JLabel nameLbl = new JLabel(ConstMessagesCN.Labels.NAME);
		JLabel birthdayLbl = new JLabel(ConstMessagesCN.Labels.Birthday);
		JLabel sexLbl = new JLabel(ConstMessagesCN.Labels.Sex);
		JLabel respDeptLbl = new JLabel(ConstMessagesCN.Labels.InPatientDept);
		JLabel respAreaLbl = new JLabel(ConstMessagesCN.Labels.InPatientArea);
		JLabel respDoctorLbl = new JLabel(ConstMessagesCN.Labels.RespDoctor);

		carNumberTF = new JTextField(TEXT_FIELD_COLUMNS);
		nameTF = new JTextField(TEXT_FIELD_COLUMNS);
		birthdaySD = new SpinnerDate("yyyy-MM-dd");

		this.respDeptComboBoxModel = new InPatientDeptComboBoxModel();
		this.respDoctorComboBoxModel = new DoctorComboBoxModel();
		this.respAreaComboBoxModel = new InPatientAreaComboBoxModel();
		this.sexComboBoxModel = new SexComboBoxModel();

		sexCB = new JComboBox<>(sexComboBoxModel);
		respDeptCB = new JComboBox<>(respDeptComboBoxModel);
		respAreaCB = new JComboBox<>(respAreaComboBoxModel);
		respDoctorCB = new JComboBox<>(respDoctorComboBoxModel);

		add(carNumberLbl);
		add(carNumberTF);
		add(nameLbl);
		add(nameTF);
		add(birthdayLbl);
		add(birthdaySD);
		add(sexLbl);
		add(sexCB);

		add(respDeptLbl);
		add(respDeptCB);

		add(respAreaLbl);
		add(respAreaCB);

		add(respDoctorLbl);
		add(respDoctorCB);
	}

	public CreateVisitVO getEntityFromForm() throws HsException {

		CreateVisitVO createVisitVO = new CreateVisitVO();
		createVisitVO.setName(nameTF.getText());
		createVisitVO.setBirthday((Date) birthdaySD.getValue());
		createVisitVO.setSex(sexComboBoxModel.getSelectedItem());
		createVisitVO.setDept(respDeptComboBoxModel.getSelectedItem());
		createVisitVO.setArea(respAreaComboBoxModel.getSelectedItem());
		createVisitVO.setRespDoctor(respDoctorComboBoxModel.getSelectedItem());
		createVisitVO.setOperator(UserUtil.getUser());

		return createVisitVO;
	}

	public void clearForm() {
		nameTF.setText("");
		respDeptCB.setSelectedIndex(0);
		respAreaCB.setSelectedIndex(0);
		respDoctorCB.setSelectedIndex(0);
	}

	public SexComboBoxModel getSexComboBoxModel() {
		return sexComboBoxModel;
	}

	public InPatientDeptComboBoxModel getRespDeptComboBoxModel() {
		return respDeptComboBoxModel;
	}

	public InPatientAreaComboBoxModel getRespAreaComboBoxModel() {
		return respAreaComboBoxModel;
	}

	public DoctorComboBoxModel getRespDoctorComboBoxModel() {
		return respDoctorComboBoxModel;
	}

}
