//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\ChargeBillRepo.java

package com.neusoft.hs.domain.cost;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.visit.Visit;

interface ChargeBillRepo extends PagingAndSortingRepository<ChargeBill, String> {

	ChargeBill findByVisit(Visit visit);

}
