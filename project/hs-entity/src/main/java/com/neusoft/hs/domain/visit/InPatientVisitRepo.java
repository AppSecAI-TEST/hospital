//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitRepo.java

package com.neusoft.hs.domain.visit;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.organization.Dept;

interface InPatientVisitRepo extends PagingAndSortingRepository<InPatientVisit, String> {

	List<InPatientVisit> findByState(String state, Pageable pageable);

	List<InPatientVisit> findByStateAndRespDept(String state, Dept dept,
			Pageable pageable);

	List<InPatientVisit> findByRespDept(Dept dept, Pageable pageable);
}
