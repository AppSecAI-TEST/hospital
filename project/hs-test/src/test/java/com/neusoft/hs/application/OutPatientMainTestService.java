package com.neusoft.hs.application;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.SampleOrderTypeApp;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.domain.pharmacy.DrugOrderType;
import com.neusoft.hs.domain.pharmacy.DrugOrderTypeApp;
import com.neusoft.hs.domain.registration.Voucher;
import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class OutPatientMainTestService extends AppTestService {

	protected Visit visit001;

	protected Visit visit002;

	protected Visit visit003;

	@Override
	public void execute() throws HsException {

		List<OutPatientPlanRecord> planRecords = new ArrayList<OutPatientPlanRecord>();

		OutPatientPlanRecord planRecord1 = new OutPatientPlanRecord();

		planRecord1.setDoctor(user002);
		planRecord1.setVoucherType(ordinaryVoucherType);
		planRecord1.setPlanStartDate(DateUtil.createMinute("2016-12-27 08:30"));
		planRecord1.setPlanEndDate(DateUtil.createMinute("2016-12-27 17:00"));
		planRecord1.setRoom(room901);

		planRecords.add(planRecord1);

		OutPatientPlanRecord planRecord2 = new OutPatientPlanRecord();

		planRecord2.setDoctor(user002);
		planRecord2.setVoucherType(ordinaryVoucherType);
		planRecord2.setPlanStartDate(DateUtil.createMinute("2016-12-28 08:30"));
		planRecord2.setPlanEndDate(DateUtil.createMinute("2016-12-28 17:00"));
		planRecord2.setRoom(room901);

		planRecords.add(planRecord2);

		outPatientPlanDomainService.createPlanRecords(planRecords);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:00"));

		CreateVisitVO createVisitVO;
		Visit theVisit;
		Pageable pageable;
		List<OrderExecute> executes;
		int changedCount;

		// 创建测试患者
		createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("xxx");
		createVisitVO.setName("测试患者001");
		createVisitVO.setBirthday(DateUtil.createDay("1978-01-27"));
		createVisitVO.setSex("男");
		createVisitVO.setOperator(user901);

		Voucher voucher001 = registrationAppService.register(createVisitVO,
				planRecord1.getId(), user901);
		visit001 = voucher001.getVisit();

		assertTrue(visit001.getState().equals(Visit.State_WaitingDiagnose));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:10"));

		// 创建测试患者
		createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("yyy");
		createVisitVO.setName("测试患者002");
		createVisitVO.setBirthday(DateUtil.createDay("2009-11-03"));
		createVisitVO.setSex("女");
		createVisitVO.setOperator(user901);

		Voucher voucher002 = registrationAppService.register(createVisitVO,
				planRecord1.getId(), user901);
		visit002 = voucher002.getVisit();

		assertTrue(visit002.getState().equals(Visit.State_WaitingDiagnose));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:20"));

		boolean rtn;
		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:25"));

		// 开立药品临时医嘱
		Order drug001Order = new TemporaryOrder();
		drug001Order.setVisit(visit001);
		drug001Order.setName("药品001");
		drug001Order.setPlanStartDate(DateUtil.getSysDate());
		drug001Order.setCount(1);
		drug001Order.setPlaceType(OrderCreateCommand.PlaceType_OutPatient);

		DrugOrderType drugOrderType = new DrugOrderType();
		drugOrderType.setDrugTypeSpec(drugTypeSpec001);

		drug001Order.setTypeApp(new DrugOrderTypeApp(drugOrderType,
				oralOrderUseMode));

		orderAppService.create(drug001Order, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:27"));

		// 创建主诉
		TreatmentItem item = new TreatmentItem();
		item.setVisit(visit001);
		item.setTreatmentItemSpec(mainDescribeTreatmentItemSpec);
		item.setCreator(user002);

		SimpleTreatmentItemValue value = new SimpleTreatmentItemValue();
		value.setInfo("患者咳嗽发烧两天");
		item.addValue(value);

		treatmentDomainService.create(item);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:30"));

		// 创建门诊记录
		MedicalRecord outPatientRecord = medicalRecordTestService
				.createOutPatientRecord(visit001,
						outPatientRecordMedicalRecordType, user002);

		outPatientRecord = medicalRecordAppService.find(outPatientRecord
				.getId());

		Map<String, Itemable> datas = outPatientRecord.getDatas();

		assertTrue(datas.get("患者姓名").getValues().get(0).toString()
				.equals("测试患者001"));
		assertTrue(datas.get("主诉").getValues().get(0).toString()
				.equals("患者咳嗽发烧两天，体温37.5"));

		medicalRecordAppService.fix(outPatientRecord.getId(), user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:35"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosed_Executing));

		theVisit = visitDomainService.find(visit002.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:45"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(!rtn);

		theVisit = visitDomainService.find(visit002.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosed_Executing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:46"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user301,
				pageable);

		assertTrue(executes.size() == 1);

		// 完成摆药医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user301);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:48"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user301,
				pageable);

		assertTrue(executes.size() == 1);

		// 完成取药医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user301);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:50"));

		// 创建测试患者
		createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("zzz");
		createVisitVO.setName("测试患者003");
		createVisitVO.setBirthday(DateUtil.createDay("2009-11-03"));
		createVisitVO.setSex("女");
		createVisitVO.setOperator(user901);

		Voucher voucher003 = registrationAppService.register(createVisitVO,
				planRecord1.getId(), user901);
		visit003 = voucher003.getVisit();

		assertTrue(visit003.getState().equals(Visit.State_WaitingDiagnose));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:55"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit003.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 10:05"));

		registrationAppService.repeatOccupy(voucher001, planRecord1.getId(),
				user901);

		assertTrue(voucher001.getNumber() == 4);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 10:15"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit003.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosed_Executing));

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 10:20"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(!rtn);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosed_Executing));

		// 2016-12-28
		DateUtil.setSysDate(DateUtil.createDay("2016-12-28"));
		changedCount = visitDomainService.changeVisitState();

		assertTrue(changedCount == 3);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:00"));

		// 创建测试患者
		createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("xxx");
		createVisitVO.setName("测试患者001");
		createVisitVO.setBirthday(DateUtil.createDay("1978-01-27"));
		createVisitVO.setSex("男");
		createVisitVO.setOperator(user002);
		createVisitVO.setRepeatVisit(true);

		Voucher voucher001x = registrationAppService.register(createVisitVO,
				planRecord2.getId(), user901);
		theVisit = voucher001x.getVisit();

		assertTrue(theVisit.getState().equals(Visit.State_WaitingDiagnose));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:05"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord2.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:10"));

		// 开立入院医嘱
		Order enterHospitalOrder = new TemporaryOrder();
		enterHospitalOrder.setVisit(visit001);
		enterHospitalOrder.setName("入院医嘱");
		enterHospitalOrder.setExecuteDept(dept111);
		enterHospitalOrder.setPlanStartDate(DateUtil.getSysDate());
		enterHospitalOrder
				.setPlaceType(OrderCreateCommand.PlaceType_OutPatient);

		enterHospitalOrder.setTypeApp(new SampleOrderTypeApp(
				enterHospitalOrderType));

		enterHospitalOrder.addParam("wardDept", dept000);

		orderAppService.create(enterHospitalOrder, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:15"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord2.getId());

		assertTrue(!rtn);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_WaitingEnterHospital));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user101,
				pageable);

		assertTrue(executes.size() == 1);

		// 完成住院医嘱执行条目
		orderExecuteAppService.finish(executes.get(0).getId(), user101);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_NeedInitAccount));

	}

	public Visit getVisit() {
		return visit001;
	}
}
