//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitDomainService.java

package com.neusoft.hs.domain.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PatientDomainService {
	@Autowired
	private PatientRepo patientRepo;

	public Patient findByCardNumber(String cardNumber) {
		return patientRepo.findByCardNumber(cardNumber);
	}

	public void delete(String cardNumber) {
		Patient patient = patientRepo.findByCardNumber(cardNumber);
		if (patient != null) {
			patient.delete();
		}
	}

}
