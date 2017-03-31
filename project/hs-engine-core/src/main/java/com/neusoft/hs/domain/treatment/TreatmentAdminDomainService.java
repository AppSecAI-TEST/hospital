package com.neusoft.hs.domain.treatment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TreatmentAdminDomainService {
	
	@Autowired
	private TreatmentItemRepo treatmentItemRepo;
	
	@Autowired
	private TreatmentItemSpecRepo treatmentItemSpecRepo;
	
	public void createTreatmentItemSpecs(
			List<TreatmentItemSpec> treatmentItemSpecs) {
		treatmentItemSpecRepo.save(treatmentItemSpecs);
	}

	public void clearTreatmentItems() {
		treatmentItemRepo.deleteAll();
	}

	public void clearTreatmentItemSpecs() {
		treatmentItemSpecRepo.deleteAll();
	}

}
