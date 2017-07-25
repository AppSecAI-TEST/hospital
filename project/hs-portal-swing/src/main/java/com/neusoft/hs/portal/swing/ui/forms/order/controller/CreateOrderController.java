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
import com.neusoft.hs.application.outpatientdept.OutPatientDeptAppService;
import com.neusoft.hs.domain.order.DrugOrderType;
import com.neusoft.hs.domain.order.DrugOrderTypeApp;
import com.neusoft.hs.domain.order.EnterHospitalOrderType;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderAdminDomainService;
import com.neusoft.hs.domain.order.OrderFrequencyType;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientRoom;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.PharmacyAdminService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.portal.businessview.visit.VisitBusinessView;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.order.view.CreateOrderFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.DrugUseModeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderFrequencyTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.PharmacyComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class CreateOrderController extends AbstractFrameController {

	@Autowired
	private CreateOrderFrame createOrderFrame;

	@Autowired
	private OrderAppService orderAppService;

	@Autowired
	private OrderAdminDomainService orderAdminDomainService;

	@Autowired
	private PharmacyAdminService pharmacyAdminService;

	@Autowired
	private OrganizationAdminDomainService organizationAdminDomainService;

	@Autowired
	private OutPatientDeptAppService outPatientDeptAppService;

	@Autowired
	private VisitBusinessView visitBusinessView;

	private VisitComboBoxModel visitComboBoxModel;

	private OrderFrequencyTypeComboBoxModel frequencyTypeComboBoxModel;

	private DrugUseModeComboBoxModel orderUseModeComboBoxModel;

	private OrderTypeComboBoxModel orderTypeComboBoxModel;

	private PharmacyComboBoxModel pharmacyComboBoxModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(createOrderFrame.getConfirmBtn(), (e) -> create());
		registerAction(createOrderFrame.getCloseBtn(), (e) -> closeWindow());

	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadOrders();
		loadVisits();
		loadOrderTypes();
		loadFrequencyTypes();
		loadPharmacys();
		loadOrderUseModes();

		createOrderFrame.setVisible(true);
	}

	private void loadOrders() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		AbstractUser user = UserUtil.getUser();

		List<Dept> depts = new ArrayList<Dept>();
		depts.add(user.getDept());

		OutPatientRoom outPatientRoom = outPatientDeptAppService
				.findOutPatientRoom(user, DateUtil.getSysDate());
		if (outPatientRoom != null) {
			depts.add(outPatientRoom);
		}

		List<Order> entities = orderAppService.findByBelongDepts(depts,
				pageable);

		OrderTableModel orderTableModel = createOrderFrame.getOrderListPanel()
				.getOrderTableModel();
		orderTableModel.clear();
		orderTableModel.addEntities(entities);
	}

	private void loadVisits() throws HsException {

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		AbstractUser user = UserUtil.getUser();

		List<Visit> entities = visitBusinessView.findVisits(user, pageable);

		visitComboBoxModel = this.createOrderFrame.getCreateOrderPanel()
				.getVisitComboBoxModel();
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

		orderTypeComboBoxModel = this.createOrderFrame.getCreateOrderPanel()
				.getOrderTypeComboBoxModel();

		orderTypeComboBoxModel.clear();
		orderTypeComboBoxModel.addElements(orderTypes);
	}

	private void loadFrequencyTypes() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<OrderFrequencyType> entities = orderAdminDomainService
				.findFrequencyType(pageable);

		frequencyTypeComboBoxModel = this.createOrderFrame
				.getCreateOrderPanel().getFrequencyTypeComboBoxModel();
		frequencyTypeComboBoxModel.clear();
		frequencyTypeComboBoxModel.addElement(null);
		frequencyTypeComboBoxModel.addElements(entities);
	}

	private void loadPharmacys() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<Pharmacy> pharmacys = pharmacyAdminService.findPharmacy(pageable);

		pharmacyComboBoxModel = this.createOrderFrame.getCreateOrderPanel()
				.getPharmacyComboBoxModel();
		pharmacyComboBoxModel.clear();
		pharmacyComboBoxModel.addElement(null);
		pharmacyComboBoxModel.addElements(pharmacys);
	}

	private void loadOrderUseModes() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<DrugUseMode> entities = pharmacyAdminService
				.findDrugUseMode(pageable);

		orderUseModeComboBoxModel = this.createOrderFrame.getCreateOrderPanel()
				.getOrderUseModeComboBoxModel();
		orderUseModeComboBoxModel.clear();
		orderUseModeComboBoxModel.addElement(null);
		orderUseModeComboBoxModel.addElements(entities);
	}

	private void create() {
		try {

			AbstractUser user = UserUtil.getUser();

			OrderFrequencyType frequencyType = frequencyTypeComboBoxModel
					.getSelectedItem();

			Date planStartDate = this.createOrderFrame.getPlanStartDate();
			Integer executeDay = this.createOrderFrame.getExecuteDay();

			Integer count = this.createOrderFrame.getCount();

			OrderType orderType = orderTypeComboBoxModel.getSelectedItem();

			Order order = null;
			if (frequencyType == null) {
				order = new TemporaryOrder();
				order.setPlanStartDate(planStartDate);
			} else {
				order = new LongOrder();
				LongOrder longOrder = (LongOrder) order;
				longOrder.setFrequencyType(frequencyType);
				longOrder.setPlanStartDate(planStartDate);
				if (executeDay != null) {
					longOrder.setPlanEndDate(DateUtil.addDay(
							longOrder.getPlanStartDate(), executeDay));
				}
			}
			order.setVisit(visitComboBoxModel.getSelectedItem());
			order.setName(orderType.getName());
			order.setOrderType(orderType);
			order.setCount(count);

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
			} else if (orderType instanceof EnterHospitalOrderType) {
				InPatientDept dept = (InPatientDept) user.getDept();
				order.addParam(EnterHospitalOrderType.WardDept, dept);
				order.addParam(EnterHospitalOrderType.RespDoctor, user);
				order.addParam(EnterHospitalOrderType.WardArea,
						organizationAdminDomainService.findInPatientArea(dept)
								.get(0));
				order.setExecuteDept(dept);
			}

			orderAppService.create(order, (Doctor) UserUtil.getUser());

			loadOrders();
		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void closeWindow() {
		createOrderFrame.dispose();
	}
}
