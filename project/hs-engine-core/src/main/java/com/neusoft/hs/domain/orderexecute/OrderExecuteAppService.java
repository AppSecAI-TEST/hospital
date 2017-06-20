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
import com.neusoft.hs.domain.order.OrderExecuteFilter;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Admin;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderExecuteAppService {

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;
	
	public static final int NeedSendOrderExecuteDay = 1;// 今天

	public static final int NeedSendOrderExecuteHour = 36;// 明天12：00之前的医嘱

	public static final int NeedExecuteOrderMinute = 30;// 医嘱执行可提前分钟数
	
	public List<OrderExecute> getNeedSendOrderExecutes(AbstractUser nurse,
			Pageable pageable) {
		Date date = DateUtil.addHour(DateUtil.getSysDateStart(),
				NeedSendOrderExecuteHour);
		return orderExecuteDomainService.getNeedSendOrderExecutes(nurse, date,
				pageable);
	}

	/**
	 * @param user003
	 * @param string
	 * @throws OrderExecuteException
	 * @roseuid 584F6109005C
	 */
	public void send(String executeId, AbstractUser nurse)
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
	public int start(Admin admin) throws OrderExecuteException {
		return orderExecuteDomainService.start(admin);
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

	public List<OrderExecute> find(OrderExecuteFilter filter,
			Map<String, Object> params, AbstractUser user, Pageable pageable) {
		return orderExecuteDomainService.find(filter, params, user, pageable);
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

}
