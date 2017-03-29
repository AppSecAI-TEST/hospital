package com.neusoft.hs.application.cost;

import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.Nurse;


public interface CostAppService {

	/**
	 * 取消医嘱执行条目对应的费用条目
	 * 
	 * @param user
	 * @param executeId
	 * @throws OrderExecuteException
	 * @roseuid 5850BC9B0098
	 */
	public void unCharging(String executeId, boolean isBackCost, Nurse nurse)
			throws OrderExecuteException;

}
