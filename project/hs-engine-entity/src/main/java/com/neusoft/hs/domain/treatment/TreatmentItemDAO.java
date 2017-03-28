package com.neusoft.hs.domain.treatment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.visit.Visit;

@Service
public class TreatmentItemDAO {

	@Autowired
	private TreatmentItemRepo treatmentItemRepo;

	public TreatmentItem findTheTreatmentItem(Visit visit,
			TreatmentItemSpec treatmentItemSpec) {
		return treatmentItemRepo.findByVisitAndTreatmentItemSpec(visit,
				treatmentItemSpec);
	}

}
