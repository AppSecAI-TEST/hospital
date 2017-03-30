package com.neusoft.hs.application.treatment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
@Service
public class TreatmentAppServiceStub implements TreatmentAppService {

	@Override
	public List<TreatmentItemSpec> getShouldTreatmentItemSpecs(Visit visit,
			AbstractUser user) throws TreatmentException {
		// TODO Auto-generated method stub
		return null;
	}

}
