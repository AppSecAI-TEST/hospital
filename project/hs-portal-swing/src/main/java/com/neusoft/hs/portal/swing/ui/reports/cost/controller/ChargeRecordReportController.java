package com.neusoft.hs.portal.swing.ui.reports.cost.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.reports.cost.view.ChargeRecordReportFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;

@Controller
public class ChargeRecordReportController extends AbstractFrameController {

	@Autowired
	private ChargeRecordReportFrame chargeRecordReportFrame;

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@Autowired
	private VisitComboBoxModel visitComboBoxModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(chargeRecordReportFrame.getVisitCB(),
				(e) -> refreshChargeRecord());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadVisits();

		chargeRecordReportFrame.setVisible(true);
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = visitAdminDomainService.find(pageable);

		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	public void refreshChargeRecord() {

	}

}
