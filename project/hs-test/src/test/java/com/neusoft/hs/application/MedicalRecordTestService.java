package com.neusoft.hs.application;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.inpatientdept.MedicalRecordAppService;
import com.neusoft.hs.application.treatment.TreatmentAppService;
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
import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentDomainService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class MedicalRecordTestService {

	@Autowired
	private InspectDomainService inspectDomainService;

	@Autowired
	private MedicalRecordAppService medicalRecordAppService;

	@Autowired
	protected VisitDomainService visitDomainService;

	@Autowired
	protected TreatmentDomainService treatmentDomainService;

	@Autowired
	protected TreatmentAppService treatmentAppService;

	public MedicalRecord createIntoWardRecord(Visit visit,
			MedicalRecordType medicalRecordType, Doctor doctor)
			throws HsException {

		// 创建入院记录
		MedicalRecordBuilder builder = new MedicalRecordTypeBuilder(
				medicalRecordType, visit);

		MedicalRecord intoWardRecord = medicalRecordAppService.create(builder,
				visit, medicalRecordType, doctor);

		Map<String, Itemable> datas = intoWardRecord.getDatas();

		assertTrue(datas.get("患者姓名").getValues().get(0).toString()
				.equals("测试患者001"));
		assertTrue(datas.get("主诉").getValues().get(0).toString()
				.equals("患者咳嗽发烧三天"));

		((SimpleTreatmentItemValue) datas.get("主诉").getValues().get(0))
				.setInfo("患者咳嗽发烧三天，体温38.5");

		medicalRecordAppService.create(intoWardRecord);

		return intoWardRecord;

	}

	public MedicalRecord createTemporaryOrderListMedicalRecord(Visit visit,
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

		return temporaryOrderListRecord;

	}

	public List<MedicalRecord> createInspectResultMedicalRecord(Visit visit,
			MedicalRecordType medicalRecordType, Doctor doctor)
			throws HsException {

		MedicalRecordBuilder builder;
		// 生成检查单病历
		List<InspectResult> results = inspectDomainService
				.findInspectResults(visit);
		assertTrue(results.size() == 1);

		List<MedicalRecord> records = new ArrayList<MedicalRecord>();

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

			itemValue = datas.get("检查时间").getValues();
			assertTrue(itemValue.get(0).toString().equals("2017-01-02 14:40"));

			itemValue = datas.get("检查科室").getValues();
			assertTrue(itemValue.get(0).toString().equals("CT室"));

			medicalRecordAppService.create(inspectResultRecord);

			records.add(inspectResultRecord);
		}

		return records;

	}

}
