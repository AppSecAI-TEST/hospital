package com.neusoft.hs.data.batch;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.order.OrderAppService;
import com.neusoft.hs.application.organization.UserAdminAppService;
import com.neusoft.hs.domain.order.DrugOrderType;
import com.neusoft.hs.domain.order.DrugOrderTypeDAO;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderDomainService;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.order.TemporaryDrugOrderBuilder;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.domain.pharmacy.DrugTypeSpec;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.PharmacyAdminService;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.log.LogUtil;

@Service
public class OrderBatchDataService {

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private OrganizationAdminDomainService organizationAdminDomainService;

	@Autowired
	private UserAdminAppService userAdminAppService;

	@Autowired
	private OrderAppService orderAppService;

	@Autowired
	private OrderDomainService orderDomainService;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	@Autowired
	private PharmacyAdminService pharmacyAdminService;

	@Autowired
	private PharmacyDomainService pharmacyDomainService;

	@Autowired
	private DrugOrderTypeDAO drugOrderTypeDAO;

	private Random randomVisit;

	private Doctor doctor002;

	private Nurse nurse003;

	private Staff userc01;

	private Staff userc03;

	public final static int OrderCount = 1;

	public void init() throws HsException {

		randomVisit = new Random();

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		doctor002 = userAdminAppService.findDoctor("doctor002");

		nurse003 = userAdminAppService.findNurse("nurse003");

		userc01 = userAdminAppService.findStaff("staffc01");

		userc03 = userAdminAppService.findStaff("staffc03");

		Pharmacy deptccc = (Pharmacy) organizationAdminDomainService
				.findTheDept("deptccc");

		List<Visit> visits = visitDomainService.findByState(
				Visit.State_IntoWard, pageable);
		int visitCount = visits.size();

		DrugOrderType drugOrderType001 = drugOrderTypeDAO
				.findDrugOrderType("drugOrderType001");

		DrugUseMode oralOrderUseMode = pharmacyAdminService
				.findDrugUseMode("oralOrderUseMode");

		readyDrugType();
		
		TemporaryDrugOrderBuilder drugOrderBuilder;

		List<Order> orders;

		for (int i = 0; i < OrderCount; i++) {
			
			LogUtil.log(OrderBatchDataService.class, "startCreateBatchOrder");

			drugOrderBuilder = new TemporaryDrugOrderBuilder();
			drugOrderBuilder.setVisit(visits.get(randomVisit
					.nextInt(visitCount - 1)));
			drugOrderBuilder.setCount(2);
			drugOrderBuilder
					.setPlaceType(OrderCreateCommand.PlaceType_InPatient);
			drugOrderBuilder.setOrderType(drugOrderType001);
			drugOrderBuilder.setPharmacy(deptccc);
			drugOrderBuilder.setDrugUseMode(oralOrderUseMode);

			orders = orderAppService.create(drugOrderBuilder, doctor002);
			
			LogUtil.log(OrderBatchDataService.class, "endCreateBatchOrder");

			for (Order order : orders) {
				this.executeOrder(order);
			}
			
			LogUtil.log(OrderBatchDataService.class, "endExecuteBatchOrder");
		}
	}

	public void executeOrder(Order order) throws OrderException,
			OrderExecuteException {

		orderAppService.verify(order.getId(), nurse003);

		Sort sort = new Sort("teamSequence");
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, sort);

		List<OrderExecute> orderExecutes = orderExecuteDomainService
				.findByOrder(order.getId(), pageable);

		orderExecuteAppService.send(orderExecutes.get(0).getId(), nurse003);

		orderExecuteAppService.finish(orderExecutes.get(0).getId(), null,
				userc01);

		orderExecuteAppService.finish(orderExecutes.get(1).getId(), null,
				userc03);
	}

	@Transactional(rollbackFor = Exception.class)
	public void readyDrugType() {
		DrugTypeSpec drugTypeSpec001 = pharmacyDomainService
				.findDrugTypeSpec("drugTypeSpec001");
		List<DrugType> drugTypes = pharmacyDomainService
				.findByDrugTypeSpec(drugTypeSpec001);
		for (DrugType drugType : drugTypes) {
			drugType.setStock(OrderCount * 2);
			drugType.save();
		}
	}
}
