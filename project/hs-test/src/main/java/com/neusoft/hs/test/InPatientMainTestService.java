package com.neusoft.hs.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.application.pharmacy.PatientPharmacyAppService;
import com.neusoft.hs.domain.inspect.InspectApply;
import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.order.CompsiteOrder;
import com.neusoft.hs.domain.order.DrugOrderTypeApp;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.pharmacy.ConfigureFluidOrder;
import com.neusoft.hs.domain.pharmacy.DispensingDrugOrder;
import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class InPatientMainTestService extends InPatientTestService {

	@Autowired
	private PatientPharmacyAppService patientPharmacyAppService;

	@Override
	protected void treatment() throws HsException {

		Pageable pageable;
		List<OrderExecute> executes;
		List<Order> orders;
		Date sysDate;
		Date startDate;
		DispensingDrugOrder dispensingDrugOrder;
		Order drug001Order1;
		Order drug001Order4;

		LongOrder firstNursingOrder;
		LongOrder secondNursingOrder;

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:40", dayCount));

		// 为001开立一级护理长期医嘱
		firstNursingOrder = new LongOrder();
		firstNursingOrder.setVisit(visit001);
		firstNursingOrder.setName("一级护理");
		firstNursingOrder.setFrequencyType(orderFrequencyType_0H);
		firstNursingOrder.setPlanStartDate(DateUtil.getSysDateStart());
		firstNursingOrder.setPlaceType(OrderCreateCommand.PlaceType_InPatient);
		firstNursingOrder.setOrderType(firstNursingOrderType);

		orderAppService.create(firstNursingOrder, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:42", dayCount));

		// 为004开立二级护理长期医嘱
		secondNursingOrder = new LongOrder();
		secondNursingOrder.setVisit(visit004);
		secondNursingOrder.setName("二级护理");
		secondNursingOrder.setFrequencyType(orderFrequencyType_0H);
		secondNursingOrder.setPlanStartDate(DateUtil.getSysDateStart());
		secondNursingOrder.setPlaceType(OrderCreateCommand.PlaceType_InPatient);
		secondNursingOrder.setOrderType(secondNursingOrderType);

		orderAppService.create(secondNursingOrder, user002);

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:45", dayCount));

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:55", dayCount));

		// 为患者001开立药品临时医嘱
		drug001Order1 = new TemporaryOrder();
		drug001Order1.setVisit(visit001);
		drug001Order1.setName("药品001");
		drug001Order1.setPlanStartDate(DateUtil.getSysDate());
		drug001Order1.setCount(2);
		drug001Order1.setPlaceType(OrderCreateCommand.PlaceType_InPatient);
		drug001Order1.setOrderType(drugOrderType001);

		drug001Order1
				.setTypeApp(new DrugOrderTypeApp(deptccc, oralOrderUseMode));

		orderAppService.create(drug001Order1, user002);
		
		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:56", dayCount));

		// 为患者004开立药品临时医嘱
		drug001Order4 = new TemporaryOrder();
		drug001Order4.setVisit(visit004);
		drug001Order4.setName("药品001");
		drug001Order4.setPlanStartDate(DateUtil.getSysDate());
		drug001Order4.setCount(4);
		drug001Order4.setPlaceType(OrderCreateCommand.PlaceType_InPatient);
		drug001Order4.setOrderType(drugOrderType001);

		drug001Order4
				.setTypeApp(new DrugOrderTypeApp(deptccc, oralOrderUseMode));

		orderAppService.create(drug001Order4, user002);

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:00", dayCount));

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		pageable = new PageRequest(0, 15);
		executes = orderAppService.getNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:05", dayCount));

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:15", dayCount));

		// pageable = new PageRequest(0, 15);
		// executes =
		// orderExecuteAppService.getNeedExecuteOrderExecutes(userc01,
		// pageable);
		//
		// assertTrue(executes.size() == 1);
		//
		// // 完成摆药医嘱执行条目
		// orderExecuteAppService.finish(executes.get(0).getId(), null,
		// userc01);

		dispensingDrugOrder = patientPharmacyAppService.print(dept000n,
				dayDispensingDrugBatch, userc01);

		assertTrue(dispensingDrugOrder.getExecutes().size() == 2);

		patientPharmacyAppService
				.dispense(dispensingDrugOrder.getId(), userc01);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:30", dayCount));

		// pageable = new PageRequest(0, 15);
		// executes =
		// orderExecuteAppService.getNeedExecuteOrderExecutes(userc03,
		// pageable);
		//
		// assertTrue(executes.size() == 1);
		//
		// // 完成发药医嘱执行条目
		// for (OrderExecute execute : executes) {
		// orderExecuteAppService.finish(execute.getId(), null, userc03);
		// }

		dispensingDrugOrder = patientPharmacyAppService.distribute(
				dispensingDrugOrder.getId(), userc01);

		assertTrue(dispensingDrugOrder.getExecutes().size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:32", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的一级护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:45", dayCount));

		// 取消医嘱条目
		orderAppService.cancel(drug001Order1.getId(), user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 13:45", dayCount));

		pageable = new PageRequest(0, 15);
		executes = costDomainService.getNeedBackChargeOrderExecutes(user201,
				pageable);

		assertTrue(executes.size() == 1);

		costAppService.unCharging(executes.get(0).getId(), true, user003);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 14:05", dayCount));

		Visit currentVisit = visitDomainService.find(visit001.getId());

		List<TreatmentItemSpec> treatmentItemSpecs = treatmentAppService
				.getShouldTreatmentItemSpecs(currentVisit, user002);

		assertTrue(treatmentItemSpecs.size() == 1);

		// 创建主诉
		TreatmentItem item = new TreatmentItem();
		item.setVisit(visit001);
		item.setTreatmentItemSpec(mainDescribeTreatmentItemSpec);
		item.setCreator(user002);

		SimpleTreatmentItemValue value = new SimpleTreatmentItemValue();
		value.setInfo("患者咳嗽发烧三天");
		item.addValue(value);

		treatmentDomainService.create(item);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 14:20", dayCount));

		// 创建入院记录
		MedicalRecord intoWardRecord = medicalRecordTestService
				.createIntoWardRecord(visit001,
						intoWardRecordMedicalRecordType, user002);

		intoWardRecord = medicalRecordAppService.find(intoWardRecord.getId());

		Map<String, Itemable> datas = intoWardRecord.getDatas();

		assertTrue(datas.get("患者姓名").getValues().get(0).toString()
				.equals("测试患者001"));
		assertTrue(datas.get("主诉").getValues().get(0).toString()
				.equals("患者咳嗽发烧三天，体温38.5"));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 15:00", dayCount));

		medicalRecordAppService.sign(intoWardRecord.getId(), user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 17:00", dayCount));

		item = treatmentDomainService.getTheTreatmentItem(visit001,
				mainDescribeTreatmentItemSpec);
		((SimpleTreatmentItemValue) item.getValues().get(0))
				.setInfo("患者咳嗽发烧三天，体温38.5，嗜睡");
		treatmentDomainService.update(item);

		intoWardRecord = medicalRecordAppService.find(intoWardRecord.getId());

		datas = intoWardRecord.getDatas();

		assertTrue(intoWardRecord.getState().equals(MedicalRecord.State_Signed));
		assertTrue(datas.get("主诉").getValues().get(0).toString()
				.equals("患者咳嗽发烧三天，体温38.5"));

		// 2016-12-29
		DateUtil.setSysDate(DateUtil.createDay("2016-12-29", dayCount));
		inPatientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 09:10", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的一级护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 10:10", dayCount));

		// 创建药品002长期医嘱
		sysDate = DateUtil.getSysDate();
		startDate = DateUtil.getSysDateStart();

		LongOrder drug002Order = new LongOrder();
		drug002Order.setVisit(visit001);
		drug002Order.setName("头孢3");
		drug002Order.setCount(2);
		drug002Order.setFrequencyType(orderFrequencyType_9H15H);
		drug002Order.setPlaceType(OrderCreateCommand.PlaceType_InPatient);

		drug002Order.setPlanStartDate(sysDate);
		drug002Order.setPlanEndDate(DateUtil.addDay(sysDate, 2));

		drug002Order.setOrderType(drugOrderType002);

		drug002Order.setTypeApp(new DrugOrderTypeApp(deptbbb,
				infusionOrderUseModeToInPatient));

		// 创建药品003长期医嘱
		LongOrder drug003Order = new LongOrder();
		drug003Order.setVisit(visit001);
		drug003Order.setName("5%葡萄糖");
		drug003Order.setCount(1);
		drug003Order.setFrequencyType(orderFrequencyType_9H15H);
		drug003Order.setPlaceType(OrderCreateCommand.PlaceType_InPatient);

		drug003Order.setPlanStartDate(sysDate);
		drug003Order.setPlanEndDate(DateUtil.addDay(sysDate, 2));

		drug003Order.setOrderType(drugOrderType003);

		drug003Order.setTypeApp(new DrugOrderTypeApp(deptbbb,
				infusionOrderUseModeToInPatient));

		CompsiteOrder drug002003Order = new CompsiteOrder();
		drug002003Order.addOrder(drug002Order);
		drug002003Order.addOrder(drug003Order);

		orderAppService.create(drug002003Order, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 10:30", dayCount));

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 2);

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 11:05", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderAppService.getNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 4);

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 13:05", dayCount));

		// //以执行条目方式完成配液
		// pageable = new PageRequest(0, 15);
		// executes =
		// orderExecuteAppService.getNeedExecuteOrderExecutes(userb02,
		// pageable);
		//
		// assertTrue(executes.size() == 2);
		//
		// // 完成配液医嘱执行条目
		// for (OrderExecute execute : executes) {
		// orderExecuteAppService.finish(execute.getId(), null, userb02);
		// }

		ConfigureFluidOrder fluidOrder = configureFluidAppService.print(
				dept000n, afternoonConfigureFluidBatch, userb02);

		assertTrue(fluidOrder.getExecutes().size() == 2);

		configureFluidAppService.finish(fluidOrder.getId(), userb02);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 15:30", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2016-12-30
		DateUtil.setSysDate(DateUtil.createDay("2016-12-30", dayCount));
		inPatientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 08:30", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderAppService.getNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 4);

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 08:50", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(userb02,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成配液医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, userb02);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 09:10", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 4);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 14:00", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(userb02,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成配液医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, userb02);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 15:10", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2016-12-31
		DateUtil.setSysDate(DateUtil.createDay("2016-12-31", dayCount));
		inPatientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-31 08:30", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderAppService.getNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 0);

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(userb02,
				pageable);

		assertTrue(executes.size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-31 08:50", dayCount));

		// 完成配液医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, userb02);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-31 09:10", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 4);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2017-01-01
		DateUtil.setSysDate(DateUtil.createDay("2017-01-01", dayCount));
		inPatientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 09:10", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 09:30", dayCount));

		TemporaryOrder brainInspectOrder = new TemporaryOrder();
		brainInspectOrder.setVisit(visit001);
		brainInspectOrder.setName("脑部检查");
		brainInspectOrder.setOrderType(inspectOrderType);
		brainInspectOrder.setPlanStartDate(DateUtil.getSysDate());
		brainInspectOrder.setPlaceType(OrderCreateCommand.PlaceType_InPatient);

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

		DateUtil.setSysDate(DateUtil.createMinute("2016-01-01 09:40", dayCount));

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 09:45", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderAppService.getNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 10:10", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user401,
				pageable);

		assertTrue(executes.size() == 1);

		// 安排检查时间
		inspectAppService.arrange(executes.get(0).getId(),
				DateUtil.createMinute("2017-01-02 14:00", dayCount), user401);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 10:30", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user501,
				pageable);

		assertTrue(executes.size() == 1);

		// 安排检查时间
		inspectAppService.arrange(executes.get(0).getId(),
				DateUtil.createMinute("2017-01-03 14:00", dayCount), user501);

		// 2017-01-02
		DateUtil.setSysDate(DateUtil.createDay("2017-01-02", dayCount));
		inPatientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-02 09:10", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-02 14:40", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user402,
				pageable);

		assertTrue(executes.size() == 1);

		Map<InspectApplyItem, String> CTResults = new HashMap<InspectApplyItem, String>();
		CTResults.put(brainCTInspectApplyItem, "没啥问题");
		inspectAppService.confirm(executes.get(0).getId(), CTResults, user402);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-02 15:00", dayCount));

		testUtil.testInspectResult(brainInspectOrder.getId(), 1);

		if ((Boolean) choices.get(ChoiceItem.CancelHC)) {
			inspectAppService.cancel(brainHCInspectApplyItem.getId(), user002);
		}

		// 2017-01-03
		DateUtil.setSysDate(DateUtil.createDay("2017-01-03", dayCount));
		inPatientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-03 09:10", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-03 15:00", dayCount));

		if (!(Boolean) choices.get(ChoiceItem.CancelHC)) {
			pageable = new PageRequest(0, 15);
			executes = orderExecuteAppService.getNeedExecuteOrderExecutes(
					user502, pageable);

			assertTrue(executes.size() == 1);

			Map<InspectApplyItem, String> HCResults = new HashMap<InspectApplyItem, String>();
			HCResults.put(brainHCInspectApplyItem, "没啥问题");
			inspectAppService.confirm(executes.get(0).getId(), HCResults,
					user502);

			DateUtil.setSysDate(DateUtil.createMinute("2017-01-03 16:00",
					dayCount));

			testUtil.testInspectResult(brainInspectOrder.getId(), 2);
		}

		// 2017-01-04
		DateUtil.setSysDate(DateUtil.createDay("2017-01-04", dayCount));
		inPatientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-04 09:10", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2017-01-05
		DateUtil.setSysDate(DateUtil.createDay("2017-01-05", dayCount));
		inPatientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-05 08:20", dayCount));

		orderAppService.stop(firstNursingOrder.getId(), user002);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-05 08:30", dayCount));

		// 开立二级护理长期医嘱
		secondNursingOrder = new LongOrder();
		secondNursingOrder.setVisit(visit001);
		secondNursingOrder.setName("二级护理");
		secondNursingOrder.setFrequencyType(orderFrequencyType_0H);
		secondNursingOrder.setPlanStartDate(DateUtil.getSysDateStart());
		secondNursingOrder.setPlaceType(OrderCreateCommand.PlaceType_InPatient);
		secondNursingOrder.setOrderType(secondNursingOrderType);

		orderAppService.create(secondNursingOrder, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-05 08:40", dayCount));

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-05 09:10", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2017-01-06
		DateUtil.setSysDate(DateUtil.createDay("2017-01-06", dayCount));
		inPatientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 09:10", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

	}
}
