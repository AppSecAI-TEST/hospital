//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderExecuteDomainService.java

package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderExecuteDomainService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private OrderExecuteRepo orderExecuteRepo;

	public List<OrderExecute> getNeedSendOrderExecutes(Nurse nurse,
			Date planStartDate, Pageable pageable) {
		return orderExecuteRepo
				.findByStateAndBelongDeptAndPlanStartDateLessThan(
						OrderExecute.State_NeedSend, nurse.getDept(),
						planStartDate, pageable);
	}

	public List<OrderExecute> getNeedExecuteOrderExecutes(AbstractUser user,
			Date planStartDate, Pageable pageable) {
		return orderExecuteRepo
				.findByStateAndExecuteDeptAndPlanStartDateLessThan(
						OrderExecute.State_Executing, user.getDept(),
						planStartDate, pageable);
	}

	public List<OrderExecute> getNeedBackChargeOrderExecutes(Staff user,
			Pageable pageable) {
		return orderExecuteRepo.findByChargeState(
				OrderExecute.ChargeState_NeedBackCharge, pageable);
	}

	/**
	 * 发送医嘱执行条目
	 * 
	 * @param nurse
	 * @param executeId
	 * @throws OrderExecuteException
	 * @roseuid 584F6150022C
	 */
	public void send(String executeId, Nurse nurse)
			throws OrderExecuteException {
		OrderExecute execute = orderExecuteRepo.findOne(executeId);
		if (execute == null) {
			throw new OrderExecuteException(null, "executeId=[" + executeId
					+ "]不存在");
		}
		execute.send();

		applicationContext.publishEvent(new OrderExecuteSendedEvent(execute));

	}

	/**
	 * 启动符合条件的医嘱执行条目
	 * 
	 * @roseuid 584F691702B2
	 */
	public int start() throws OrderExecuteException {
		Date sysDate = DateUtil.getSysDate();
		Date startDate = DateUtil.addDay(DateUtil.getSysDateStart(), 1);
		return orderExecuteRepo.start(OrderExecute.State_Executing,
				OrderExecute.State_NeedExecute, ChargeBill.State_Normal,
				sysDate, startDate);
	}

	/**
	 * 完成医嘱执行条目
	 * 
	 * @param user
	 * @param executeId
	 * @param params
	 * @throws OrderExecuteException
	 * @roseuid 584FB6AF013C
	 */
	public void finish(String executeId, Map<String, Object> params,
			AbstractUser user) throws OrderExecuteException {
		OrderExecute execute = orderExecuteRepo.findOne(executeId);
		if (execute == null) {
			throw new OrderExecuteException(null, "executeId=[" + executeId
					+ "]不存在");
		}
		execute.finish(params, user);

		applicationContext.publishEvent(new OrderExecuteFinishedEvent(execute));
	}

	public OrderExecute find(String executeId) {
		return orderExecuteRepo.findOne(executeId);
	}

}
