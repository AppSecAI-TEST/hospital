package com.neusoft.hs.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class InspectDomainService {

	@Autowired
	private ApplyRepo applyRepo;

	public InspectApply find(String applyId) {
		return (InspectApply) applyRepo.findOne(applyId);
	}

	public void save(InspectApply inspectApply) {
		applyRepo.save(inspectApply);
	}
}
