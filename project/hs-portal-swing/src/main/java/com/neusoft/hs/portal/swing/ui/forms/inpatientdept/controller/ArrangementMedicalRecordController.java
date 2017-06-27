package com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.view.ArrangementMedicalRecordFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;

@Controller
public class ArrangementMedicalRecordController extends AbstractFrameController {

	@Autowired
	private ArrangementMedicalRecordFrame arrangementMedicalRecordFrame;

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

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

		VisitComboBoxModel visitComboBoxModel = arrangementMedicalRecordFrame.getVisitComboBoxModel();
		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	private void createInspectResultMR() {
	}

	private void createTemporaryOrderListMR() {
	}

}
