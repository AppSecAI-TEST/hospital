//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\orderexecute\\OrderExecuteAppService.java

package com.neusoft.hs.domain.orderexecute;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.visit.Visit;

public interface OrderExecuteAppService {

	/**
	 * @param user003
	 * @param string
	 * @throws OrderExecuteException
	 * @roseuid 584F6109005C
	 */
	public void send(String executeId, Nurse nurse)
			throws OrderExecuteException;

	/**
	 * @roseuid 584F67A6034B
	 */
	public int start() throws OrderExecuteException;

	public List<OrderExecute> getNeedExecuteOrderExecutes(AbstractUser user,
			Pageable pageable);

	public List<OrderExecute> getNeedExecuteOrderExecutes(Visit visit,
			String type, AbstractUser user, Pageable pageable);

	/**
	 * @param user
	 * @param executeId
	 * @roseuid 584FB68C010C
	 */
	public void finish(String executeId, Map<String, Object> params,
			AbstractUser user) throws OrderExecuteException;
}
