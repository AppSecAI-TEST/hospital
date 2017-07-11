//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\ChargeRecordRepo.java

package com.neusoft.hs.domain.cost;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.visit.Visit;

interface ChargeRecordRepo extends
		PagingAndSortingRepository<ChargeRecord, String> {

	List<ChargeRecord> findByVisit(Visit visit, Pageable pageable);

	List<ChargeRecord> findByVisitAndBelongDeptIn(Visit visit,
			List<Dept> depts, Pageable pageable);

}
