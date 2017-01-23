package com.neusoft.hs.application.inspect;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.InspectApply;
import com.neusoft.hs.domain.order.InspectApplyItem;
import com.neusoft.hs.domain.order.InspectDomainService;
import com.neusoft.hs.domain.order.InspectException;
import com.neusoft.hs.domain.organization.AbstractUser;

@Service
@Transactional(rollbackFor = Exception.class)
public class InspectAppService {

	@Autowired
	private InspectDomainService inspectDomainService;

	public InspectApply find(String applyId) {
		return inspectDomainService.find(applyId);
	}

	public void arrange(String executeId, Date planExecuteDate)
			throws InspectException {
		inspectDomainService.arrange(executeId, planExecuteDate);
	}

	public void confirm(String executeId,
			Map<InspectApplyItem, String> results, AbstractUser user)
			throws InspectException {
		inspectDomainService.confirm(executeId, results, user);
	}

}
