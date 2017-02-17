//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordDomainService.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemValue;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class MedicalRecordDomainService {

	@Autowired
	private MedicalRecordClipRepo medicalRecordClipRepo;

	@Autowired
	private MedicalRecordTypeRepo medicalRecordTypeRepo;

	@Autowired
	private MedicalRecordRepo medicalRecordRepo;

	@Autowired
	private ApplicationContext applicationContext;

	public MedicalRecord create(Visit visit, MedicalRecordType type,
			Doctor doctor) {
		MedicalRecord record = new MedicalRecord(type, visit, doctor);

		return record;

	}

	public void create(MedicalRecord record) {
		if (record.getCreateDate() == null) {
			record.setCreateDate(DateUtil.getSysDate());
		}
		if (record.getClip() == null) {
			record.setClip(medicalRecordClipRepo.findByVisit(record.getVisit()));
		}
		record.setState(MedicalRecord.State_Created);

		// 填充病历项目信息
		for (TreatmentItem item : record.getDatas().values()) {
			for (TreatmentItemValue value : item.getValues()) {
				if (value.getItem().getCreateDate() == null) {
					value.getItem().setCreateDate(DateUtil.getSysDate());
				}
				if (value.getItem().getCreator() == null) {
					value.getItem().setCreator(record.getDoctor());
				}
			}
		}

		record.save();

		MedicalRecordLog recordLog = new MedicalRecordLog();
		recordLog.setRecord(record);
		recordLog.setType(MedicalRecordLog.Type_Create);
		recordLog.setOperator(record.getDoctor());
		recordLog.setCreateDate(DateUtil.getSysDate());

		recordLog.save();

		applicationContext.publishEvent(new MedicalRecordCreatedEvent(record));
	}

	public MedicalRecord find(String id) {
		MedicalRecord record = medicalRecordRepo.findOne(id);
		if (record != null) {
			record.init();
		}

		return record;
	}

	public void sign(String id, Doctor doctor) throws MedicalRecordException {
		MedicalRecord record = medicalRecordRepo.findOne(id);
		if (record == null) {
			throw new MedicalRecordException(null, "id=[" + id + "]病历不存在");
		}
		record.sign(doctor);

		MedicalRecordLog recordLog = new MedicalRecordLog();
		recordLog.setRecord(record);
		recordLog.setType(MedicalRecordLog.Type_Sign);
		recordLog.setOperator(doctor);
		recordLog.setCreateDate(DateUtil.getSysDate());

		recordLog.save();

		applicationContext.publishEvent(new MedicalRecordCreatedEvent(record));

	}

	public void createMedicalRecordType(MedicalRecordType type) {
		medicalRecordTypeRepo.save(type);
	}

	public void createMedicalRecordTypes(
			List<MedicalRecordType> medicalRecordTypes) {
		medicalRecordTypeRepo.save(medicalRecordTypes);
	}

	/**
	 * @roseuid 584E167A0000
	 */
	public void clear() {
		medicalRecordClipRepo.deleteAll();
		medicalRecordTypeRepo.deleteAll();
	}

}
