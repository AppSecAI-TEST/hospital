package com.neusoft.hs.application.inpatientdept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordBuilder;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordTypeBuilder;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;

@Service
@Transactional(rollbackFor = Exception.class)
public class MedicalRecordAppService {

	@Autowired
	private MedicalRecordDomainService medicalRecordDomainService;

	public MedicalRecord create(Visit visit,
			MedicalRecordType type, Doctor doctor) {
		MedicalRecordBuilder builder = new MedicalRecordTypeBuilder(type, visit);
		return medicalRecordDomainService.create(builder, visit, type, doctor);
	}

	public void create(MedicalRecord record) {
		medicalRecordDomainService.create(record);
	}

	public MedicalRecord find(String id) {
		return medicalRecordDomainService.find(id);
	}

	public void sign(String id, Doctor doctor) throws MedicalRecordException {
		medicalRecordDomainService.sign(id, doctor);
	}
}
