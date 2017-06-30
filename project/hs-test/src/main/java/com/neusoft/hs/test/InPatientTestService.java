package com.neusoft.hs.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordClip;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.ReceiveVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public abstract class InPatientTestService extends AppTestService {

	protected Visit visit001;

	protected Visit visit004;

	protected List<Visit> visitList = new ArrayList<Visit>();

	protected int dayCount = 0;

	@Autowired
	protected InPatientNightTestService inPatientNightTestService;

	private static final int runCount = 1;// 入院次数

	@Override
	public void testInit() {
		super.testInit();

		MedicalRecordTestService.temporaryOrderCount = 3;
	}

	/**
	 * 
	 * @throws HsException
	 */
	@Override
	public void execute() throws HsException {

		for (int count = 0; count < runCount; count++) {

			dayCount = count * 20;

			this.createVisit001();

			this.createVisit004();

			this.doExecute();
		}
	}

	public void doExecute() throws HsException {

		this.intoWard();

		this.treatment();

		this.outWard();

		this.followUp();
	}

	protected abstract void treatment() throws HsException;

	protected void createVisit001() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:50", dayCount));
		// 创建测试患者
		CreateVisitVO createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("xxx");
		createVisitVO.setName("测试患者001");
		createVisitVO.setBirthday(DateUtil.createDay("1978-01-27"));
		createVisitVO.setSex("男");
		createVisitVO.setOperator(user002);
		createVisitVO.setDept(dept000);
		createVisitVO.setArea(dept000n);
		createVisitVO.setRespDoctor(user002);
		// 送诊
		visit001 = registerAppService.register(createVisitVO);

		visitList.add(visit001);
	}

	protected void createVisit004() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:52", dayCount));
		// 创建测试患者
		CreateVisitVO createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("aaa");
		createVisitVO.setName("测试患者004");
		createVisitVO.setBirthday(DateUtil.createDay("1980-01-22"));
		createVisitVO.setSex("男");
		createVisitVO.setOperator(user002);
		createVisitVO.setDept(dept000);
		createVisitVO.setArea(dept000n);
		createVisitVO.setRespDoctor(user002);
		// 送诊
		visit004 = registerAppService.register(createVisitVO);

		visitList.add(visit004);
	}

	protected void intoWard() throws HsException {

		Pageable pageable;
		List<Visit> visits;
		Visit visit;
		ReceiveVisitVO receiveVisitVO;

		pageable = new PageRequest(0, 15);
		visits = cashierAppService.getNeedInitAccountVisits(pageable);

		assertTrue(visits.size() == 2);
		for (Visit v : visits) {
			assertTrue(visitList.contains(v));
		}

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedInitAccount));

		visit = visitDomainService.find(visit004.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedInitAccount));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:10", dayCount));

		// 预存费用
		if (!visit001.isInitedAccount()) {
			cashierAppService.initAccount(visit001.getId(), 2000F, user201);
		} else {
			cashierAppService.addCost(visit001.getId(), 2000F, user201);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:12", dayCount));

		cashierAppService.initAccount(visit004.getId(), 3000F, user201);

		pageable = new PageRequest(0, 15);
		visits = inPatientAppService.getNeedReceiveVisits(user001, pageable);

		assertTrue(visits.size() == 2);

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedIntoWard));

		visit = visitDomainService.find(visit004.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedIntoWard));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:30", dayCount));

		// 接诊
		receiveVisitVO = new ReceiveVisitVO();
		receiveVisitVO.setVisit(visit001);
		receiveVisitVO.setBed("bed001");
		receiveVisitVO.setNurse(user003);

		inPatientAppService.receive(receiveVisitVO, user001);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:32", dayCount));

		receiveVisitVO = new ReceiveVisitVO();
		receiveVisitVO.setVisit(visit004);
		receiveVisitVO.setBed("bed004");
		receiveVisitVO.setNurse(user003);

		inPatientAppService.receive(receiveVisitVO, user001);

		pageable = new PageRequest(0, 15);
		visits = inPatientAppService.InWardVisits(dept000, pageable);

		assertTrue(visits.size() == 2);

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_IntoWard));

		visit = visitDomainService.find(visit004.getId());

		assertTrue(visit.getState().equals(Visit.State_IntoWard));
	}

	protected void outWard() throws HsException {

		Pageable pageable;
		List<OrderExecute> executes;
		List<Order> orders;
		Visit visit;

		// 2017-01-07
		DateUtil.setSysDate(DateUtil.createDay("2017-01-07", dayCount));
		inPatientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 10:10", dayCount));

		// 开立出院临时医嘱
		Order leaveHospitalOrder001 = new TemporaryOrder();
		leaveHospitalOrder001.setVisit(visit001);
		leaveHospitalOrder001.setName("出院医嘱");
		leaveHospitalOrder001.setPlanStartDate(DateUtil.createDay("2017-01-09",
				dayCount));
		leaveHospitalOrder001
				.setPlaceType(OrderCreateCommand.PlaceType_InPatient);

		leaveHospitalOrder001.setOrderType(leaveHospitalOrderType);

		orderAppService.create(leaveHospitalOrder001, user002);

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);
		assertTrue(orders.get(0).getId().equals(leaveHospitalOrder001.getId()));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 10:30", dayCount));

		// 核对医嘱
		orderAppService.verify(leaveHospitalOrder001.getId(), user003);

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2017-01-08
		DateUtil.setSysDate(DateUtil.createDay("2017-01-08", dayCount));
		inPatientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-08 09:10", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2017-01-09
		DateUtil.setSysDate(DateUtil.createDay("2017-01-09", dayCount));
		inPatientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 09:30", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		assertTrue(executes.size() == 2);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
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
		orderExecuteAppService.finish(executes.get(0).getId(), null, user201);

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_OutHospital));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 10:40", dayCount));

		// 开立出院临时医嘱
		Order leaveHospitalOrder002 = new TemporaryOrder();
		leaveHospitalOrder002.setVisit(visit004);
		leaveHospitalOrder002.setName("出院医嘱");
		leaveHospitalOrder002.setPlanStartDate(DateUtil.createDay("2017-01-09",
				dayCount));
		leaveHospitalOrder002.setExecuteDept(dept222);
		leaveHospitalOrder002
				.setPlaceType(OrderCreateCommand.PlaceType_InPatient);

		leaveHospitalOrder002.setOrderType(leaveHospitalOrderType);

		orderAppService.create(leaveHospitalOrder002, user002);

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);
		assertTrue(orders.get(0).getId().equals(leaveHospitalOrder002.getId()));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 11:00", dayCount));

		// 核对医嘱
		orderAppService.verify(leaveHospitalOrder002.getId(), user003);

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user003,
				pageable);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 11:02", dayCount));

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		visit = visitDomainService.find(visit004.getId());

		assertTrue(visit.getState()
				.equals(Visit.State_NeedLeaveHospitalBalance));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 11:30", dayCount));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user201,
				pageable);

		assertTrue(executes.size() == 1);

		// 完成出院结算医嘱执行条目
		orderExecuteAppService.finish(executes.get(0).getId(), null, user201);

		visit = visitDomainService.find(visit004.getId());

		assertTrue(visit.getState().equals(Visit.State_OutHospital));
	}

	public void followUp() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 14:30", dayCount));

		// 整理病历
		arrangementMedicalRecord();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 15:00", dayCount));

		// 病历移交档案室
		medicalRecordAppService.transfer(visit001, dept666, user003);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-10 09:30", dayCount));

		List<MedicalRecordClip> clips = qualityControlAppService
				.findNeedCheckRecordClips(user601);

		assertTrue(clips.size() == 1);

		// 审查病历
		qualityControlAppService.pass(clips.get(0).getId(), user601);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-11 10:30", dayCount));

		// 归档病历
		String position = "Num001";
		recordRoomDomainService
				.archive(clips.get(0).getId(), position, user602);

	}

	public void arrangementMedicalRecord() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 14:30", dayCount));

		medicalRecordTestService.setDayCount(dayCount);

		MedicalRecord medicalRecord;
		List<MedicalRecord> medicalRecords;
		// 创建临时医嘱单
		medicalRecord = medicalRecordTestService
				.createTemporaryOrderListMedicalRecord(visit001,
						temporaryOrderListMedicalRecordType, user002);

		medicalRecordAppService.fix(medicalRecord.getId(), user002);

		// 生成检查单病历
		medicalRecords = medicalRecordTestService
				.createInspectResultMedicalRecord(visit001,
						inspectResultMedicalRecordType, user002);

		for (MedicalRecord mr : medicalRecords) {
			medicalRecordAppService.fix(mr.getId(), user002);
		}
	}

	public Visit getVisit001() {
		return visit001;
	}

	public void setVisit001(Visit visit001) {
		this.visit001 = visit001;

		visitList.add(visit001);
	}

	public Visit getVisit004() {
		return visit004;
	}

	public void setVisit004(Visit visit004) {
		this.visit004 = visit004;
	}

}
