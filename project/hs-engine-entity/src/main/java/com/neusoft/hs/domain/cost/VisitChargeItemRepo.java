//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\VisitChargeItemRepo.java

package com.neusoft.hs.domain.cost;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

interface VisitChargeItemRepo extends
		PagingAndSortingRepository<VisitChargeItem, String> {
	
	public List<VisitChargeItem> findByState(String state);

}
