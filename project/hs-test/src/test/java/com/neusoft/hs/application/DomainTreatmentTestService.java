package com.neusoft.hs.application;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class DomainTreatmentTestService extends AppTestService {

	@Override
	protected void treatment() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:55"));

		TreatmentItem item = new TreatmentItem();
		item.setVisit(visit001);
		item.setTreatmentItemSpec(mainDescribeTreatmentItemSpec);
		item.setCreator(user002);
		
		SimpleTreatmentItemValue value = new SimpleTreatmentItemValue();
		value.setInfo("患者咳嗽发烧三天");
		item.addValue(value);
		
		treatmentDomainService.create(item);
	}
}
