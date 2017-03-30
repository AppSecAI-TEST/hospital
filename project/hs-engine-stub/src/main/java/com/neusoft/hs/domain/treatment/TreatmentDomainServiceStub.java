package com.neusoft.hs.domain.treatment;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
@Service
public class TreatmentDomainServiceStub implements TreatmentDomainService {

	@Override
	public void create(TreatmentItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<TreatmentItemSpec> getShouldTreatmentItemSpecs(Visit visit,
			Date shouldDate, AbstractUser user) throws TreatmentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreatmentItem getTheTreatmentItem(Visit visit,
			TreatmentItemSpec treatmentItemSpec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(TreatmentItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterable<TreatmentItemSpec> getAllTreatmentItemSpecs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createTreatmentItemSpecs(
			List<TreatmentItemSpec> treatmentItemSpecs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearTreatmentItems() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearTreatmentItemSpecs() {
		// TODO Auto-generated method stub

	}

}
