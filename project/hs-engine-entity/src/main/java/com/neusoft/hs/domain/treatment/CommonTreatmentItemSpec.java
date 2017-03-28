package com.neusoft.hs.domain.treatment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("Common")
public class CommonTreatmentItemSpec extends TreatmentItemSpec {
	
	@Override
	public TreatmentItem getTheItem(Visit visit) {
		return this.getService(TreatmentItemRepo.class)
				.findByVisitAndTreatmentItemSpec(visit, this);
	}

}
