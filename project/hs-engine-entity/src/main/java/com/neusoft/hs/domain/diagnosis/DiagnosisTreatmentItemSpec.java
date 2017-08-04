//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\entity\\diagnosis\\DiagnosisTreatmentItemSpec.java

package com.neusoft.hs.domain.diagnosis;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemDAO;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("Diagnosis")
public class DiagnosisTreatmentItemSpec extends TreatmentItemSpec {

	@Override
	public TreatmentItem getTheItem(Visit visit, AbstractUser user)
			throws TreatmentException {
		return this.getService(TreatmentItemDAO.class).findTheTreatmentItem(
				visit, this);
	}

	@Override
	public TreatmentItem createTreatmentItem(Visit visit, AbstractUser user)
			throws TreatmentException {
		throw new TreatmentException("该类型诊疗信息不能自动生成");
	}
}
