//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordClipRepo.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.visit.Visit;

interface MedicalRecordRepo extends
		PagingAndSortingRepository<MedicalRecord, String> {

	List<MedicalRecord> findByVisit(Visit visit, Pageable pageable);

}
