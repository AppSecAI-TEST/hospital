package com.neusoft.hs.portal.swing.ui.forms.outpatientoffice.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanDomainService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.forms.outpatientoffice.view.OutPatientPlanRecordFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.OutPatientPlanRecordTableModel;

@Controller
public class OutPatientPlanRecordController extends AbstractFrameController {

	@Autowired
	private OutPatientPlanDomainService outPatientPlanDomainService;

	@Autowired
	private OutPatientPlanRecordFrame outPatientPlanRecordFrame;

	@PostConstruct
	private void prepareListeners() {
		registerAction(outPatientPlanRecordFrame.getAddBtn(),
				(e) -> createOutPatientPlanRecord());
		registerAction(outPatientPlanRecordFrame.getCloseBtn(),
				(e) -> closeWindow());
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

	private void createOutPatientPlanRecord() {

	}

	private void closeWindow() {
		outPatientPlanRecordFrame.dispose();
	}

}
