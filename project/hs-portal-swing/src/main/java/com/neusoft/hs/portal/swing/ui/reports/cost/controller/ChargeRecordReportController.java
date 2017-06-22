package com.neusoft.hs.portal.swing.ui.reports.cost.controller;

import java.awt.event.ItemEvent;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.reports.cost.view.ChargeRecordReportFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.ChargeRecordTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;

@Controller
public class ChargeRecordReportController extends AbstractFrameController {

	@Autowired
	private ChargeRecordReportFrame chargeRecordReportFrame;

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@Autowired
	private CostDomainService costDomainService;

	@Autowired
	private VisitComboBoxModel visitComboBoxModel;

	@Autowired
	private ChargeRecordTableModel chargeRecordTableModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(chargeRecordReportFrame.getVisitCB(),
				(e) -> refreshChargeRecord(e));
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

	@Transactional(rollbackFor = Exception.class)
	public void refreshChargeRecord(ItemEvent e) {
		Visit visit = (Visit) e.getItem();

		chargeRecordTableModel.clear();

		if (visit != null) {
			List<ChargeRecord> entities = costDomainService
					.getChargeRecords(visit);
			
			chargeRecordTableModel.addEntities(entities);
		}
	}

}
