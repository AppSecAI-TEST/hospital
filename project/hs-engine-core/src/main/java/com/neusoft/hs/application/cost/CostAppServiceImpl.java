package com.neusoft.hs.application.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.Nurse;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostAppServiceImpl implements CostAppService {

	@Autowired
	private CostDomainService costDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	/**
	 * 取消医嘱执行条目对应的费用条目
	 * 
	 * @param user
	 * @param executeId
	 * @throws OrderExecuteException
	 * @roseuid 5850BC9B0098
	 */
	public void unCharging(String executeId, boolean isBackCost, Nurse nurse)
			throws OrderExecuteException {

		OrderExecute execute = orderExecuteDomainService.find(executeId);
		if (execute == null) {
			throw new OrderExecuteException(null, "executeId=[" + executeId
					+ "]不存在");
		}
		costDomainService.unCharging(execute, isBackCost, nurse);
	}

}
