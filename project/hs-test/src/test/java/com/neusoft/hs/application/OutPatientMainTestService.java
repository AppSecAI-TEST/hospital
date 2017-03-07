package com.neusoft.hs.application;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
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

		// 创建测试患者
		CreateVisitVO createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("211381197801270235");
		createVisitVO.setName("测试患者001");
		createVisitVO.setBirthday(DateUtil.createDay("1978-01-27"));
		createVisitVO.setSex("男");
		createVisitVO.setOperator(user002);

		visit001 = registrationAppService.register(createVisitVO, planRecord,
				user901);
	}
}
