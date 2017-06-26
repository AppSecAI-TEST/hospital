package com.neusoft.hs.portal.swing.ui.reports.order.controller;

import java.awt.event.ItemEvent;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderAdminDomainService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.reports.order.view.OrderFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;

@Controller
public class OrderController extends AbstractFrameController {

	@Autowired
	private OrderFrame orderFrame;

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@Autowired
	private OrderAdminDomainService orderAdminDomainService;

	@Autowired
	private OrderTableModel orderTableModel;

	@Autowired
	private VisitComboBoxModel visitComboBoxModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(orderFrame.getVisitCB(), (e) -> refreshOrder(e));
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadVisits();

		orderFrame.setVisible(true);
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = visitAdminDomainService.find(pageable);

		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	public void refreshOrder(ItemEvent e) {
		Visit visit = (Visit) e.getItem();

		orderTableModel.clear();

		if (visit != null) {
			Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
			List<Order> entities = orderAdminDomainService
					.find(visit, pageable);

			orderTableModel.addEntities(entities);
		}
	}

}
