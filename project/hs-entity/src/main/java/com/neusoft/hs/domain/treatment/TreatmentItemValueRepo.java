//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\TreatmentItemValue.java

package com.neusoft.hs.domain.treatment;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.visit.Visit;

interface TreatmentItemValueRepo extends PagingAndSortingRepository<TreatmentItemValue, String> {

	public List<TreatmentItemValue> findByVisitAndTreatmentItemSpec(Visit visit, TreatmentItemSpec treatmentItemSpec);
}

