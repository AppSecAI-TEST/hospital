package com.neusoft.hs.portal.swing.ui.reports.outpatientoffice.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.domain.outpatientoffice.OutPatientRoom;
import com.neusoft.hs.domain.outpatientoffice.VoucherType;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.portal.swing.ui.shared.model.DoctorComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OutPatientRoomComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VoucherTypeComboBoxModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.widget.SpinnerDate;
import com.neusoft.hs.portal.swing.widget.SpinnerNumber;

@Component
public class CreateOutPatientPlanRecordFrame extends JFrame {

	private static final int LAYOUT_ROWS = 3;
	private static final int LAYOUT_COLS = 2;
	private static final int HORIZONTAL_GAP = 0;
	private static final int VERTICAL_GAP = 20;
	private static final int TEXT_FIELD_COLUMNS = 20;

	private JComboBox<Doctor> doctorCB;
	private DoctorComboBoxModel doctorComboBoxModel;

	private JComboBox<VoucherType> voucherTypeCB;
	private VoucherTypeComboBoxModel voucherTypeComboBoxModel;

	private SpinnerDate planStartDateSD;

	private SpinnerDate planEndDateSD;

	private JComboBox<OutPatientRoom> outPatientRoomCB;
	private OutPatientRoomComboBoxModel outPatientRoomComboBoxModel;

	private SpinnerNumber maxAllotNumberSN;

	private JButton confirmBtn;

	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 200;

	@Autowired
	public CreateOutPatientPlanRecordFrame() {
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

		JLabel doctorLbl = new JLabel(ConstMessagesCN.Labels.Doctor);
		JLabel voucherTypeLbl = new JLabel(ConstMessagesCN.Labels.VoucherType);
		JLabel planStartDateLbl = new JLabel(
				ConstMessagesCN.Labels.PlanStartDate);
		JLabel planEndDateLbl = new JLabel(ConstMessagesCN.Labels.PlanEndDate);
		JLabel outPatientRoomLbl = new JLabel(
				ConstMessagesCN.Labels.OutPatientRoom);
		JLabel maxAllotNumberLbl = new JLabel(
				ConstMessagesCN.Labels.MaxAllotNumber);

		this.doctorComboBoxModel = new DoctorComboBoxModel();
		this.voucherTypeComboBoxModel = new VoucherTypeComboBoxModel();
		this.outPatientRoomComboBoxModel = new OutPatientRoomComboBoxModel();

		doctorCB = new JComboBox<>(doctorComboBoxModel);
		voucherTypeCB = new JComboBox<>(voucherTypeComboBoxModel);
		outPatientRoomCB = new JComboBox<>(outPatientRoomComboBoxModel);

		planStartDateSD = new SpinnerDate("yyyy-MM-dd HH",
				DateUtil.getSysDateStart());
		planEndDateSD = new SpinnerDate("yyyy-MM-dd HH", DateUtil.addDay(
				DateUtil.getSysDateStart(), 1));

		maxAllotNumberSN = new SpinnerNumber(
				OutPatientPlanRecord.MaxAllotNumber);

		contentPanel.add(doctorLbl);
		contentPanel.add(doctorCB);
		contentPanel.add(voucherTypeLbl);
		contentPanel.add(voucherTypeCB);
		contentPanel.add(planStartDateLbl);
		contentPanel.add(planStartDateSD);
		contentPanel.add(planEndDateLbl);
		contentPanel.add(planEndDateSD);

		contentPanel.add(outPatientRoomLbl);
		contentPanel.add(outPatientRoomCB);

		contentPanel.add(maxAllotNumberLbl);
		contentPanel.add(maxAllotNumberSN);

		workspacePanel.add(contentPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		confirmBtn = new JButton(ConstMessagesCN.Labels.CONFIRM_BTN);
		buttonPanel.add(confirmBtn);

		workspacePanel.add(buttonPanel, BorderLayout.SOUTH);

		add(workspacePanel);

	}

	public DoctorComboBoxModel getDoctorComboBoxModel() {
		return doctorComboBoxModel;
	}

	public VoucherTypeComboBoxModel getVoucherTypeComboBoxModel() {
		return voucherTypeComboBoxModel;
	}

	public SpinnerDate getPlanStartDateSD() {
		return planStartDateSD;
	}

	public SpinnerDate getPlanEndDateSD() {
		return planEndDateSD;
	}

	public OutPatientRoomComboBoxModel getOutPatientRoomComboBoxModel() {
		return outPatientRoomComboBoxModel;
	}

	public JButton getConfirmBtn() {
		return confirmBtn;
	}

	public OutPatientPlanRecord getOutPatientPlanRecord() {

		OutPatientPlanRecord planRecord = new OutPatientPlanRecord();

		planRecord.setDoctor(doctorComboBoxModel.getSelectedItem());
		planRecord.setVoucherType(voucherTypeComboBoxModel.getSelectedItem());
		planRecord.setPlanStartDate(planStartDateSD.getDate());
		planRecord.setPlanEndDate(planEndDateSD.getDate());
		planRecord.setRoom(outPatientRoomComboBoxModel.getSelectedItem());
		planRecord.setMaxAllotNumber(maxAllotNumberSN.getInteger());

		return planRecord;
	}

}
