//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderRepo.java

package com.neusoft.hs.domain.inspect;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.visit.Visit;

interface InspectResultRepo extends
		PagingAndSortingRepository<InspectResult, String> {
	
	public List<InspectResult> findByVisit(Visit visit);

}
