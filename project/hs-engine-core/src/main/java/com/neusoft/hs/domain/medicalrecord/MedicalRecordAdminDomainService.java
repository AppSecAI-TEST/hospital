package com.neusoft.hs.domain.medicalrecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class MedicalRecordAdminDomainService {

	@Autowired
	private MedicalRecordClipRepo medicalRecordClipRepo;

	@Autowired
	private MedicalRecordTypeRepo medicalRecordTypeRepo;

	@Autowired
	private MedicalRecordRepo medicalRecordRepo;

	@Autowired
	private MedicalRecordTypeBuilderRepo medicalRecordTypeBuilderRepo;

	public void createMedicalRecordType(MedicalRecordType type) {
		medicalRecordTypeRepo.save(type);
	}

	public void createMedicalRecordTypes(
			List<MedicalRecordType> medicalRecordTypes) {
		medicalRecordTypeRepo.save(medicalRecordTypes);
	}

	public MedicalRecordType getMedicalRecordType(String id) {
		return medicalRecordTypeRepo.findOne(id);
	}

	/**
	 * @roseuid 584E167A0000
	 */
	public void clear() {
		medicalRecordTypeBuilderRepo.deleteAll();
		medicalRecordClipRepo.deleteAll();
		medicalRecordTypeRepo.deleteAll();
	}

}
