package com.neusoft.hs.domain.visit;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.treatment.TreatmentItemValue;

@Entity
@DiscriminatorValue("VisitName")
public class VisitNameTreatmentItemSpec extends TreatmentItemSpec {

	@Override
	public TreatmentItem getTheItem(Visit visit) throws TreatmentException {

		TreatmentItem item = new TreatmentItem();
		item.setTreatmentItemSpec(this);
		item.setVisit(visit);

		List<TreatmentItemValue> values = new ArrayList<TreatmentItemValue>();

		SimpleTreatmentItemValue value = new SimpleTreatmentItemValue();
		value.setInfo(visit.getName());

		item.addValue(value);

		values.add(value);

		return item;
	}

}
