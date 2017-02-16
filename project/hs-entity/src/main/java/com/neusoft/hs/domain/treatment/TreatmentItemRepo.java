//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitRepo.java

package com.neusoft.hs.domain.treatment;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.visit.Visit;

interface TreatmentItemRepo extends
		PagingAndSortingRepository<TreatmentItem, String> {

	public TreatmentItem findByVisitAndTreatmentItemSpec(Visit visit,
			TreatmentItemSpec treatmentItemSpec);
}
