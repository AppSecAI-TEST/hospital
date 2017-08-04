package com.neusoft.hs.domain.treatment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("Common")
public class CommonTreatmentItemSpec extends TreatmentItemSpec {

	@Override
	public TreatmentItem getTheItem(Visit visit, AbstractUser user) throws TreatmentException {
		return this.getService(TreatmentItemDAO.class).findTheTreatmentItem(
				visit, this);
	}

	@Override
	public TreatmentItem createTreatmentItem(Visit visit, AbstractUser user)
			throws TreatmentException {
		throw new TreatmentException("该类型诊疗信息不能自动生成");
	}

}
