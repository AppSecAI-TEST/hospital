package com.neusoft.hs.portal.swing.ui.forms.registration.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.shared.model.OutPatientPlanRecordComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.SexComboBoxModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.widget.SpinnerDate;

@Component
public class CreateVoucherFrame extends JFrame {

	private static final int LAYOUT_ROWS = 3;
	private static final int LAYOUT_COLS = 2;
	private static final int HORIZONTAL_GAP = 0;
	private static final int VERTICAL_GAP = 20;
	private static final int TEXT_FIELD_COLUMNS = 20;

	private JTextField nameTF;

	private JTextField carNumberTF;

	private SpinnerDate birthdaySD;

	private JComboBox<String> sexCB;
	private SexComboBoxModel sexComboBoxModel;

	private JComboBox<OutPatientPlanRecord> outPatientPlanRecordCB;
	private OutPatientPlanRecordComboBoxModel outPatientPlanRecordComboBoxModel;

	private JButton confirmBtn;

	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 200;

	@Autowired
	public CreateVoucherFrame() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setTitle(ConstMessagesCN.Labels.CreateOutPatientRecord);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {

		JPanel workspacePanel = new JPanel(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS,
				HORIZONTAL_GAP, VERTICAL_GAP));

		JLabel carNumberLbl = new JLabel(ConstMessagesCN.Labels.CarNumber);
		JLabel nameLbl = new JLabel(ConstMessagesCN.Labels.NAME);
		JLabel birthdayLbl = new JLabel(ConstMessagesCN.Labels.Birthday);
		JLabel sexLbl = new JLabel(ConstMessagesCN.Labels.Sex);
		JLabel planRecordLbl = new JLabel(
				ConstMessagesCN.Labels.OutPatientPlanRecord);

		carNumberTF = new JTextField(TEXT_FIELD_COLUMNS);
		nameTF = new JTextField(TEXT_FIELD_COLUMNS);
		birthdaySD = new SpinnerDate("yyyy-MM-dd");

		this.sexComboBoxModel = new SexComboBoxModel();
		sexCB = new JComboBox<>(sexComboBoxModel);

		this.outPatientPlanRecordComboBoxModel = new OutPatientPlanRecordComboBoxModel();
		outPatientPlanRecordCB = new JComboBox<>(
				outPatientPlanRecordComboBoxModel);

		contentPanel.add(carNumberLbl);
		contentPanel.add(carNumberTF);
		contentPanel.add(nameLbl);
		contentPanel.add(nameTF);
		contentPanel.add(birthdayLbl);
		contentPanel.add(birthdaySD);
		contentPanel.add(sexLbl);
		contentPanel.add(sexCB);

		contentPanel.add(planRecordLbl);
		contentPanel.add(outPatientPlanRecordCB);

		workspacePanel.add(contentPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		confirmBtn = new JButton(ConstMessagesCN.Labels.CONFIRM_BTN);
		buttonPanel.add(confirmBtn);

		workspacePanel.add(buttonPanel, BorderLayout.SOUTH);

		add(workspacePanel);

	}

	public OutPatientPlanRecordComboBoxModel getOutPatientPlanRecordComboBoxModel() {
		return outPatientPlanRecordComboBoxModel;
	}

	public JButton getConfirmBtn() {
		return confirmBtn;
	}

	public CreateVisitVO getCreateVisitVO() throws HsException {

		CreateVisitVO createVisitVO = new CreateVisitVO();
		createVisitVO.setName(nameTF.getText());
		createVisitVO.setBirthday((Date) birthdaySD.getValue());
		createVisitVO.setSex(sexComboBoxModel.getSelectedItem());
		createVisitVO.setOperator(UserUtil.getUser());

		return createVisitVO;
	}
}
