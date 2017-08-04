package com.neusoft.hs.domain.visit;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.Hibernate;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemDAO;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.treatment.TreatmentItemValue;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("VisitName")
public class VisitNameTreatmentItemSpec extends TreatmentItemSpec {

	@Override
	public TreatmentItem getTheItem(Visit visit, AbstractUser user)
			throws TreatmentException {

		TreatmentItem treatmentItem = this.getService(TreatmentItemDAO.class)
				.findTheTreatmentItem(visit, this);
		if (treatmentItem == null) {
			treatmentItem = createTreatmentItem(visit, user);
			this.getService(TreatmentItemDAO.class).save(treatmentItem);
		}

		return treatmentItem;
	}

	public TreatmentItem createTreatmentItem(Visit visit, AbstractUser user)
			throws TreatmentException {

		TreatmentItem treatmentItem = new TreatmentItem();
		treatmentItem.setTreatmentItemSpec(this);
		treatmentItem.setVisit(visit);
		treatmentItem.setCreator(user);
		treatmentItem.setCreateDate(DateUtil.getSysDate());

		List<TreatmentItemValue> values = new ArrayList<TreatmentItemValue>();

		SimpleTreatmentItemValue value = new SimpleTreatmentItemValue();
		value.setInfo(visit.getName());

		treatmentItem.addValue(value);

		values.add(value);

		return treatmentItem;
	}

}
