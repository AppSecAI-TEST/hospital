package com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.medicalrecord.MedicalRecordAppService;
import com.neusoft.hs.domain.inspect.InspectDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordAdminDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordBuilder;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordTypeBuilder;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.view.ArrangementMedicalRecordFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class ArrangementMedicalRecordController extends AbstractFrameController {

	@Autowired
	private ArrangementMedicalRecordFrame arrangementMedicalRecordFrame;

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@Autowired
	private InspectDomainService inspectDomainService;

	@Autowired
	private MedicalRecordAppService medicalRecordAppService;

	@Autowired
	private MedicalRecordAdminDomainService medicalRecordAdminDomainService;

	@PostConstruct
	private void prepareListeners() {
		registerAction(
				arrangementMedicalRecordFrame.getCreateTemporaryOrderListBtn(),
				(e) -> createTemporaryOrderListMR());
		registerAction(
				arrangementMedicalRecordFrame.getCreateInspectResultBtn(),
				(e) -> createInspectResultMR());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {

		loadVisits();

		arrangementMedicalRecordFrame.setVisible(true);
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = visitAdminDomainService.find(pageable);

		VisitComboBoxModel visitComboBoxModel = arrangementMedicalRecordFrame
				.getVisitComboBoxModel();
		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	private void createInspectResultMR() {
		Visit visit = this.getVisit();
		if (visit == null) {
			Notifications.showFormValidationAlert("请选择患者");
		}

	}

	private void createTemporaryOrderListMR() {

		Visit visit = this.getVisit();
		if (visit == null) {
			Notifications.showFormValidationAlert("请选择患者");
		}

		try {
			this.createMedicalRecord("临时医嘱单", visit);
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}

	}

	
	private MedicalRecord createMedicalRecord(String typeId, Visit visit)
			throws HsException {

		MedicalRecordType type = medicalRecordAdminDomainService
				.getMedicalRecordType(typeId);

		MedicalRecordBuilder builder = new MedicalRecordTypeBuilder(type, visit);

		MedicalRecord medicalRecord = medicalRecordAppService.create(builder,
				visit, type, UserUtil.getUser());

		medicalRecordAppService.save(medicalRecord);

		return medicalRecord;
	}

	private Visit getVisit() {
		return arrangementMedicalRecordFrame.getVisitComboBoxModel()
				.getSelectedItem();
	}

}
