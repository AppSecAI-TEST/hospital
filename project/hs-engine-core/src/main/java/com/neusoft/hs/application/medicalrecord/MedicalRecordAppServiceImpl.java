package com.neusoft.hs.application.medicalrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordBuilder;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;

@Service
@Transactional(rollbackFor = Exception.class)
public class MedicalRecordAppServiceImpl implements MedicalRecordAppService{

	@Autowired
	private MedicalRecordDomainService medicalRecordDomainService;

	public MedicalRecord create(MedicalRecordBuilder builder, Visit visit,
			MedicalRecordType type, Doctor doctor) {
		return medicalRecordDomainService.create(builder, visit, type, doctor);
	}

	public void create(MedicalRecord record) {
		medicalRecordDomainService.create(record);
	}

	public MedicalRecord find(String id) {
		return medicalRecordDomainService.find(id);
	}

	public void sign(String id, Doctor doctor) throws MedicalRecordException {
		MedicalRecord record = medicalRecordDomainService.find(id);
		if (record == null) {
			throw new MedicalRecordException(null, "id=[" + id + "]病历不存在");
		}
		medicalRecordDomainService.sign(record, doctor);
	}

	public void fix(String id, AbstractUser user) throws MedicalRecordException {
		MedicalRecord record = medicalRecordDomainService.find(id);
		if (record == null) {
			throw new MedicalRecordException(null, "id=[" + id + "]病历不存在");
		}
		medicalRecordDomainService.fix(record, user);
	}

	public void transfer(Visit visit, Dept dept, AbstractUser user)
			throws MedicalRecordException {
		medicalRecordDomainService.transfer(visit, dept, user);
	}
}
