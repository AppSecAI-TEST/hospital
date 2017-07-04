package com.neusoft.hs.domain.treatment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("Common")
public class CommonTreatmentItemSpec extends TreatmentItemSpec {

	@Override
	public TreatmentItem getTheItem(Visit visit) throws TreatmentException {
		return this.getService(TreatmentItemRepo.class)
				.findByVisitAndTreatmentItemSpec(visit, this);
	}

	@Override
	public TreatmentItem createTreatmentItem(Visit visit)
			throws TreatmentException {
		throw new TreatmentException("该类型诊疗信息不能自动生成");
	}

}
