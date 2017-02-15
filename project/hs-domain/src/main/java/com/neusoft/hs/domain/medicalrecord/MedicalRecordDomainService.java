//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordDomainService.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;

@Service
@Transactional(rollbackFor = Exception.class)
public class MedicalRecordDomainService {

	@Autowired
	private MedicalRecordClipRepo medicalRecordClipRepo;

	@Autowired
	private MedicalRecordTypeRepo medicalRecordTypeRepo;

	public MedicalRecord create(Visit visit, MedicalRecordType type,
			Doctor doctor) {
		MedicalRecord record = new MedicalRecord(type, visit, doctor);

		return record;

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
