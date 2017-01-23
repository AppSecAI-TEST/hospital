package com.neusoft.hs.application;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.neusoft.hs.application.inspect.InspectAppService;
import com.neusoft.hs.application.register.RegisterAppService;
import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.order.AssistMaterial;
import com.neusoft.hs.domain.order.CompsiteOrder;
import com.neusoft.hs.domain.order.DrugOrderType;
import com.neusoft.hs.domain.order.InfusionOrderUseMode;
import com.neusoft.hs.domain.order.InspectApply;
import com.neusoft.hs.domain.order.InspectApplyItem;
import com.neusoft.hs.domain.order.InspectItem;
import com.neusoft.hs.domain.order.InspectOrderType;
import com.neusoft.hs.domain.order.LeaveHospitalOrderType;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.OralOrderUseMode;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderDomainService;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderFrequencyType;
import com.neusoft.hs.domain.order.OrderFrequencyType9H15H;
import com.neusoft.hs.domain.order.OrderFrequencyTypeDay;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OrderUseMode;
import com.neusoft.hs.domain.order.OrderUseModeAssistMaterial;
import com.neusoft.hs.domain.order.OrderUtil;
import com.neusoft.hs.domain.order.SecondNursingOrderType;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.InspectDept;
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
	private InspectAppService inspectAppService;

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

	@Autowired
	private OrderUtil orderUtil;

	@Autowired
	private TestUtil testUtil;

	private Org org;// 哈医大二院
	private Dept dept111;// 住院处
	private Dept dept222;// 收费处
	private Pharmacy dept333;// 药房
	private InspectDept dept444;// CT室
	private InspectDept dept555;// 核磁检查室

	private InPatientDept dept000;// 内泌五

	private Staff user111;// 住院处送诊人-曹操
	private Staff user222;// 收费处-张飞
	private Staff user333;// 药房摆药岗位-赵云
	private Staff user444;// 药房配液岗位-关羽
	private Staff user555;// CT室安排检查员-吕玲绮
	private Staff user666;// CT室检查师-张合
	private Staff user777;// 核磁检查室安排检查员-周瑜
	private Staff user888;// 核磁检查室检查师-鲁肃

	private Staff user001;// 内泌五接诊护士-大乔
	private Doctor user002;// 内泌五医生-貂蝉
	private Nurse user003;// 内泌五护士-小乔

	private ChargeItem bedChargeItem;// 床位费计费项目【暂时床位费只设一个计费项目】

	private ChargeItem drugTypeSpec001ChargeItem;// 药品001计费项目（阿司匹林）

	private ChargeItem drugTypeSpec002ChargeItem;// 药品002计费项目（头孢3）

	private ChargeItem drugTypeSpec003ChargeItem;// 药品003计费项目(5%葡萄糖液)

	private ChargeItem transportFluidMaterialChargeItem;// 输液材料费

	private ChargeItem secondNursingChargeItem;// 二级护理计费项目

	private ChargeItem brainCTChargeItem;// 脑CT计费项目

	private ChargeItem brainHCChargeItem;// 脑核磁计费项目

	private DrugTypeSpec drugTypeSpec001;// 药品规格001

	private DrugTypeSpec drugTypeSpec002;// 药品规格002

	private DrugTypeSpec drugTypeSpec003;// 药品规格003

	private DrugType drugType001;// 药房下的药品类型001（有库存属性）

	private DrugType drugType002;// 药房下的药品类型002（有库存属性）

	private DrugType drugType003;// 药房下的药品类型003（有库存属性）

	private DrugOrderType drugOrderType001;// 药品医嘱类型001

	private DrugOrderType drugOrderType002;// 药品医嘱类型002

	private DrugOrderType drugOrderType003;// 药品医嘱类型003

	private InspectItem brainCTInspectItem;// 脑CT检查项目

	private InspectItem brainHCInspectItem;// 脑核磁检查项目

	private SecondNursingOrderType secondNursingOrderType;// 二级护理医嘱类型

	private LeaveHospitalOrderType leaveHospitalOrderType;// 出院医嘱类型

	private InspectOrderType inspectOrderType;// 检查医嘱类型

	private OralOrderUseMode oralOrderUseMode;// 口服用法

	private InfusionOrderUseMode infusionOrderUseMode;// 输液用法

	private AssistMaterial transportFluidAssistMaterial;// 输液辅材

	private OrderUseModeAssistMaterial everyOneOrderUseModeAssistMaterial;// 按频次收费

	private OrderUseModeAssistMaterial everyDayOrderUseModeAssistMaterial;// 按天收费

	private OrderUseModeAssistMaterial onlyOneOrderUseModeAssistMaterial;// 只收一次

	private OrderFrequencyType orderFrequencyType_Day;// 每天

	private OrderFrequencyType orderFrequencyType_9H15H;// 每天2次/早9/下3

	private Visit visit001;

	public void testInit() {
		// 初始化Context
		ApplicationContext applicationContext = SpringApplication
				.run(Application.class);
		ApplicationContextUtil.setApplicationContext(applicationContext);

		clear();

		initData();

		choice();
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
		Visit visit;
		Date sysDate;

		pageable = new PageRequest(0, 15);
		visits = cashierAppService.getNeedInitAccountVisits(pageable);

		assertTrue(visits.size() == 1);
		assertTrue(visits.get(0).getId().equals(visit001.getId()));

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedInitAccount));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:10"));

		// 预存费用
		cashierAppService.initAccount(visit001.getId(), 2000F, user222);

		pageable = new PageRequest(0, 15);
		visits = inPatientAppService.getNeedReceiveVisits(user001, pageable);

		assertTrue(visits.size() == 1);
		assertTrue(visits.get(0).getId().equals(visit001.getId()));

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedIntoWard));

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

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_IntoWard));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:50"));

		// 开立二级护理长期医嘱
		LongOrder secondNursingOrder = new LongOrder();
		secondNursingOrder.setVisit(visit001);
		secondNursingOrder.setName("二级护理");
		secondNursingOrder.setFrequencyType(orderFrequencyType_Day);
		secondNursingOrder.setPlanStartDate(DateUtil.getSysDateStart());

		secondNursingOrder.setType(secondNursingOrderType);

		orderAppService.create(secondNursingOrder, user002);

		// 开立药品临时医嘱
		Order drug001Order = new TemporaryOrder();
		drug001Order.setVisit(visit001);
		drug001Order.setName("药品001");
		drug001Order.setPlanStartDate(DateUtil.getSysDate());
		drug001Order.setCount(2);
		drug001Order.setUseMode(oralOrderUseMode);

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

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:15"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user333,
				pageable);

		assertTrue(executes.size() == 1);

		// 完成摆药医嘱执行条目
		orderExecuteAppService.finish(executes.get(0).getId(), user333);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:30"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:45"));

		// 取消医嘱条目
		orderAppService.cancel(drug001Order.getId(), user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 13:45"));

		pageable = new PageRequest(0, 15);
		executes = costDomainService.getNeedBackChargeOrderExecutes(user222,
				pageable);

		assertTrue(executes.size() == 1);

		costDomainService.unCharging(executes.get(0).getId(), true, user003);

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

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 10:10"));

		// 创建药品002长期医嘱
		sysDate = DateUtil.getSysDate();

		LongOrder drug002Order = new LongOrder();
		drug002Order.setVisit(visit001);
		drug002Order.setName("头孢3");
		drug002Order.setCount(2);
		drug002Order.setUseMode(infusionOrderUseMode);
		drug002Order.setFrequencyType(orderFrequencyType_9H15H);

		drug002Order.setPlanStartDate(sysDate);
		drug002Order.setPlanEndDate(DateUtil.addDay(sysDate, 2));

		drug002Order.setType(drugOrderType002);

		// 创建药品003长期医嘱
		LongOrder drug003Order = new LongOrder();
		drug003Order.setVisit(visit001);
		drug003Order.setName("5%葡萄糖");
		drug003Order.setCount(1);
		drug003Order.setUseMode(infusionOrderUseMode);
		drug003Order.setFrequencyType(orderFrequencyType_9H15H);

		drug003Order.setPlanStartDate(sysDate);
		drug003Order.setPlanEndDate(DateUtil.addDay(sysDate, 2));

		drug003Order.setType(drugOrderType003);

		CompsiteOrder drug002003Order = new CompsiteOrder();
		drug002003Order.addOrder(drug002Order);
		drug002003Order.addOrder(drug003Order);

		orderAppService.create(drug002003Order, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 10:30"));

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 2);

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 11:05"));

		pageable = new PageRequest(0, 15);
		executes = orderAppService.getNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 6);

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 13:05"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user444,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成配液医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user444);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 15:30"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user003);
		}

		// 2016-12-30
		DateUtil.setSysDate(DateUtil.createDay("2016-12-30"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 08:30"));

		pageable = new PageRequest(0, 15);
		executes = orderAppService.getNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 08:50"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user444,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成配液医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user444);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 09:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 3);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 14:00"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user444,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成配液医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user444);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 15:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user003);
		}

		// 2016-12-31
		DateUtil.setSysDate(DateUtil.createDay("2016-12-31"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-31 08:30"));

		pageable = new PageRequest(0, 15);
		executes = orderAppService.getNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 0);

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user444,
				pageable);

		assertTrue(executes.size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-31 08:50"));

		// 完成配液医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user444);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-31 09:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 3);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user003);
		}

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

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 09:30"));

		TemporaryOrder brainCTOrder = new TemporaryOrder();
		brainCTOrder.setVisit(visit001);
		brainCTOrder.setName("脑CT检查");
		brainCTOrder.setType(inspectOrderType);
		brainCTOrder.setPlanStartDate(DateUtil.getSysDate());

		InspectApply inspectApply = new InspectApply();
		inspectApply.setGoal("查查是否有问题");

		InspectApplyItem brainCTInspectApplyItem = new InspectApplyItem();
		brainCTInspectApplyItem.setInspectItem(brainCTInspectItem);
		brainCTInspectApplyItem.setInspectDept(dept444);

		inspectApply.addInspectApplyItem(brainCTInspectApplyItem);

		brainCTOrder.setApply(inspectApply);

		orderAppService.create(brainCTOrder, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-01-01 09:40"));

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 09:45"));

		pageable = new PageRequest(0, 15);
		executes = orderAppService.getNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 1);

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 10:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user555,
				pageable);

		assertTrue(executes.size() == 1);

		// 安排检查时间
		inspectAppService.arrange(executes.get(0).getId(),
				DateUtil.createMinute("2017-01-02 14:00"));

		orderExecuteAppService.finish(executes.get(0).getId(), user555);

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

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-02 14:40"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user666,
				pageable);

		assertTrue(executes.size() == 1);

		Map<InspectApplyItem, String> results = new HashMap<InspectApplyItem, String>();
		results.put(brainCTInspectApplyItem, "没啥问题");
		inspectAppService.confirm(executes.get(0).getId(), results, user666);

		orderExecuteAppService.finish(executes.get(0).getId(), user666);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-02 15:00"));

		testUtil.testInspectResult(brainCTOrder.getId());

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
		leaveHospitalOrder.setVisit(visit001);
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

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user003);
		}

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState()
				.equals(Visit.State_NeedLeaveHospitalBalance));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user222,
				pageable);

		assertTrue(executes.size() == 1);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 10:30"));

		// 完成出院结算医嘱执行条目
		orderExecuteAppService.finish(executes.get(0).getId(), user222);

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_LeaveHospital));

	}

	public void clear() {
		// 清空医嘱用法
		orderDomainService.clearOrderUseModes();
		// 清空医嘱类型
		orderDomainService.clearOrderTypes();
		// 清空组合医嘱
		orderDomainService.clearCompsiteOrdes();
		// 清空频次类型
		orderDomainService.clearOrderFrequencyTypes();
		// 清空药品类型
		pharmacyDomainService.clearDrugTypes();
		// 清空药品规格
		pharmacyDomainService.clearDrugTypeSpecs();
		// 清空检查项目
		orderDomainService.clearInspectItems();
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

		initInspectItems();

		initOrderTypes();

		initOrderUseModes();

		initAssistMaterials();

		initOrderUseModeAssistMaterials();

		initOrderFrequencyTypes();
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

		dept444 = new InspectDept();
		dept444.setId("dept444");
		dept444.setName("CT室");
		dept444.setParent(org);

		units.add(dept444);

		dept555 = new InspectDept();
		dept555.setId("dept555");
		dept555.setName("核磁检查室");
		dept555.setParent(org);

		units.add(dept555);

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

		user444 = new Staff();

		user444.setId("staff444");
		user444.setName("药房配液岗位-关羽");
		user444.setDept(dept333);

		users.add(user444);

		user555 = new Staff();

		user555.setId("staff555");
		user555.setName("CT室安排检查员-吕玲绮");
		user555.setDept(dept444);

		users.add(user555);

		user666 = new Staff();

		user666.setId("staff666");
		user666.setName("CT室检查师-张合");
		user666.setDept(dept444);

		users.add(user666);
		
		user777 = new Staff();

		user777.setId("staff777");
		user777.setName("核磁检查室安排检查员-周瑜");
		user777.setDept(dept555);

		users.add(user777);

		user888 = new Staff();

		user888.setId("staff888");
		user888.setName("核磁检查室检查师-鲁肃");
		user888.setDept(dept555);

		users.add(user888);

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
		bedChargeItem.setId("bedChargeItem");
		bedChargeItem.setCode("bedChargeItem");
		bedChargeItem.setName("床位费");
		bedChargeItem.setPrice(20F);
		bedChargeItem.setChargingMode(ChargeItem.ChargingMode_Day);

		chargeItems.add(bedChargeItem);

		drugTypeSpec001ChargeItem = new ChargeItem();
		drugTypeSpec001ChargeItem.setId("drugTypeSpec001ChargeItem");
		drugTypeSpec001ChargeItem.setCode("drugTypeSpec001ChargeItem");
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

		drugTypeSpec002ChargeItem = new ChargeItem();
		drugTypeSpec002ChargeItem.setId("drugTypeSpec002ChargeItem");
		drugTypeSpec002ChargeItem.setCode("drugTypeSpec002ChargeItem");
		drugTypeSpec002ChargeItem.setName("头孢3");
		drugTypeSpec002ChargeItem.setPrice(120);
		drugTypeSpec002ChargeItem.setUnit("支");
		drugTypeSpec002ChargeItem
				.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(drugTypeSpec002ChargeItem);

		drugTypeSpec003ChargeItem = new ChargeItem();
		drugTypeSpec003ChargeItem.setId("drugTypeSpec003ChargeItem");
		drugTypeSpec003ChargeItem.setCode("drugTypeSpec003ChargeItem");
		drugTypeSpec003ChargeItem.setName("5%葡萄糖液");
		drugTypeSpec003ChargeItem.setPrice(15);
		drugTypeSpec003ChargeItem.setUnit("袋");
		drugTypeSpec003ChargeItem
				.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(drugTypeSpec003ChargeItem);

		transportFluidMaterialChargeItem = new ChargeItem();
		transportFluidMaterialChargeItem
				.setId("transportFluidMaterialChargeItem");
		transportFluidMaterialChargeItem
				.setCode("transportFluidMaterialChargeItem");
		transportFluidMaterialChargeItem.setName("输液材料费");
		transportFluidMaterialChargeItem.setPrice(7);
		transportFluidMaterialChargeItem.setUnit("件");
		transportFluidMaterialChargeItem
				.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(transportFluidMaterialChargeItem);

		brainCTChargeItem = new ChargeItem();
		brainCTChargeItem.setId("brainCTChargeItem");
		brainCTChargeItem.setCode("brainCTChargeItem");
		brainCTChargeItem.setName("脑CT");
		brainCTChargeItem.setPrice(150);
		brainCTChargeItem.setUnit("次");
		brainCTChargeItem.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(brainCTChargeItem);

		brainHCChargeItem = new ChargeItem();
		brainHCChargeItem.setId("brainHCChargeItem");
		brainHCChargeItem.setCode("brainHCChargeItem");
		brainHCChargeItem.setName("脑核磁");
		brainHCChargeItem.setPrice(350);
		brainHCChargeItem.setUnit("次");
		brainHCChargeItem.setChargingMode(ChargeItem.ChargingMode_Amount);

		chargeItems.add(brainHCChargeItem);

		costDomainService.create(chargeItems);
	}

	private void initDrugTypeSpecs() {

		List<DrugTypeSpec> drugTypeSpecs = new ArrayList<DrugTypeSpec>();

		drugTypeSpec001 = new DrugTypeSpec();
		drugTypeSpec001.setId("drugTypeSpec001");
		drugTypeSpec001.setName("阿司匹林");
		drugTypeSpec001.setChargeItem(drugTypeSpec001ChargeItem);

		drugTypeSpecs.add(drugTypeSpec001);

		drugTypeSpec002 = new DrugTypeSpec();
		drugTypeSpec002.setId("drugTypeSpec002");
		drugTypeSpec002.setName("头孢3");
		drugTypeSpec002.setChargeItem(drugTypeSpec002ChargeItem);

		drugTypeSpecs.add(drugTypeSpec002);

		drugTypeSpec003 = new DrugTypeSpec();
		drugTypeSpec003.setId("drugTypeSpec003");
		drugTypeSpec003.setName("5%葡萄糖液");
		drugTypeSpec003.setChargeItem(drugTypeSpec003ChargeItem);
		drugTypeSpec003.setTransportFluidCharge(true);

		drugTypeSpecs.add(drugTypeSpec003);

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

		drugType002 = new DrugType();
		drugType002.setId("drugType002");
		drugType002.setDrugTypeSpec(drugTypeSpec002);
		drugType002.setPharmacy(dept333);
		drugType002.setStock(1200);

		drugTypes.add(drugType002);

		drugType003 = new DrugType();
		drugType003.setId("drugType003");
		drugType003.setDrugTypeSpec(drugTypeSpec003);
		drugType003.setPharmacy(dept333);
		drugType003.setStock(3000);

		drugTypes.add(drugType003);

		pharmacyDomainService.createDrugTypes(drugTypes);
	}

	private void initInspectItems() {

		List<InspectItem> inspectItems = new ArrayList<InspectItem>();

		brainCTInspectItem = new InspectItem();
		brainCTInspectItem.setId("brainCTInspectItem");
		brainCTInspectItem.setCode("brainCTInspectItem");
		brainCTInspectItem.setName("脑CT");
		brainCTInspectItem.setChargeItem(brainCTChargeItem);

		inspectItems.add(brainCTInspectItem);

		brainHCInspectItem = new InspectItem();
		brainHCInspectItem.setId("brainHCInspectItem");
		brainHCInspectItem.setCode("brainHCInspectItem");
		brainHCInspectItem.setName("脑核磁");
		brainHCInspectItem.setChargeItem(brainHCChargeItem);

		inspectItems.add(brainHCInspectItem);

		orderDomainService.createInspectItems(inspectItems);
	}

	private void initOrderTypes() {

		List<OrderType> orderTypes = new ArrayList<OrderType>();

		drugOrderType001 = new DrugOrderType();
		drugOrderType001.setId("drugOrderType001");
		drugOrderType001.setCode("drugOrderType001");
		drugOrderType001.setName("阿司匹林");
		drugOrderType001.setDrugType(drugType001);

		orderTypes.add(drugOrderType001);

		drugOrderType002 = new DrugOrderType();
		drugOrderType002.setId("drugOrderType002");
		drugOrderType002.setCode("drugOrderType002");
		drugOrderType002.setName("头孢3");
		drugOrderType002.setDrugType(drugType002);

		orderTypes.add(drugOrderType002);

		drugOrderType003 = new DrugOrderType();
		drugOrderType003.setId("drugOrderType003");
		drugOrderType003.setCode("drugOrderType003");
		drugOrderType003.setName("5%葡萄糖液");
		drugOrderType003.setDrugType(drugType003);

		orderTypes.add(drugOrderType003);

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

		inspectOrderType = new InspectOrderType();
		inspectOrderType.setId("brainCTInspectOrderType");
		inspectOrderType.setCode("brainCTInspectOrderType");
		inspectOrderType.setName("脑CT");

		orderTypes.add(inspectOrderType);

		orderDomainService.createOrderTypes(orderTypes);
	}

	private void initOrderUseModes() {

		List<OrderUseMode> orderUseModes = new ArrayList<OrderUseMode>();

		oralOrderUseMode = new OralOrderUseMode();
		oralOrderUseMode.setId("oralOrderUseMode");
		oralOrderUseMode.setCode("oralOrderUseMode");
		oralOrderUseMode.setName("口服");

		orderUseModes.add(oralOrderUseMode);

		infusionOrderUseMode = new InfusionOrderUseMode();
		infusionOrderUseMode.setId("infusionOrderUseMode");
		infusionOrderUseMode.setCode("infusionOrderUseMode");
		infusionOrderUseMode.setName("输液");

		orderUseModes.add(infusionOrderUseMode);

		orderDomainService.createOrderUseModes(orderUseModes);

	}

	private void initAssistMaterials() {

		List<AssistMaterial> assistMaterials = new ArrayList<AssistMaterial>();

		transportFluidAssistMaterial = new AssistMaterial();
		transportFluidAssistMaterial.setId("infusionAssistMaterial");
		transportFluidAssistMaterial.setCode("infusionAssistMaterial");
		transportFluidAssistMaterial.setName("输液辅材");
		transportFluidAssistMaterial
				.setChargeItem(transportFluidMaterialChargeItem);

		assistMaterials.add(transportFluidAssistMaterial);

		orderDomainService.createAssistMaterials(assistMaterials);
	}

	private void initOrderUseModeAssistMaterials() {

		everyOneOrderUseModeAssistMaterial = new OrderUseModeAssistMaterial();
		everyOneOrderUseModeAssistMaterial.setId("everyOne");
		everyOneOrderUseModeAssistMaterial.setCode("everyOne");
		everyOneOrderUseModeAssistMaterial
				.setAssistMaterial(transportFluidAssistMaterial);
		everyOneOrderUseModeAssistMaterial
				.setOrderUseMode(infusionOrderUseMode);
		everyOneOrderUseModeAssistMaterial
				.setChargeMode(OrderUseModeAssistMaterial.everyOne);
		everyOneOrderUseModeAssistMaterial
				.setSign(InfusionOrderUseMode.transportFluid);

		everyDayOrderUseModeAssistMaterial = new OrderUseModeAssistMaterial();
		everyDayOrderUseModeAssistMaterial.setId("everyDay");
		everyDayOrderUseModeAssistMaterial.setCode("everyDay");
		everyDayOrderUseModeAssistMaterial
				.setAssistMaterial(transportFluidAssistMaterial);
		everyDayOrderUseModeAssistMaterial
				.setOrderUseMode(infusionOrderUseMode);
		everyDayOrderUseModeAssistMaterial
				.setChargeMode(OrderUseModeAssistMaterial.everyDay);
		everyDayOrderUseModeAssistMaterial
				.setSign(InfusionOrderUseMode.transportFluid);

		onlyOneOrderUseModeAssistMaterial = new OrderUseModeAssistMaterial();
		onlyOneOrderUseModeAssistMaterial.setId("onlyOne");
		onlyOneOrderUseModeAssistMaterial.setCode("onlyOne");
		onlyOneOrderUseModeAssistMaterial
				.setAssistMaterial(transportFluidAssistMaterial);
		onlyOneOrderUseModeAssistMaterial.setOrderUseMode(infusionOrderUseMode);
		onlyOneOrderUseModeAssistMaterial
				.setChargeMode(OrderUseModeAssistMaterial.onlyOne);
		onlyOneOrderUseModeAssistMaterial
				.setSign(InfusionOrderUseMode.transportFluid);
	}

	private void initOrderFrequencyTypes() {

		List<OrderFrequencyType> orderFrequencyTypes = new ArrayList<OrderFrequencyType>();

		orderFrequencyType_Day = new OrderFrequencyTypeDay();

		orderFrequencyTypes.add(orderFrequencyType_Day);

		orderFrequencyType_9H15H = new OrderFrequencyType9H15H();

		orderFrequencyTypes.add(orderFrequencyType_9H15H);

		orderDomainService.createOrderFrequencyTypes(orderFrequencyTypes);

	}

	private void choice() {
		this.choiceOrderUseModeAssistMaterial(onlyOneOrderUseModeAssistMaterial);
	}

	private void choiceOrderUseModeAssistMaterial(
			OrderUseModeAssistMaterial orderUseModeAssistMaterial) {
		orderDomainService
				.createOrderUseModeAssistMaterial(orderUseModeAssistMaterial);
	}

}
