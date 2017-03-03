package com.neusoft.hs.application;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
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
	}
}
