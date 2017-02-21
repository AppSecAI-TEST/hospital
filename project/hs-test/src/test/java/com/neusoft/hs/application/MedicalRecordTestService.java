package com.neusoft.hs.application;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.inpatientdept.MedicalRecordAppService;
import com.neusoft.hs.domain.inspect.InspectDomainService;
import com.neusoft.hs.domain.inspect.InspectResult;
import com.neusoft.hs.domain.inspect.InspectResultMedicalRecordBuilder;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordBuilder;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordTypeBuilder;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.treatment.ItemValue;
import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.ListTreatmentItemValue;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class MedicalRecordTestService {

	@Autowired
	private InspectDomainService inspectDomainService;

	@Autowired
	private MedicalRecordAppService medicalRecordAppService;

	public void createTemporaryOrderListMedicalRecord(Visit visit,
			MedicalRecordType medicalRecordType, Doctor doctor)
			throws HsException {

		MedicalRecordBuilder builder = new MedicalRecordTypeBuilder(
				medicalRecordType, visit);

		MedicalRecord temporaryOrderListRecord = medicalRecordAppService
				.create(builder, visit, medicalRecordType, doctor);

		Map<String, Itemable> datas = temporaryOrderListRecord.getDatas();

		List<? extends ItemValue> itemValue;

		itemValue = datas.get("患者姓名").getValues();
		assertTrue(itemValue.get(0).toString().equals("测试患者001"));

		itemValue = datas.get("临时医嘱列表").getValues();
		assertTrue(itemValue.size() == 3);
		assertTrue(((ListTreatmentItemValue) itemValue.get(0)).getData()
				.get("name").equals("药品001"));

		medicalRecordAppService.create(temporaryOrderListRecord);

	}

	public void createInspectResultMedicalRecord(Visit visit,
			MedicalRecordType medicalRecordType, Doctor doctor)
			throws HsException {

		MedicalRecordBuilder builder;
		// 生成检查单病历
		List<InspectResult> results = inspectDomainService
				.findInspectResults(visit);
		assertTrue(results.size() == 1);
		for (InspectResult result : results) {
			builder = new InspectResultMedicalRecordBuilder(result);
			MedicalRecord inspectResultRecord = medicalRecordAppService.create(
					builder, visit, medicalRecordType, doctor);

			Map<String, Itemable> datas = inspectResultRecord.getDatas();

			List<? extends ItemValue> itemValue;

			itemValue = datas.get("患者姓名").getValues();
			assertTrue(itemValue.get(0).toString().equals("测试患者001"));

			itemValue = datas.get("检查结果").getValues();
			assertTrue(itemValue.get(0).toString().equals("没啥问题"));

			itemValue = datas.get("检查日期").getValues();
			assertTrue(itemValue.get(0).toString()
					.equals("2017-01-02 14:40:00"));

			itemValue = datas.get("检查部门").getValues();
			assertTrue(itemValue.get(0).toString().equals("CT室"));

			medicalRecordAppService.create(inspectResultRecord);
		}

	}

}
