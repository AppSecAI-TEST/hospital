//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordClipRepo.java

package com.neusoft.hs.domain.medicalrecord;

import org.springframework.data.repository.PagingAndSortingRepository;

interface MedicalRecordItemRepo extends
		PagingAndSortingRepository<MedicalRecordItem, String> {

	MedicalRecordItem findByRecordAndName(MedicalRecord medicalRecord,
			String name);

}
