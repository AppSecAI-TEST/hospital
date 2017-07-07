//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordClipRepo.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.visit.Visit;

interface MedicalRecordClipRepo extends
		PagingAndSortingRepository<MedicalRecordClip, String> {

	public MedicalRecordClip findByVisit(Visit visit);

	public List<MedicalRecordClip> findByStateAndCheckDept(String state,
			Dept checkDept, Pageable pageable);

	public List<MedicalRecordClip> findByState(String state, Pageable pageable);

}
