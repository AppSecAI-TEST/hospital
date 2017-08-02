package com.neusoft.hs.data.batch;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.order.OrderAppService;
import com.neusoft.hs.application.organization.UserAdminAppService;
import com.neusoft.hs.domain.order.DrugOrderType;
import com.neusoft.hs.domain.order.DrugOrderTypeDAO;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderExecute;
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
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Service
public class OrderBatchDataService {

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@Autowired
	private OrganizationAdminDomainService organizationAdminDomainService;

	@Autowired
	private UserAdminAppService userAdminAppService;

	@Autowired
	private OrderAppService orderAppService;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private PharmacyAdminService pharmacyAdminService;

	@Autowired
	private PharmacyDomainService pharmacyDomainService;

	@Autowired
	private DrugOrderTypeDAO drugOrderTypeDAO;

	private Random randomVisit;

	public final static int OrderCount = 5000;

	public void init() throws HsException {

		TemporaryDrugOrderBuilder drugOrderBuilder;

		randomVisit = new Random();

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		Doctor doctor002 = userAdminAppService.findDoctor("doctor002");

		Nurse nurse003 = userAdminAppService.findNurse("nurse003");

		Staff userc01 = userAdminAppService.findStaff("userc01");

		Staff userc03 = userAdminAppService.findStaff("userc03");

		Pharmacy deptccc = (Pharmacy) organizationAdminDomainService
				.findTheDept("deptccc");

		List<Visit> visits = visitAdminDomainService.find(pageable);
		int visitCount = visits.size();

		DrugOrderType drugOrderType001 = drugOrderTypeDAO
				.findDrugOrderType("drugOrderType001");

		DrugUseMode oralOrderUseMode = pharmacyAdminService
				.findDrugUseMode("oralOrderUseMode");

		readyDrugType();

		List<Order> orders;

		for (int i = 0; i < OrderCount; i++) {

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

			List<OrderExecute> orderExecutes;
			for (Order order : orders) {
				orderAppService.verify(order.getId(), nurse003);

				orderExecutes = orderExecuteAppService
						.getNeedSendOrderExecutes(nurse003, pageable);

				for (OrderExecute orderExecute : orderExecutes) {
					orderExecuteAppService.send(orderExecute.getId(), nurse003);
				}

				orderExecutes = orderExecuteAppService
						.getNeedExecuteOrderExecutes(userc01, pageable);

				for (OrderExecute orderExecute : orderExecutes) {
					orderExecuteAppService.finish(orderExecute.getId(), null,
							userc01);
				}

				orderExecutes = orderExecuteAppService
						.getNeedExecuteOrderExecutes(userc03, pageable);

				for (OrderExecute orderExecute : orderExecutes) {
					orderExecuteAppService.finish(orderExecute.getId(), null,
							userc03);
				}

			}
		}
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

	@Transactional(rollbackFor = Exception.class)
	private void create() {

	}

}
