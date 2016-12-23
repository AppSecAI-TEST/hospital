//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\orderexecute\\OrderExecuteAppService.java

package com.neusoft.hs.domain.orderexecute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.cost.CostDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderExecuteAppService {

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	/**
	 * @param user003
	 * @param string
	 * @throws OrderExecuteException
	 * @roseuid 584F6109005C
	 */
	public void send(String executeId, Nurse nurse)
			throws OrderExecuteException {
		orderExecuteDomainService.send(executeId, nurse);
	}

	/**
	 * @roseuid 584F67A6034B
	 */
	public void start() {

	}

	/**
	 * @roseuid 584FB68C010C
	 */
	public void finish() {

	}

	/**
	 * @roseuid 5850BC9B0098
	 */
	public void unCharging() {

	}
}
