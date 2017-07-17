package com.neusoft.hs.portal.swing.ui.reports.visit.controller;

import java.awt.event.ItemEvent;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.domain.visit.VisitLog;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.reports.visit.view.VisitLogFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitLogTableModel;

@Controller
public class VisitLogController extends AbstractFrameController {

	@Autowired
	private VisitLogFrame visitLogFrame;

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	private VisitLogTableModel visitLogTableModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(visitLogFrame.getVisitCB(), (e) -> refreshVisitLog(e));
		registerAction(visitLogFrame.getCloseBtn(), (e) -> closeWindow());

	}

	@Override
	public void prepareAndOpenFrame() throws HsException {

		loadVisits();

		visitLogTableModel = this.visitLogFrame.getVisitLogTableModel();
		visitLogTableModel.clear();

		visitLogFrame.setVisible(true);
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = visitAdminDomainService.find(pageable);

		VisitComboBoxModel visitComboBoxModel = visitLogFrame
				.getVisitComboBoxModel();

		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	public void refreshVisitLog(ItemEvent e) {
		Visit visit = (Visit) e.getItem();

		visitLogTableModel.clear();

		if (visit != null) {

			visitLogFrame.getStateTF().setText(visit.getState());

			Sort sort = new Sort("createDate");
			Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, sort);

			List<VisitLog> entities = visitAdminDomainService.getVisitLogs(
					visit, pageable);

			visitLogTableModel.addEntities(entities);
		}
	}

	private void closeWindow() {
		visitLogFrame.dispose();
	}
}
