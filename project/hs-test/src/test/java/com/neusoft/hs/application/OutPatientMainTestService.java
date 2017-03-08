package com.neusoft.hs.application;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.domain.pharmacy.DrugOrderType;
import com.neusoft.hs.domain.pharmacy.DrugOrderTypeApp;
import com.neusoft.hs.domain.registration.Voucher;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class OutPatientMainTestService extends AppTestService {

	@Override
	public void execute() throws HsException {

		OutPatientPlanRecord planRecord = new OutPatientPlanRecord();

		planRecord.setDoctor(user002);
		planRecord.setVoucherType(ordinaryVoucherType);
		planRecord.setPlanStartDate(DateUtil.createMinute("2016-12-28 08:30"));
		planRecord.setPlanEndDate(DateUtil.createMinute("2016-12-28 17:00"));
		planRecord.setRoom(room901);

		outPatientPlanDomainService.createPlanRecord(planRecord);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:00"));

		CreateVisitVO createVisitVO;
		Visit theVisit;
		Pageable pageable;
		List<OrderExecute> executes;
		
		// 创建测试患者
		createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("xxx");
		createVisitVO.setName("测试患者001");
		createVisitVO.setBirthday(DateUtil.createDay("1978-01-27"));
		createVisitVO.setSex("男");
		createVisitVO.setOperator(user002);

		Voucher voucher001 = registrationAppService.register(createVisitVO,
				planRecord.getId(), user901);
		visit001 = voucher001.getVisit();

		assertTrue(visit001.getState().equals(Visit.State_WaitingDiagnose));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:10"));

		// 创建测试患者
		createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("yyy");
		createVisitVO.setName("测试患者002");
		createVisitVO.setBirthday(DateUtil.createDay("2009-11-03"));
		createVisitVO.setSex("女");
		createVisitVO.setOperator(user002);

		Voucher voucher002 = registrationAppService.register(createVisitVO,
				planRecord.getId(), user901);
		visit002 = voucher002.getVisit();

		assertTrue(visit002.getState().equals(Visit.State_WaitingDiagnose));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:20"));

		boolean rtn;
		rtn = outPatientDeptAppService.nextVoucher(planRecord.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));

		// 开立药品临时医嘱
		Order drug001Order = new TemporaryOrder();
		drug001Order.setVisit(visit001);
		drug001Order.setName("药品001");
		drug001Order.setPlanStartDate(DateUtil.getSysDate());
		drug001Order.setCount(2);
		drug001Order.setPlaceType(OrderCreateCommand.PlaceType_OutPatient);

		DrugOrderType drugOrderType = new DrugOrderType();
		drugOrderType.setDrugTypeSpec(drugTypeSpec001);

		drug001Order.setTypeApp(new DrugOrderTypeApp(drugOrderType,
				oralOrderUseMode));

		orderAppService.create(drug001Order, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:35"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosed_Executing));

		theVisit = visitDomainService.find(visit002.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:45"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord.getId());

		assertTrue(!rtn);

		theVisit = visitDomainService.find(visit002.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosed_Executing));
		
		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:46"));
		
		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user301,
				pageable);

		assertTrue(executes.size() == 1);

		// 完成摆药医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user301);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:48"));

		pageable = new PageRequest(0, 15);
		executes = orderExecuteAppService.getNeedExecuteOrderExecutes(user301,
				pageable);

		assertTrue(executes.size() == 1);

		// 完成取药医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), user301);
		}
		
		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:50"));

		// 创建测试患者
		createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("zzz");
		createVisitVO.setName("测试患者003");
		createVisitVO.setBirthday(DateUtil.createDay("2009-11-03"));
		createVisitVO.setSex("女");
		createVisitVO.setOperator(user002);

		Voucher voucher003 = registrationAppService.register(createVisitVO,
				planRecord.getId(), user901);
		visit003 = voucher003.getVisit();

		assertTrue(visit003.getState().equals(Visit.State_WaitingDiagnose));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:55"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit003.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));
	}
}
