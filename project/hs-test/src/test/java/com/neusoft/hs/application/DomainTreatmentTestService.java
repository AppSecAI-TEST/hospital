package com.neusoft.hs.application;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class DomainTreatmentTestService extends AppTestService {

	@Override
	protected void treatment() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:55"));
		
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
		
		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:20"));
		
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
		
		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 14:20"));
		
		medicalRecordAppService.sign(intoWardRecord.getId(), user002);
		
		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 17:00"));
		
		item = treatmentDomainService.getTheTreatmentItem(visit001, mainDescribeTreatmentItemSpec);
		((SimpleTreatmentItemValue)item.getValues().get(0)).setInfo("患者咳嗽发烧三天，体温38.5，嗜睡");
		treatmentDomainService.update(item);
		
		intoWardRecord = medicalRecordAppService.find(intoWardRecord.getId());
		
		assertTrue(intoWardRecord.getState().equals(MedicalRecord.State_Signed));
		assertTrue(datas.get("主诉").getValues().get(0).toString().equals("患者咳嗽发烧三天，体温38.5"));

	}
}
