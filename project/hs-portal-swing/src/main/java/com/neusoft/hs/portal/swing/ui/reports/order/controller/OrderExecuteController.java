package com.neusoft.hs.portal.swing.ui.reports.order.controller;

import java.awt.event.ItemEvent;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class OrderExecuteController extends AbstractFrameController {

	@Autowired
	private OrderExecuteFrame orderExecuteFrame;

	@Autowired
	private OrderExecuteAdminService orderExecuteAdminService;
	
	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	private OrderExecuteTableModel orderExecuteTableModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(orderExecuteFrame.getNextPageBtn(), (e) -> nextPage());
		registerAction(orderExecuteFrame.getVisitCB(), (e) -> refreshOrderExecute(e));
		registerAction(orderExecuteFrame.getCloseBtn(), (e) -> closeWindow());
		
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadVisits();
		
		orderExecuteTableModel = this.orderExecuteFrame.getOrderExecuteTableModel();
		orderExecuteTableModel.clear();
		
		orderExecuteFrame.setVisible(true);
	}
	
	public void nextPage() {
		this.orderExecuteFrame.nextPage();
		try {
			this.loadVisits();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(this.orderExecuteFrame.getPageNumber(),
				15);

		List<Visit> entities = visitAdminDomainService.find(pageable);

		VisitComboBoxModel visitComboBoxModel = this.orderExecuteFrame.getVisitComboBoxModel();
		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	public void refreshOrderExecute(ItemEvent e) {
		Visit visit = (Visit) e.getItem();

		orderExecuteTableModel.clear();

		if (visit != null) {
			Sort sort = new Sort("planStartDate");
			Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, sort);
			List<OrderExecute> entities = orderExecuteAdminService
					.find(visit, pageable);

			orderExecuteTableModel.addEntities(entities);
		}
	}
	
	private void closeWindow() {
		orderExecuteFrame.dispose();
	}
}
