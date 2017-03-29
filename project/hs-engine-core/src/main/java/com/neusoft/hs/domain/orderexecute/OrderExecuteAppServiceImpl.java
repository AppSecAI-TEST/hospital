//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\orderexecute\\OrderExecuteAppService.java

package com.neusoft.hs.domain.orderexecute;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderExecuteAppServiceImpl implements OrderExecuteAppService{

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	public static int NeedExecuteOrderMinute = 30;// 医嘱执行可提前分钟数

	/**
	 * @param user003
	 * @param string
	 * @throws OrderExecuteException
	 * @roseuid 584F6109005C
	 */
	public void send(String executeId, Nurse nurse)
			throws OrderExecuteException {
		OrderExecute execute = orderExecuteDomainService.find(executeId);
		if (execute == null) {
			throw new OrderExecuteException(null, "executeId=[" + executeId
					+ "]不存在");
		}
		orderExecuteDomainService.send(execute, nurse);
	}

	/**
	 * @roseuid 584F67A6034B
	 */
	public int start() throws OrderExecuteException {
		return orderExecuteDomainService.start();
	}

	public List<OrderExecute> getNeedExecuteOrderExecutes(AbstractUser user,
			Pageable pageable) {
		Date planStartDate = DateUtil.addMinute(DateUtil.getSysDate(),
				NeedExecuteOrderMinute);
		return orderExecuteDomainService.getNeedExecuteOrderExecutes(user,
				planStartDate, pageable);
	}

	public List<OrderExecute> getNeedExecuteOrderExecutes(Visit visit,
			String type, AbstractUser user, Pageable pageable) {
		Date planStartDate = DateUtil.addMinute(DateUtil.getSysDate(),
				NeedExecuteOrderMinute);
		return orderExecuteDomainService.getNeedExecuteOrderExecutes(visit,
				type, user, planStartDate, pageable);
	}

	/**
	 * @param user
	 * @param executeId
	 * @roseuid 584FB68C010C
	 */
	public void finish(String executeId, Map<String, Object> params,
			AbstractUser user) throws OrderExecuteException {
		OrderExecute execute = orderExecuteDomainService.find(executeId);
		if (execute == null) {
			throw new OrderExecuteException(null, "executeId=[" + executeId
					+ "]不存在");
		}
		orderExecuteDomainService.finish(execute, params, user);
	}
}
