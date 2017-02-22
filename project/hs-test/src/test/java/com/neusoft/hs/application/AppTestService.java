package com.neusoft.hs.application;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderUtil;
import com.neusoft.hs.domain.order.SampleOrderTypeApp;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.pharmacy.DrugUseModeAssistMaterial;
import com.neusoft.hs.domain.visit.ReceiveVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public abstract class AppTestService extends DataIniter {

	@Autowired
	protected OrderUtil orderUtil;

	@Autowired
	protected TestUtil testUtil;

	protected Map<ChoiceItem, Object> choices;

	public void testInit() {
		// 初始化Context
		ApplicationContext applicationContext = SpringApplication
				.run(Application.class);
		ApplicationContextUtil.setApplicationContext(applicationContext);

		clear();

		initData();

		choice();

		ready();
	}

	/**
	 * 
	 * @throws HsException
	 */
	public void execute() throws HsException {

		this.intoWard();

		this.treatment();

		this.outWard();

		this.followUp();

	}

	protected abstract void treatment() throws HsException;

	protected void intoWard() throws HsException {

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
		Visit visit;
		List<Order> orders;

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
	}

	private void outWard() throws HsException {

		Pageable pageable;
		List<OrderExecute> executes;
		List<Order> orders;
		int startedCount;
		int resolveCount;
		Visit visit;

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

		leaveHospitalOrder.setTypeApp(new SampleOrderTypeApp(
				leaveHospitalOrderType));

		orderAppService.create(leaveHospitalOrder, user002);

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);
		assertTrue(orders.get(0).getId().equals(leaveHospitalOrder.getId()));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 10:30"));

		// 核对医嘱
		orderAppService.verify(leaveHospitalOrder.getId(), user003);

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user003);
		}

		// 2017-01-08
		DateUtil.setSysDate(DateUtil.createDay("2017-01-08"));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-08 09:10"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user003);
		}

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

	public void followUp() throws HsException {
		//整理病历夹
		arrangementMedicalRecord();
		
		m
	}

	public void arrangementMedicalRecord() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 14:30"));

		// 创建临时医嘱单
		medicalRecordTestService.createTemporaryOrderListMedicalRecord(
				visit001, temporaryOrderListMedicalRecordType, user002);

		// 生成检查单病历
		medicalRecordTestService.createInspectResultMedicalRecord(visit001,
				inspectResultMedicalRecordType, user002);
	}

	private void choice() {

		this.choices = new HashMap<ChoiceItem, Object>();
		this.choices.put(ChoiceItem.OrderUseModeAssistMaterial,
				onlyOneOrderUseModeAssistMaterial);
		this.choices.put(ChoiceItem.CancelHC, true);
	}

	private void ready() {
		this.choiceOrderUseModeAssistMaterial((DrugUseModeAssistMaterial) this.choices
				.get(ChoiceItem.OrderUseModeAssistMaterial));
	}

	private void choiceOrderUseModeAssistMaterial(
			DrugUseModeAssistMaterial orderUseModeAssistMaterial) {
		pharmacyDomainService
				.createOrderUseModeAssistMaterial(orderUseModeAssistMaterial);
	}

}
