package com.neusoft.hs.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class InspectDomainService {

	@Autowired
	private InspectApplyRepo inspectApplyRepo;

	public InspectApply find(String applyId) {
		return inspectApplyRepo.findOne(applyId);
	}

	public void save(InspectApply inspectApply) {
		inspectApplyRepo.save(inspectApply);
	}
}
