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
import com.neusoft.hs.domain.inpatientdept.LeaveHospitalOrderType;
import com.neusoft.hs.domain.inpatientdept.SecondNursingOrderType;
import com.neusoft.hs.domain.inspect.InspectApply;
import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.inspect.InspectDept;
import com.neusoft.hs.domain.inspect.InspectDomainService;
import com.neusoft.hs.domain.inspect.InspectItem;
import com.neusoft.hs.domain.inspect.InspectOrderType;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.order.AssistMaterial;
import com.neusoft.hs.domain.order.CompsiteOrder;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderDomainService;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderFrequencyType;
import com.neusoft.hs.domain.order.OrderFrequencyType9H15H;
import com.neusoft.hs.domain.order.OrderFrequencyTypeDay;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OrderUtil;
import com.neusoft.hs.domain.order.SampleOrderTypeApp;
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
import com.neusoft.hs.domain.pharmacy.DrugOrderType;
import com.neusoft.hs.domain.pharmacy.DrugOrderTypeApp;
import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.domain.pharmacy.DrugTypeSpec;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.DrugUseModeAssistMaterial;
import com.neusoft.hs.domain.pharmacy.InfusionOrderUseMode;
import com.neusoft.hs.domain.pharmacy.OralOrderUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;
import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.ReceiveVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class AppMainTestService extends AppTestService {

	@Override
	protected void treatment() throws HsException {

		Pageable pageable;
		List<OrderExecute> executes;
		List<Order> orders;
		int startedCount;
		int resolveCount;
		Date sysDate;
		
		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:40"));

		// 开立二级护理长期医嘱
		LongOrder secondNursingOrder = new LongOrder();
		secondNursingOrder.setVisit(visit001);
		secondNursingOrder.setName("二级护理");
		secondNursingOrder.setFrequencyType(orderFrequencyType_Day);
		secondNursingOrder.setPlanStartDate(DateUtil.getSysDateStart());

		secondNursingOrder.setTypeApp(new SampleOrderTypeApp(
				secondNursingOrderType));

		orderAppService.create(secondNursingOrder, user002);

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:45"));

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:55"));

		// 开立药品临时医嘱
		Order drug001Order = new TemporaryOrder();
		drug001Order.setVisit(visit001);
		drug001Order.setName("药品001");
		drug001Order.setPlanStartDate(DateUtil.getSysDate());
		drug001Order.setCount(2);

		DrugOrderType drugOrderType = new DrugOrderType();
		drugOrderType.setDrugTypeSpec(drugTypeSpec001);

		drug001Order.setTypeApp(new DrugOrderTypeApp(drugOrderType,
				oralOrderUseMode));

		orderAppService.create(drug001Order, user002);

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);

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
		
		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 14:05"));
		
		Visit currentVisit = visitDomainService.find(visit001.getId());
		
		List<TreatmentItemSpec> treatmentItemSpecs = treatmentAppService.getShouldTreatmentItemSpecs(currentVisit, user002);
		
		assertTrue(treatmentItemSpecs.size() == 1);
		
		//创建主诉
		TreatmentItem item = new TreatmentItem();
		item.setVisit(visit001);
		item.setTreatmentItemSpec(mainDescribeTreatmentItemSpec);
		item.setCreator(user002);
		
		SimpleTreatmentItemValue value = new SimpleTreatmentItemValue();
		value.setInfo("患者咳嗽发烧三天");
		item.addValue(value);
		
		treatmentDomainService.create(item);
		
		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 14:20"));
		
		//创建入院记录
		MedicalRecord intoWardRecord = medicalRecordAppService.create(visit001, intoWardRecordMedicalRecordType, user002);
		
		Map<String, Itemable> datas = intoWardRecord.getDatas();
		
		assertTrue(datas.get("患者姓名").getValues().get(0).toString().equals("测试患者001"));
		assertTrue(datas.get("主诉").getValues().get(0).toString().equals("患者咳嗽发烧三天"));
		
		((SimpleTreatmentItemValue)datas.get("主诉").getValues().get(0)).setInfo("患者咳嗽发烧三天，体温38.5");
		
		medicalRecordAppService.create(intoWardRecord);
		
		intoWardRecord = medicalRecordAppService.find(intoWardRecord.getId());
		
		datas = intoWardRecord.getDatas();
		
		assertTrue(datas.get("患者姓名").getValues().get(0).toString().equals("测试患者001"));
		assertTrue(datas.get("主诉").getValues().get(0).toString().equals("患者咳嗽发烧三天，体温38.5"));
		
		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 15:00"));
		
		medicalRecordAppService.sign(intoWardRecord.getId(), user002);
		
		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 17:00"));
		
		item = treatmentDomainService.getTheTreatmentItem(visit001, mainDescribeTreatmentItemSpec);
		((SimpleTreatmentItemValue)item.getValues().get(0)).setInfo("患者咳嗽发烧三天，体温38.5，嗜睡");
		treatmentDomainService.update(item);
		
		intoWardRecord = medicalRecordAppService.find(intoWardRecord.getId());
		
		assertTrue(intoWardRecord.getState().equals(MedicalRecord.State_Signed));
		assertTrue(datas.get("主诉").getValues().get(0).toString().equals("患者咳嗽发烧三天，体温38.5"));


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
		drug002Order.setFrequencyType(orderFrequencyType_9H15H);

		drug002Order.setPlanStartDate(sysDate);
		drug002Order.setPlanEndDate(DateUtil.addDay(sysDate, 2));

		drug002Order.setTypeApp(new DrugOrderTypeApp(drugOrderType002,
				infusionOrderUseMode));

		// 创建药品003长期医嘱
		LongOrder drug003Order = new LongOrder();
		drug003Order.setVisit(visit001);
		drug003Order.setName("5%葡萄糖");
		drug003Order.setCount(1);
		drug003Order.setFrequencyType(orderFrequencyType_9H15H);

		drug003Order.setPlanStartDate(sysDate);
		drug003Order.setPlanEndDate(DateUtil.addDay(sysDate, 2));

		drug003Order.setTypeApp(new DrugOrderTypeApp(drugOrderType003,
				infusionOrderUseMode));

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

		TemporaryOrder brainInspectOrder = new TemporaryOrder();
		brainInspectOrder.setVisit(visit001);
		brainInspectOrder.setName("脑部检查");
		brainInspectOrder.setTypeApp(new SampleOrderTypeApp(inspectOrderType));
		brainInspectOrder.setPlanStartDate(DateUtil.getSysDate());

		InspectApply inspectApply = new InspectApply();
		inspectApply.setGoal("查查是否有问题");

		InspectApplyItem brainCTInspectApplyItem = new InspectApplyItem();
		brainCTInspectApplyItem.setInspectItem(brainCTInspectItem);
		brainCTInspectApplyItem.setArrangeDept(dept444);
		brainCTInspectApplyItem.setInspectDept(dept444);

		inspectApply.addInspectApplyItem(brainCTInspectApplyItem);

		InspectApplyItem brainHCInspectApplyItem = new InspectApplyItem();
		brainHCInspectApplyItem.setInspectItem(brainHCInspectItem);
		brainHCInspectApplyItem.setArrangeDept(dept555);
		brainHCInspectApplyItem.setInspectDept(dept555);

		inspectApply.addInspectApplyItem(brainHCInspectApplyItem);

		brainInspectOrder.setApply(inspectApply);

		orderAppService.create(brainInspectOrder, user002);

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

		assertTrue(executes.size() == 2);

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
				DateUtil.createMinute("2017-01-02 14:00"), user555);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 10:30"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user777,
				pageable);

		assertTrue(executes.size() == 1);

		// 安排检查时间
		inspectAppService.arrange(executes.get(0).getId(),
				DateUtil.createMinute("2017-01-03 14:00"), user777);

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

		Map<InspectApplyItem, String> CTResults = new HashMap<InspectApplyItem, String>();
		CTResults.put(brainCTInspectApplyItem, "没啥问题");
		inspectAppService.confirm(executes.get(0).getId(), CTResults, user666);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-02 15:00"));

		testUtil.testInspectResult(brainInspectOrder.getId(), 1);

		if ((Boolean) choices.get(ChoiceItem.CancelHC)) {
			inspectAppService.cancel(brainHCInspectApplyItem.getId(), user002);
		}

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

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-03 15:00"));

		if (!(Boolean) choices.get(ChoiceItem.CancelHC)) {
			pageable = new PageRequest(0, 15);
			executes = orderExecuteAppService.getNeedExecuteOrderExecutes(
					user888, pageable);

			assertTrue(executes.size() == 1);

			Map<InspectApplyItem, String> HCResults = new HashMap<InspectApplyItem, String>();
			HCResults.put(brainHCInspectApplyItem, "没啥问题");
			inspectAppService.confirm(executes.get(0).getId(), HCResults,
					user888);

			DateUtil.setSysDate(DateUtil.createMinute("2017-01-03 16:00"));

			testUtil.testInspectResult(brainInspectOrder.getId(), 2);
		}

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

	}

}
