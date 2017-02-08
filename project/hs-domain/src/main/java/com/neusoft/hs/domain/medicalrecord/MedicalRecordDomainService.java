//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordDomainService.java

package com.neusoft.hs.domain.medicalrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordDomainService {

	@Autowired
	private MedicalRecordClipRepo medicalRecordClipRepo;

	/**
	 * @roseuid 584E167A0000
	 */
	public void clear() {
		medicalRecordClipRepo.deleteAll();
	}
}
