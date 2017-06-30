//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordClipRepo.java

package com.neusoft.hs.domain.medicalrecord;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

interface MedicalRecordRenderRepo extends
		PagingAndSortingRepository<MedicalRecordRender, String> {

	@Query(value = "delete from domain_medical_record_render", nativeQuery = true)
	@Modifying
	void clear();

}
