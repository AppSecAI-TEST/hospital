//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\orderexecute\\OrderExecuteAppService.java

package com.neusoft.hs.domain.orderexecute;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Staff;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderExecuteAppService {

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	@Autowired
	private CostDomainService costDomainService;

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
	public int start() throws OrderExecuteException {
		return orderExecuteDomainService.start();
	}

	public List<OrderExecute> getNeedExecuteOrderExecutes(AbstractUser user,
			Pageable pageable) {
		return orderExecuteDomainService.getNeedExecuteOrderExecutes(user,
				pageable);
	}

	/**
	 * @param user
	 * @param executeId
	 * @roseuid 584FB68C010C
	 */
	public void finish(String executeId, AbstractUser user)
			throws OrderExecuteException {
		orderExecuteDomainService.finish(executeId, user);
	}

	public List<OrderExecute> getNeedBackChargeOrderExecutes(Staff user,
			Pageable pageable) {
		return orderExecuteDomainService.getNeedBackChargeOrderExecutes(user,
				pageable);
	}

	/**
	 * @param user
	 * @param executeId
	 * @throws OrderExecuteException
	 * @roseuid 5850BC9B0098
	 */
	public void unCharging(String executeId, boolean isBackCost, Staff user)
			throws OrderExecuteException {

		OrderExecute execute = orderExecuteDomainService.find(executeId);
		if (execute == null) {
			throw new OrderExecuteException(null, "executeId=[" + executeId
					+ "]不存在");
		}
		costDomainService.unCharging(execute, isBackCost, user);
		
		orderExecuteDomainService.unCharging(execute, isBackCost, user);
		
	}

}
