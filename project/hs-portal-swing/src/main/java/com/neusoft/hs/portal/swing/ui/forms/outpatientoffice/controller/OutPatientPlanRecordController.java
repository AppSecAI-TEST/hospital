package com.neusoft.hs.portal.swing.ui.forms.outpatientoffice.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanAdminDomainService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanDomainService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.domain.outpatientoffice.OutPatientRoom;
import com.neusoft.hs.domain.outpatientoffice.VoucherType;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.forms.outpatientoffice.view.CreateOutPatientPlanRecordFrame;
import com.neusoft.hs.portal.swing.ui.forms.outpatientoffice.view.OutPatientPlanRecordFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.DoctorComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OutPatientPlanRecordTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OutPatientRoomComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VoucherTypeComboBoxModel;

@Controller
public class OutPatientPlanRecordController extends AbstractFrameController {

	@Autowired
	private OutPatientPlanDomainService outPatientPlanDomainService;

	@Autowired
	private UserAdminDomainService userDomainService;

	@Autowired
	private OutPatientPlanAdminDomainService outPatientPlanAdminDomainService;

	@Autowired
	private OutPatientPlanRecordFrame outPatientPlanRecordFrame;

	@Autowired
	private CreateOutPatientPlanRecordFrame createOutPatientPlanRecordFrame;

	@PostConstruct
	private void prepareListeners() {
		registerAction(outPatientPlanRecordFrame.getAddBtn(),
				(e) -> openCreateOutPatientPlanRecord());
		registerAction(outPatientPlanRecordFrame.getCloseBtn(),
				(e) -> closeWindow());

		registerAction(createOutPatientPlanRecordFrame.getConfirmBtn(),
				(e) -> createOutPatientPlanRecord());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadOutPatientPlanRecords();
		outPatientPlanRecordFrame.setVisible(true);
	}

	private void loadOutPatientPlanRecords() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<OutPatientPlanRecord> entities = outPatientPlanDomainService
				.listPlanRecord(pageable);

		OutPatientPlanRecordTableModel tableModel = outPatientPlanRecordFrame
				.getRecordTableModel();
		tableModel.clear();
		tableModel.addEntities(entities);
	}

	private void openCreateOutPatientPlanRecord() {

		loadDoctors();
		loadVoucherTypes();
		loadOutPatientRooms();

		createOutPatientPlanRecordFrame.setVisible(true);
	}

	private void loadDoctors() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<Doctor> doctors = this.userDomainService.findDoctor(pageable);

		DoctorComboBoxModel doctorComboBoxModel = createOutPatientPlanRecordFrame
				.getDoctorComboBoxModel();
		doctorComboBoxModel.clear();

		doctorComboBoxModel.addElements(doctors);
	}

	private void loadVoucherTypes() {

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<VoucherType> voucherTypes = this.outPatientPlanAdminDomainService
				.findVoucherTypes(pageable);

		VoucherTypeComboBoxModel voucherTypeComboBoxModel = createOutPatientPlanRecordFrame
				.getVoucherTypeComboBoxModel();
		voucherTypeComboBoxModel.clear();

		voucherTypeComboBoxModel.addElements(voucherTypes);
	}

	private void loadOutPatientRooms() {

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<OutPatientRoom> outPatientRooms = this.outPatientPlanAdminDomainService
				.findOutPatientRooms(pageable);

		OutPatientRoomComboBoxModel outPatientRoomComboBoxModel = createOutPatientPlanRecordFrame
				.getOutPatientRoomComboBoxModel();
		outPatientRoomComboBoxModel.clear();

		outPatientRoomComboBoxModel.addElements(outPatientRooms);
	}

	private void createOutPatientPlanRecord() {
		OutPatientPlanRecord planRecord = createOutPatientPlanRecordFrame
				.getOutPatientPlanRecord();
		outPatientPlanDomainService.createPlanRecord(planRecord);
		loadOutPatientPlanRecords();
		createOutPatientPlanRecordFrame.dispose();
	}

	private void closeWindow() {
		outPatientPlanRecordFrame.dispose();
	}

}
