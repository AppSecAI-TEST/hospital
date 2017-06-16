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
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.portal.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.order.view.OrderFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class OrderController extends AbstractFrameController {
	
	@Autowired
	private OrderFrame orderFrame;
	
	@Autowired
	private OrderAppService orderAppService;
	
	@PostConstruct
	private void prepareListeners() {
		registerAction(orderFrame.getConfirmBtn(), (e) -> create());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
//		loadReceiveVisits();
//		loadNurses();
		orderFrame.setVisible(true);
	}

//	private void loadReceiveVisits() throws HsException {
//		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
//
//		List<Visit> entities = inPatientAppService.getNeedReceiveVisits(
//				UserUtil.getUser(), pageable);
//
//		tableModel.clear();
//		tableModel.addEntities(entities);
//	}
//
//	private void loadNurses() throws HsException {
//		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
//		
//		List<Dept> depts = new ArrayList<Dept>();
//		Dept dept = UserUtil.getUser().getDept();
//		depts.add(dept);
//		
//		List<Nurse> nurses = this.userAdminDomainService.findNurse(depts, pageable);
//		respNurseComboBoxModel.clear();
//		respNurseComboBoxModel.addElements(nurses);
//	}

	private void create() {
		try {
//			Visit visit = receiveVisitFrame.getSelectedVisit();
//			String bed = receiveVisitFrame.getBed();
//			Nurse respNurse = receiveVisitFrame.getNurse();

			Order order = new LongOrder();
//			order.setVisit(visit004);
//			order.setName("二级护理");
//			order.setFrequencyType(orderFrequencyType_0H);
//			order.setPlanStartDate(DateUtil.getSysDateStart());
//			order.setPlaceType(OrderCreateCommand.PlaceType_InPatient);
//			order.setOrderType(secondNursingOrderType);

			orderAppService.create(order, (Doctor)UserUtil.getUser());

			//loadOrders();
			
			//orderFrame.clearBed();

		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}
}
