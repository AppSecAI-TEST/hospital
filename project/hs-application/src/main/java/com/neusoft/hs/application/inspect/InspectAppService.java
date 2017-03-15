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
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.AbstractUser;

@Service
@Transactional(rollbackFor = Exception.class)
public class InspectAppService {

	@Autowired
	private ApplyDomainService applyDomainService;

	@Autowired
	private InspectDomainService inspectDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	public InspectApply find(String applyId) {
		return (InspectApply) applyDomainService.find(applyId);
	}

	public void arrange(String executeId, Date planExecuteDate,
			AbstractUser user) throws InspectException, OrderExecuteException {
		inspectDomainService.arrange(executeId, planExecuteDate, user);
		orderExecuteDomainService.finish(executeId, null, user);
	}

	public void confirm(String executeId,
			Map<InspectApplyItem, String> results, AbstractUser user)
			throws InspectException, OrderExecuteException {
		inspectDomainService.confirm(executeId, results, user);
		orderExecuteDomainService.finish(executeId, null, user);
	}

	public void cancel(String inspectApplyItemId, AbstractUser user)
			throws InspectException {
		inspectDomainService.cancel(inspectApplyItemId, user);
	}
}
