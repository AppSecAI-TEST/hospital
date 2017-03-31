package com.neusoft.hs.domain.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PatientAdminDomainService {
	
	@Autowired
	private PatientRepo patientRepo;

	public void clear() {
		patientRepo.deleteAll();
	}
}
