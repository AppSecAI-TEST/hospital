//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordClipRepo.java

package com.neusoft.hs.domain.medicalrecord;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.visit.Visit;

interface MedicalRecordClipRepo extends
		PagingAndSortingRepository<MedicalRecordClip, String> {
	
	public MedicalRecordClip findByVisit(Visit visit);

}
