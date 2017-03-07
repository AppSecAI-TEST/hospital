package com.neusoft.hs.application;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.medicalrecord.MedicalRecordClip;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.SampleOrderTypeApp;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.pharmacy.DrugUseModeAssistMaterial;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.ReceiveVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public abstract class InPatientTestService extends AppTestService {

	protected Map<ChoiceItem, Object> choices;

	protected int dayCount = 0;

	private static final int runCount = 1;// 入院次数

	@Override
	public void testInit() {
		super.testInit();

		choice();

		ready();
	}

	/**
	 * 
	 * @throws HsException
	 */
	@Override
	public void execute() throws HsException {

		for (int count = 0; count < runCount; count++) {

			dayCount = count * 20;

			this.intoWard();

			this.treatment();

			this.outWard();

			this.followUp();
		}

	}

	protected abstract void treatment() throws HsException;

	protected void intoWard() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:50", dayCount));

		// 创建测试患者
		CreateVisitVO createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("211381197801270235");
		createVisitVO.setName("测试患者001");
		createVisitVO.setBirthday(DateUtil.createDay("1978-01-27"));
		createVisitVO.setSex("男");
		createVisitVO.setOperator(user002);
		createVisitVO.setRespDept(dept000);
		createVisitVO.setRespDoctor(user002);

		// 送诊
		visit001 = registerAppService.register(createVisitVO);

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

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:10", dayCount));

		// 预存费用
		cashierAppService.initAccount(visit001.getId(), 2000F, user201);

		pageable = new PageRequest(0, 15);
		visits = inPatientAppService.getNeedReceiveVisits(user001, pageable);

		assertTrue(visits.size() == 1);
		assertTrue(visits.get(0).getId().equals(visit001.getId()));

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedIntoWard));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:30", dayCount));

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

	protected void outWard() throws HsException {

		Pageable pageable;
		List<OrderExecute> executes;
		List<Order> orders;
		int startedCount;
		int resolveCount;
		Visit visit;

		// 2017-01-07
		DateUtil.setSysDate(DateUtil.createDay("2017-01-07", dayCount));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 10:10", dayCount));

		// 开立出院临时医嘱
		Order leaveHospitalOrder = new TemporaryOrder();
		leaveHospitalOrder.setVisit(visit001);
		leaveHospitalOrder.setName("出院医嘱");
		leaveHospitalOrder.setPlanStartDate(DateUtil.createDay("2017-01-09",
				dayCount));
		leaveHospitalOrder.setExecuteDept(dept222);

		leaveHospitalOrder.setTypeApp(new SampleOrderTypeApp(
				leaveHospitalOrderType));

		orderAppService.create(leaveHospitalOrder, user002);

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);
		assertTrue(orders.get(0).getId().equals(leaveHospitalOrder.getId()));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 10:30", dayCount));

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
		DateUtil.setSysDate(DateUtil.createDay("2017-01-08", dayCount));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-08 09:10", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user003);
		}

		// 2017-01-09
		DateUtil.setSysDate(DateUtil.createDay("2017-01-09", dayCount));
		resolveCount = orderAppService.resolve();
		startedCount = orderExecuteAppService.start();

		assertTrue(startedCount == 1);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 09:30", dayCount));

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
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user201,
				pageable);

		assertTrue(executes.size() == 1);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 10:30", dayCount));

		// 完成出院结算医嘱执行条目
		orderExecuteAppService.finish(executes.get(0).getId(), user201);

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_LeaveHospital));
	}

	public void followUp() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 14:30", dayCount));

		// 整理病历
		arrangementMedicalRecord();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 15:00", dayCount));

		medicalRecordAppService.transfer(visit001, dept666);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-10 09:30", dayCount));

		List<MedicalRecordClip> clips = qualityControlAppService
				.findNeedCheckRecordClips(user601);

		assertTrue(clips.size() == 1);

		qualityControlAppService.pass(clips.get(0).getId(), user601);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-11 10:30", dayCount));

		String position = "Num001";
		recordRoomDomainService
				.archive(clips.get(0).getId(), position, user602);

	}

	public void arrangementMedicalRecord() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 14:30", dayCount));

		medicalRecordTestService.setDayCount(dayCount);

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
