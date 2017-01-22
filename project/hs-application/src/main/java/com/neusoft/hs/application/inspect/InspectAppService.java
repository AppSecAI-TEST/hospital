package com.neusoft.hs.application.inspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.InspectApply;
import com.neusoft.hs.domain.order.InspectDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class InspectAppService {

	@Autowired
	private InspectDomainService inspectDomainService;

	public InspectApply find(String applyId) {
		return inspectDomainService.find(applyId);
	}

	public void save(InspectApply inspectApply) {
		inspectDomainService.save(inspectApply);
	}

}
