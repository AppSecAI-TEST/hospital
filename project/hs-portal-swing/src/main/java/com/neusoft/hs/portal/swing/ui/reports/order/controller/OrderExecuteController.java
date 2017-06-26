package com.neusoft.hs.portal.swing.ui.reports.order.controller;

import java.awt.event.ItemEvent;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteAdminService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.reports.order.view.OrderExecuteFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderExecuteTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;

@Controller
public class OrderExecuteController extends AbstractFrameController {

	@Autowired
	private OrderExecuteFrame orderExecuteFrame;

	@Autowired
	private OrderExecuteAdminService orderExecuteAdminService;
	
	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@Autowired
	private OrderExecuteTableModel orderExecuteTableModel;
	
	@Autowired
	private VisitComboBoxModel visitComboBoxModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(orderExecuteFrame.getVisitCB(), (e) -> refreshOrderExecute(e));
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadVisits();
		orderExecuteTableModel.clear();
		
		orderExecuteFrame.setVisible(true);
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = visitAdminDomainService.find(pageable);

		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	public void refreshOrderExecute(ItemEvent e) {
		Visit visit = (Visit) e.getItem();

		orderExecuteTableModel.clear();

		if (visit != null) {
			Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
			List<OrderExecute> entities = orderExecuteAdminService
					.find(visit, pageable);

			orderExecuteTableModel.addEntities(entities);
		}
	}
}
