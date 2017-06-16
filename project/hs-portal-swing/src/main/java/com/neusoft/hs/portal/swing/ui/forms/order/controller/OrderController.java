package com.neusoft.hs.portal.swing.ui.forms.order.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.neusoft.hs.domain.order.OrderFrequencyType;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.PharmacyAdminService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.portal.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.order.view.OrderFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.DrugUseModeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderFrequencyTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.StringComboBoxModel;
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
	private PharmacyAdminService pharmacyAdminService;

	@Autowired
	private OrderTableModel orderTableModel;

	@Autowired
	private VisitComboBoxModel visitComboBoxModel;

	@Autowired
	private StringComboBoxModel placeTypeComboBoxModel;

	@Autowired
	private OrderFrequencyTypeComboBoxModel frequencyTypeComboBoxModel;

	@Autowired
	private DrugUseModeComboBoxModel orderUseModeComboBoxModel;

	@Autowired
	private OrderTypeComboBoxModel orderTypeComboBoxModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(orderFrame.getConfirmBtn(), (e) -> create());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadOrders();
		loadVisits();
		loadOrderTypes();
		loadPlaceTypes();
		loadFrequencyTypes();
		loadOrderUseModes();

		orderFrame.setVisible(true);
	}

	private void loadOrders() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Order> entities = orderAppService.findByBelongDept(UserUtil
				.getUser().getDept(), pageable);

		orderTableModel.clear();
		orderTableModel.addEntities(entities);
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

	private void loadPlaceTypes() {

		List<String> placeTypes = new ArrayList<String>();
		placeTypes.add(Order.PlaceType_InPatient);
		placeTypes.add(Order.PlaceType_OutPatient);

		placeTypeComboBoxModel.clear();
		placeTypeComboBoxModel.addElements(placeTypes);
	}

	private void loadFrequencyTypes() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<OrderFrequencyType> entities = orderAdminDomainService
				.findFrequencyType(pageable);

		frequencyTypeComboBoxModel.clear();
		frequencyTypeComboBoxModel.addElement(null);
		frequencyTypeComboBoxModel.addElements(entities);
	}

	private void loadOrderUseModes() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<DrugUseMode> entities = pharmacyAdminService
				.findDrugUseMode(pageable);

		orderUseModeComboBoxModel.clear();
		orderUseModeComboBoxModel.addElement(null);
		orderUseModeComboBoxModel.addElements(entities);
	}

	private void create() {
		try {
			// Visit visit = receiveVisitFrame.getSelectedVisit();
			// String bed = receiveVisitFrame.getBed();
			// Nurse respNurse = receiveVisitFrame.getNurse();

			OrderFrequencyType frequencyType = frequencyTypeComboBoxModel
					.getSelectedItem();

			Date planStartDate = this.orderFrame.getPlanStartDate();
			if (planStartDate == null) {
				planStartDate = DateUtil.getSysDateStart();
			}
			if (frequencyType == null) {
				
				TemporaryOrder order = new TemporaryOrder();
				order.setVisit(visitComboBoxModel.getSelectedItem());
				order.setName(orderTypeComboBoxModel.getSelectedItem().getName());
				order.setOrderType(orderTypeComboBoxModel.getSelectedItem());
				order.setPlanStartDate(planStartDate);
				order.setPlaceType(placeTypeComboBoxModel.getSelectedItem());
				
				orderAppService.create(order, (Doctor) UserUtil.getUser());
			} else {
				
				LongOrder order = new LongOrder();
				order.setVisit(visitComboBoxModel.getSelectedItem());
				order.setName(orderTypeComboBoxModel.getSelectedItem().getName());
				order.setOrderType(orderTypeComboBoxModel.getSelectedItem());
				order.setFrequencyType(frequencyType);
				order.setPlanStartDate(planStartDate);
				order.setPlaceType(placeTypeComboBoxModel.getSelectedItem());
				
				orderAppService.create(order, (Doctor) UserUtil.getUser());
			}

			loadOrders();

			// orderFrame.clearBed();

		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}
}
