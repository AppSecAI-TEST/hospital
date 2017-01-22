package com.neusoft.hs.application.inspect;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.Apply;
import com.neusoft.hs.domain.order.InspectApply;
import com.neusoft.hs.domain.order.InspectDomainService;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class InspectAppService {

	@Autowired
	private InspectDomainService inspectDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	public InspectApply find(String applyId) {
		return inspectDomainService.find(applyId);
	}

	public void save(InspectApply inspectApply) {
		inspectDomainService.save(inspectApply);
	}

	public void arrange(String executeId, Date planExecuteDate)
			throws InspectException {
		OrderExecute orderExecute = orderExecuteDomainService.find(executeId);
		if (orderExecute == null) {
			throw new InspectException("医嘱执行条目[" + executeId + "]不存在");
		}
		Order order = orderExecute.getOrder();
		Apply apply = order.getApply();
		if (apply == null) {
			throw new InspectException("医嘱条目[" + order + "]不存在申请单");
		}
		apply.setPlanExecuteDate(planExecuteDate);

		this.save((InspectApply) apply);
	}

}
