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
import com.neusoft.hs.domain.order.DrugOrderType;
import com.neusoft.hs.domain.order.DrugOrderTypeApp;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderAdminDomainService;
import com.neusoft.hs.domain.order.OrderFrequencyType;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.PharmacyAdminService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.order.view.CreateOrderFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.DrugUseModeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderFrequencyTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.PharmacyComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.PlaceTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class OrderController extends AbstractFrameController {

	@Autowired
	private CreateOrderFrame orderFrame;

	@Autowired
	private OrderAppService orderAppService;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private OrderAdminDomainService orderAdminDomainService;

	@Autowired
	private PharmacyAdminService pharmacyAdminService;

	@Autowired
	private OrganizationAdminDomainService organizationDomainService;

	@Autowired
	private OrderTableModel orderTableModel;

	@Autowired
	private VisitComboBoxModel visitComboBoxModel;

	@Autowired
	private PlaceTypeComboBoxModel placeTypeComboBoxModel;

	@Autowired
	private OrderFrequencyTypeComboBoxModel frequencyTypeComboBoxModel;

	@Autowired
	private DrugUseModeComboBoxModel orderUseModeComboBoxModel;

	@Autowired
	private OrderTypeComboBoxModel orderTypeComboBoxModel;

	@Autowired
	private PharmacyComboBoxModel pharmacyComboBoxModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(orderFrame.getConfirmBtn(), (e) -> create());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadOrders();
		loadVisits();
		loadOrderTypes();
		loadFrequencyTypes();
		loadPharmacys();
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

	private void loadFrequencyTypes() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<OrderFrequencyType> entities = orderAdminDomainService
				.findFrequencyType(pageable);

		frequencyTypeComboBoxModel.clear();
		frequencyTypeComboBoxModel.addElement(null);
		frequencyTypeComboBoxModel.addElements(entities);
	}

	private void loadPharmacys() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<Pharmacy> pharmacys = pharmacyAdminService.findPharmacy(pageable);

		pharmacyComboBoxModel.clear();
		pharmacyComboBoxModel.addElement(null);
		pharmacyComboBoxModel.addElements(pharmacys);
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

			OrderFrequencyType frequencyType = frequencyTypeComboBoxModel
					.getSelectedItem();

			Date planStartDate = this.orderFrame.getPlanStartDate();

			Integer count = this.orderFrame.getCount();

			OrderType orderType = orderTypeComboBoxModel.getSelectedItem();

			Order order = null;
			if (frequencyType == null) {
				order = new TemporaryOrder();
			} else {
				order = new LongOrder();
				LongOrder longOrder = (LongOrder) order;
				longOrder.setFrequencyType(frequencyType);
			}
			order.setVisit(visitComboBoxModel.getSelectedItem());
			order.setName(orderType.getName());
			order.setOrderType(orderType);
			order.setPlanStartDate(planStartDate);
			order.setCount(count);
			order.setPlaceType(placeTypeComboBoxModel.getSelectedItem());

			if (orderType instanceof DrugOrderType) {
				Pharmacy pharmacy = pharmacyComboBoxModel.getSelectedItem();
				if (pharmacy == null) {
					throw new UIException("请选择药房");
				}
				DrugUseMode drugUseMode = orderUseModeComboBoxModel
						.getSelectedItem();
				if (drugUseMode == null) {
					throw new UIException("请选择药品用法");
				}
				if (order.getCount() == null) {
					throw new UIException("请录入数量");
				}

				order.setTypeApp(new DrugOrderTypeApp(pharmacy, drugUseMode));
			}

			orderAppService.create(order, (Doctor) UserUtil.getUser());

			loadOrders();

			// orderFrame.clearBed();

		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}
}
