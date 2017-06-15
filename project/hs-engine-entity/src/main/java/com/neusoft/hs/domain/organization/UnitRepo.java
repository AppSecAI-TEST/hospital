//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitRepo.java

package com.neusoft.hs.domain.organization;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

interface UnitRepo extends PagingAndSortingRepository<Unit, String> {

	@Query("select d from InPatientDept d")
	List<InPatientDept> findInPatientDept(Pageable pageable);

	@Query("select d from InPatientAreaDept d")
	List<InPatientAreaDept> findInPatientArea(Pageable pageable);

}
