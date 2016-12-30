package com.neusoft.hs.application;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.cashier.CashierAppService;
import com.neusoft.hs.application.inpatientdept.InPatientAppService;
import com.neusoft.hs.application.inpatientdept.OrderAppService;
import com.neusoft.hs.application.register.RegisterAppService;
import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.order.DrugOrderType;
import com.neusoft.hs.domain.order.LeaveHospitalOrderType;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderDomainService;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.SecondNursingOrderType;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Org;
import com.neusoft.hs.domain.organization.OrganizationDomainService;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.organization.Unit;
import com.neusoft.hs.domain.organization.UserDomainService;
import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.domain.pharmacy.DrugTypeSpec;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;
import com.neusoft.hs.domain.visit.ReceiveVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class AppTestService {

	@Autowired
	private RegisterAppService registerAppService;

	@Autowired
	private CashierAppService cashierAppService;

	@Autowired
	private InPatientAppService inPatientAppService;

	@Autowired
	private OrderAppService orderAppService;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private OrganizationDomainService organizationDomainService;

	@Autowired
	private UserDomainService userDomainService;

	@Autowired
	private CostDomainService costDomainService;

	@Autowired
	private PharmacyDomainService pharmacyDomainService;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private OrderDomainService orderDomainService;

	private Org org;// 哈医大二院
	private Dept dept111;// 住院处
	private Dept dept222;// 收费处
	private Pharmacy dept333;// 药房
	private InPatientDept dept000;// 内泌五

	private Staff user111;// 住院处送诊人-曹操
	private Staff user222;// 收费处-张飞
	private Staff user333;// 药房摆药岗位-赵云
	private Staff user001;// 内泌五接诊护士-大乔
	private Doctor user002;// 内泌五医生-貂蝉
	private Nurse user003;// 内泌五护士-小乔

	private ChargeItem bedChargeItem;// 床位费计费项目【暂时床位费只设一个计费项目】

	private ChargeItem drugTypeSpec001ChargeItem;// 药品001计费项目

	private ChargeItem secondNursingChargeItem;// 二级护理计费项目

	private SecondNursingOrderType secondNursingOrderType;// 二级护理医嘱类型

	private DrugTypeSpec drugTypeSpec001;// 药品规格001

	private DrugType drugType001;// 药房下的药品类型001（有库存属性）

	private DrugOrderType drugOrderType001;// 药品医嘱类型001

	private LeaveHospitalOrderType leaveHospitalOrderType;// 出院医嘱类型

	private Visit visit001;

	public void testInit() {
		// 初始化Context
		ApplicationContext applicationContext = SpringApplication
				.run(Application.class);
		ApplicationContextUtil.setApplicationContext(applicationContext);

		clear();

		initData();
	}

	/**
	 * 
	 * @throws HsException
	 */
	public void execute() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:50"));

		// 创建测试患者
		visit001 = new Visit();
		visit001.setName("测试患者001");
		visit001.setRespDept(dept000);
		visit001.setRespDoctor(user002);
		// 送诊
		registerAppService.register(visit001, user111);

		Pageable pageable;
		List<Visit> visits;
		List<OrderExecute> executes;
		List<Order> orders;
		int startedCount;
		int resolveCount;

		pageable = new PageRequest(0, 15);
		visits = cashierAppService.getNeedInitAccountVisits(pageable);

		assertTrue(visits.size() == 1);
		assertTrue(visits.get(0).getId().equals(visit001.getId()));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:10"));

		// 预存费用
		cashierAppService.initAccount(visit001.getId(), 2000F, user222);

		pageable = new PageRequest(0, 15);
		visits = inPatientAppService.getNeedReceiveVisits(user001, pageable);

		assertTrue(visits.size() == 1);
		assertTrue(visits.get(0).getId().equals(visit001.getId()));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:30"));

		// 接诊
		ReceiveVisitVO receiveVisitVO = new ReceiveVisitVO();
		receiveVisitVO.setVisitId(visit001.getId());
		receiveVisitVO.setBed("bed001");
		receiveVisitVO.setNurseId(user003.getId());

		inPatientAppService.receive(receiveVisitVO, user001);

		pageable = new PageRequest(0, 15);
		visits = inPatientAppService.InWardVisits(dept000, pageable);

		assertTrue(visits.size() == 1);
		assertTrue(visits.get(0).getId().equals(visit001.getId()));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:50"));

		// 开立二级护理长期医嘱
		LongOrder secondNursingOrder = new LongOrder();
		secondNursingOrder.setVisitId(visit001.getId());
		secondNursingOrder.setName("二级护理");
		secondNursingOrder.setFrequencyType(LongOrder.FrequencyType_Day);
		secondNursingOrder.setPlanStartDate(DateUtil.getSysDate());

		secondNursingOrder.setType(secondNursingOrderType);

		orderAppService.create(secondNursingOrder, user002);

		// 开立药品临时医嘱
		Order drug001Order = new TemporaryOrder();
		drug001Order.setVisitId(visit001.getId());
		drug001Order.setName("药品001");
		drug001Order.setPlanStartDate(DateUtil.getSysDate());
		drug001Order.setCount(2);

		DrugOrderType drugOrderType = new DrugOrderType();
		drugOrderType.setDrugTypeSpecId(drugTypeSpec001.getId());

		drug001Order.setType(drugOrderType);

		orderAppService.create(drug001Order, user002);

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:00"));

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		pageable = new PageRequest(0, 15);
		executes = orderAppService.getNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 1);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:05"));

		// 发送医嘱执行条目
		orderExecuteAppService.send(executes.get(0).getId(), user003);

		// 采用API启动符合条件的执行条目
		startedCount = orderExecuteAppService.start();

		assertTrue(startedCount == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:15"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user333,
				pageable);

		assertTrue(executes.size() == 1);

		// 完成摆药医嘱执行条目
		orderExecuteAppService.finish(executes.get(0).getId(), user333);

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:30"));

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:45"));

		// 取消医嘱条目
		orderAppService.cancel(drug001Order.getId(), user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 13:45"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedBackChargeOrderExecutes(
				user222, pageable);

		assertTrue(executes.size() == 1);

		orderExecuteAppService.unCharging(executes.get(0).getId(), true,
				user222);

		// 2016-12-29
		DateUtil.setSysDate(DateUtil.createDay("2016-12-29"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 09:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 1);

		orderExecuteAppService.finish(executes.get(0).getId(), user003);

		// 2016-12-30
		DateUtil.setSysDate(DateUtil.createDay("2016-12-30"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 09:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 1);

		orderExecuteAppService.finish(executes.get(0).getId(), user003);

		// 2016-12-31
		DateUtil.setSysDate(DateUtil.createDay("2016-12-31"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-31 09:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 1);

		orderExecuteAppService.finish(executes.get(0).getId(), user003);

		// 2017-01-01
		DateUtil.setSysDate(DateUtil.createDay("2017-01-01"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 09:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 1);

		orderExecuteAppService.finish(executes.get(0).getId(), user003);

		// 2017-01-02
		DateUtil.setSysDate(DateUtil.createDay("2017-01-02"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-02 09:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 1);

		orderExecuteAppService.finish(executes.get(0).getId(), user003);

		// 2017-01-03
		DateUtil.setSysDate(DateUtil.createDay("2017-01-03"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-03 09:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 1);

		orderExecuteAppService.finish(executes.get(0).getId(), user003);

		// 2017-01-04
		DateUtil.setSysDate(DateUtil.createDay("2017-01-04"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-04 09:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 1);

		orderExecuteAppService.finish(executes.get(0).getId(), user003);

		// 2017-01-05
		DateUtil.setSysDate(DateUtil.createDay("2017-01-05"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-05 09:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 1);

		orderExecuteAppService.finish(executes.get(0).getId(), user003);

		// 2017-01-06
		DateUtil.setSysDate(DateUtil.createDay("2017-01-06"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 09:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 1);

		orderExecuteAppService.finish(executes.get(0).getId(), user003);

		// 2017-01-07
		DateUtil.setSysDate(DateUtil.createDay("2017-01-07"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 10:10"));

		// 开立出院临时医嘱
		Order leaveHospitalOrder = new TemporaryOrder();
		leaveHospitalOrder.setVisitId(visit001.getId());
		leaveHospitalOrder.setName("出院医嘱");
		leaveHospitalOrder.setPlanStartDate(DateUtil.createDay("2017-01-09"));
		leaveHospitalOrder.setExecuteDept(dept222);

		leaveHospitalOrder.setType(leaveHospitalOrderType);

		orderAppService.create(leaveHospitalOrder, user002);

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);
		assertTrue(orders.get(0).getId().equals(leaveHospitalOrder.getId()));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 10:30"));

		// 核对医嘱
		orderAppService.verify(leaveHospitalOrder.getId(), user003);

		// 采用API启动符合条件的执行条目
		startedCount = orderExecuteAppService.start();

		assertTrue(startedCount == 0);

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 1);

		orderExecuteAppService.finish(executes.get(0).getId(), user003);

		// 2017-01-08
		DateUtil.setSysDate(DateUtil.createDay("2017-01-08"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-08 09:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 1);

		orderExecuteAppService.finish(executes.get(0).getId(), user003);

		// 2017-01-09
		DateUtil.setSysDate(DateUtil.createDay("2017-01-09"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		assertTrue(startedCount == 1);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 09:30"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 1);

		// 完成出院登记医嘱执行条目
		orderExecuteAppService.finish(executes.get(0).getId(), user003);

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user222,
				pageable);

		assertTrue(executes.size() == 1);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 10:30"));

		// 完成出院结算医嘱执行条目
		orderExecuteAppService.finish(executes.get(0).getId(), user222);

		Visit visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_LeaveHospital));

	}

	public void clear() {
		// 清空医嘱类型
		orderDomainService.clearOrderTypes();
		// 清空药品类型
		pharmacyDomainService.clearDrugTypes();
		// 清空药品规格
		pharmacyDomainService.clearDrugTypeSpecs();
		// 清空计费项目
		costDomainService.clearChargeItems();
		// 清空患者一次住院
		visitDomainService.clear();
		// 清空成本记录
		costDomainService.clearCostRecords();
		// 清空用户信息
		userDomainService.clear();
		// 清空组织机构信息
		organizationDomainService.clear();
	}

	@Transactional(rollbackFor = Exception.class)
	public void initData() {

		initOrgs();

		initUsers();

		initChargeItems();

		initDrugTypeSpecs();

		initDrugTypes();

		initOrderTypes();

	}

	private void initOrgs() {

		List<Unit> units = new ArrayList<Unit>();

		org = new Org();
		org.setId("org000");
		org.setName("哈医大二院");

		units.add(org);

		dept111 = new Dept();
		dept111.setId("dept111");
		dept111.setName("住院处");
		dept111.setParent(org);

		units.add(dept111);

		dept222 = new Dept();
		dept222.setId("dept222");
		dept222.setName("收费处");
		dept222.setParent(org);

		units.add(dept222);

		dept333 = new Pharmacy();
		dept333.setId("dept333");
		dept333.setName("药房");
		dept333.setParent(org);

		units.add(dept333);

		dept000 = new InPatientDept();
		dept000.setId("dept000");
		dept000.setName("内泌五");
		dept000.setParent(org);

		units.add(dept000);

		organizationDomainService.create(units);
	}

	private void initUsers() {

		List<AbstractUser> users = new ArrayList<AbstractUser>();

		user111 = new Staff();

		user111.setId("staff111");
		user111.setName("住院处送诊人-曹操");
		user111.setDept(dept111);

		users.add(user111);

		user222 = new Staff();

		user222.setId("staff222");
		user222.setName("收费处-张飞");
		user222.setDept(dept222);

		users.add(user222);

		user333 = new Staff();

		user333.setId("staff333");
		user333.setName("药房摆药岗位-赵云");
		user333.setDept(dept333);

		users.add(user333);

		user001 = new Staff();

		user001.setId("staff001");
		user001.setName("内泌五接诊护士-大乔");
		user001.setDept(dept000);

		users.add(user001);

		user002 = new Doctor();

		user002.setId("doctor002");
		user002.setName("内泌五医生-貂蝉");
		user002.setDept(dept000);

		users.add(user002);

		user003 = new Nurse();

		user003.setId("nurse003");
		user003.setName("内泌五护士-小乔");
		user003.setDept(dept000);

		users.add(user003);

		userDomainService.create(users);
	}

	private void initChargeItems() {

		List<ChargeItem> chargeItems = new ArrayList<ChargeItem>();

		bedChargeItem = new ChargeItem();
		bedChargeItem.setId("bed");
		bedChargeItem.setCode("bed");
		bedChargeItem.setName("床位费");
		bedChargeItem.setPrice(20F);
		bedChargeItem.setChargingMode(ChargeItem.ChargingMode_Day);

		chargeItems.add(bedChargeItem);

		drugTypeSpec001ChargeItem = new ChargeItem();
		drugTypeSpec001ChargeItem.setId("drugTypeSpec001");
		drugTypeSpec001ChargeItem.setCode("drugTypeSpec001");
		drugTypeSpec001ChargeItem.setName("阿司匹林");
		drugTypeSpec001ChargeItem.setPrice(30F);
		drugTypeSpec001ChargeItem.setUnit("盒");
		drugTypeSpec001ChargeItem
				.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(drugTypeSpec001ChargeItem);

		secondNursingChargeItem = new ChargeItem();
		secondNursingChargeItem.setId("secondNursingChargeItem");
		secondNursingChargeItem.setCode("secondNursingChargeItem");
		secondNursingChargeItem.setName("二级护理");
		secondNursingChargeItem.setPrice(8);
		secondNursingChargeItem.setChargingMode(ChargeItem.ChargingMode_Day);

		chargeItems.add(secondNursingChargeItem);

		costDomainService.create(chargeItems);
	}

	private void initDrugTypeSpecs() {

		List<DrugTypeSpec> drugTypeSpecs = new ArrayList<DrugTypeSpec>();

		drugTypeSpec001 = new DrugTypeSpec();
		drugTypeSpec001.setId("drugTypeSpec001");
		drugTypeSpec001.setName("阿司匹林");
		drugTypeSpec001.setChargeItem(drugTypeSpec001ChargeItem);

		drugTypeSpecs.add(drugTypeSpec001);

		pharmacyDomainService.createDrugTypeSpecs(drugTypeSpecs);
	}

	private void initDrugTypes() {

		List<DrugType> drugTypes = new ArrayList<DrugType>();

		drugType001 = new DrugType();
		drugType001.setId("drugType001");
		drugType001.setDrugTypeSpec(drugTypeSpec001);
		drugType001.setPharmacy(dept333);
		drugType001.setStock(100);

		drugTypes.add(drugType001);

		pharmacyDomainService.createDrugTypes(drugTypes);
	}

	private void initOrderTypes() {

		List<OrderType> orderTypes = new ArrayList<OrderType>();

		drugOrderType001 = new DrugOrderType();
		drugOrderType001.setId("drugOrderType001");
		drugOrderType001.setCode("drugOrderType001");
		drugOrderType001.setName("阿司匹林");
		drugOrderType001.setDrugType(drugType001);

		orderTypes.add(drugOrderType001);

		leaveHospitalOrderType = new LeaveHospitalOrderType();
		leaveHospitalOrderType.setId("leaveHospitalOrderType");
		leaveHospitalOrderType.setCode("leaveHospitalOrderType");
		leaveHospitalOrderType.setName("出院医嘱");

		orderTypes.add(leaveHospitalOrderType);

		secondNursingOrderType = new SecondNursingOrderType();
		secondNursingOrderType.setId("secondNursingOrderType");
		secondNursingOrderType.setCode("secondNursingOrderType");
		secondNursingOrderType.setName("二级护理");
		secondNursingOrderType.setChargeItem(secondNursingChargeItem);

		orderTypes.add(secondNursingOrderType);

		orderDomainService.createOrderTypes(orderTypes);
	}

}
