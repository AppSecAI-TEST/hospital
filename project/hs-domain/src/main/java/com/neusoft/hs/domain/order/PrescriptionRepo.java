//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\VisitChargeItemRepo.java

package com.neusoft.hs.domain.order;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.visit.Visit;

interface PrescriptionRepo extends
		PagingAndSortingRepository<Prescription, String> {

	List<Prescription> findByVisit(Visit visit);

	Prescription findByOrdersIn(Order order);

}
