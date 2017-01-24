package com.neusoft.hs.application.inspect;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.inspect.InspectApply;
import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.inspect.InspectDomainService;
import com.neusoft.hs.domain.inspect.InspectException;
import com.neusoft.hs.domain.order.ApplyDomainService;
import com.neusoft.hs.domain.organization.AbstractUser;

@Service
@Transactional(rollbackFor = Exception.class)
public class InspectAppService {

	@Autowired
	private ApplyDomainService applyDomainService;

	@Autowired
	private InspectDomainService inspectDomainService;

	public InspectApply find(String applyId) {
		return (InspectApply) applyDomainService.find(applyId);
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

	public void cancel(String inspectApplyItemId, AbstractUser user)
			throws InspectException {
		inspectDomainService.cancel(inspectApplyItemId, user);
	}
}
