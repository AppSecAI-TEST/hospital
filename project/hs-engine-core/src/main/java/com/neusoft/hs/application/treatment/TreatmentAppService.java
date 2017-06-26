package com.neusoft.hs.application.treatment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.treatment.TreatmentDomainService;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.treatment.TreatmentItemValue;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class TreatmentAppService {

	public final static int shouldHour = 24;

	@Autowired
	private TreatmentDomainService treatmentDomainService;

	public void create(Visit visit, TreatmentItemSpec treatmentItemSpec,
			TreatmentItemValue value, AbstractUser user) {

		List<TreatmentItemValue> values = new ArrayList<TreatmentItemValue>();
		values.add(value);

		create(visit, treatmentItemSpec, values, user);
	}

	public void create(Visit visit, TreatmentItemSpec treatmentItemSpec,
			List<TreatmentItemValue> values, AbstractUser user) {

		TreatmentItem item = new TreatmentItem();
		item.setVisit(visit);
		item.setTreatmentItemSpec(treatmentItemSpec);
		item.setCreator(user);

		values.forEach(value -> {
			item.addValue(value);
		});

		treatmentDomainService.create(item);
	}

	public List<TreatmentItemSpec> getShouldTreatmentItemSpecs(Visit visit,
			AbstractUser user) throws TreatmentException {
		Date shouldDate = DateUtil.addHour(DateUtil.getSysDate(), shouldHour);
		return treatmentDomainService.getShouldTreatmentItemSpecs(visit,
				shouldDate, user);
	}

}
