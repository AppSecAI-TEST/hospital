package com.neusoft.hs.portal.swing.ui.forms.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.order.OrderAppService;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderAdminDomainService;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.order.view.OrderFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class OrderController extends AbstractFrameController {

	@Autowired
	private OrderFrame orderFrame;

	@Autowired
	private OrderAppService orderAppService;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private OrderAdminDomainService orderAdminDomainService;

	@Autowired
	private VisitComboBoxModel visitComboBoxModel;

	@Autowired
	private OrderTypeComboBoxModel orderTypeComboBoxModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(orderFrame.getConfirmBtn(), (e) -> create());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadVisits();
		loadOrderTypes();
		orderFrame.setVisible(true);
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = visitDomainService.findByStateAndDept(
				Visit.State_IntoWard, UserUtil.getUser().getDept(), pageable);

		visitComboBoxModel.clear();
		visitComboBoxModel.addElements(entities);
	}

	private void loadOrderTypes() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Dept> depts = new ArrayList<Dept>();
		Dept dept = UserUtil.getUser().getDept();
		depts.add(dept);

		List<OrderType> orderTypes = this.orderAdminDomainService
				.findOrderType(pageable);
		orderTypeComboBoxModel.clear();
		orderTypeComboBoxModel.addElements(orderTypes);
	}

	private void create() {
		try {
			// Visit visit = receiveVisitFrame.getSelectedVisit();
			// String bed = receiveVisitFrame.getBed();
			// Nurse respNurse = receiveVisitFrame.getNurse();

			Order order = new LongOrder();
			// order.setVisit(visit004);
			// order.setName("二级护理");
			// order.setFrequencyType(orderFrequencyType_0H);
			// order.setPlanStartDate(DateUtil.getSysDateStart());
			// order.setPlaceType(OrderCreateCommand.PlaceType_InPatient);
			// order.setOrderType(secondNursingOrderType);

			orderAppService.create(order, (Doctor) UserUtil.getUser());

			// loadOrders();

			// orderFrame.clearBed();

		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}
}
