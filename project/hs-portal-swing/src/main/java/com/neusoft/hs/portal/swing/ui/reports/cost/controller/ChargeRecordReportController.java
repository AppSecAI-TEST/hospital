package com.neusoft.hs.portal.swing.ui.reports.cost.controller;

import java.awt.event.ItemEvent;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.cost.ChargeBill;
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

	private ChargeRecordTableModel chargeRecordTableModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(chargeRecordReportFrame.getVisitCB(),
				(e) -> refreshChargeRecord(e));
		registerAction(chargeRecordReportFrame.getCloseBtn(),
				(e) -> closeWindow());

	}

	@Override
	public void prepareAndOpenFrame() throws HsException {

		loadVisits();

		chargeRecordTableModel = this.chargeRecordReportFrame
				.getChargeRecordTableModel();
		chargeRecordTableModel.clear();

		chargeRecordReportFrame.setVisible(true);
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = visitAdminDomainService.find(pageable);

		VisitComboBoxModel visitComboBoxModel = chargeRecordReportFrame
				.getVisitComboBoxModel();

		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	public void refreshChargeRecord(ItemEvent e) {
		Visit visit = (Visit) e.getItem();

		chargeRecordTableModel.clear();

		if (visit != null) {
			Sort sort = new Sort("createDate");
			Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, sort);

			List<ChargeRecord> entities = costDomainService.getChargeRecords(
					visit, pageable);

			chargeRecordTableModel.addEntities(entities);

			ChargeBill chargeBill = costDomainService.getChargeBill(visit);

			String chargeBillInfo = "收费单：余额[" + chargeBill.getBalance()
					+ "],消费[" + chargeBill.getConsume() + "]";
			chargeRecordReportFrame.getChargeBillLbl().setText(chargeBillInfo);
		}
	}

	private void closeWindow() {
		chargeRecordReportFrame.dispose();
	}
}
