package com.neusoft.hs.domain.treatment.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.treatment.TreatmentItemValue;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("VisitName")
public class VisitNameTreatmentItemSpec extends TreatmentItemSpec {

	@Override
	public List<TreatmentItemValue> getValue(Visit visit) {
		
		List<TreatmentItemValue> values = new ArrayList<TreatmentItemValue>();
		
		SimpleTreatmentItemValue value = new SimpleTreatmentItemValue();
		value.setInfo(visit.getName());
		value.setVisit(visit);
		value.setTreatmentItemSpec(this);
		
		values.add(value);
		
		return values;
	}

}
