//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordClipRepo.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

interface MedicalRecordTypeRepo extends
		PagingAndSortingRepository<MedicalRecordType, String> {
	
	public List<MedicalRecordType> findByNeedCreate(boolean needCreate);

}
