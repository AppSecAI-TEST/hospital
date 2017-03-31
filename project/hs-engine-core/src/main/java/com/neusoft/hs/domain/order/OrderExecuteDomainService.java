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
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderExecuteDomainService {

	@Autowired
	private OrderExecuteRepo orderExecuteRepo;

	@Autowired
	private ApplicationContext applicationContext;

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

	public List<OrderExecute> getNeedExecuteOrderExecutes(Visit visit,
			String type, AbstractUser user, Date planStartDate,
			Pageable pageable) {
		return orderExecuteRepo
				.findByVisitAndTypeAndStateAndExecuteDeptAndPlanStartDateLessThan(
						visit, type, OrderExecute.State_Executing,
						user.getDept(), planStartDate, pageable);
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
	public void send(OrderExecute execute, Nurse nurse)
			throws OrderExecuteException {

		execute.send();

		applicationContext.publishEvent(new OrderExecuteSendedEvent(execute));

		LogUtil.log(this.getClass(), "护士[{}]发送患者一次就诊[{}]的医嘱执行条目[{}],类型为[{}]",
				nurse.getId(), execute.getVisit().getName(), execute.getId(),
				execute.getType());

	}

	/**
	 * 启动符合条件的医嘱执行条目
	 * 
	 * @roseuid 584F691702B2
	 */
	public int start() throws OrderExecuteException {
		Date sysDate = DateUtil.getSysDate();
		Date startDate = DateUtil.addDay(DateUtil.getSysDateStart(), 1);
		int count = orderExecuteRepo.start(OrderExecute.State_Executing,
				OrderExecute.State_NeedExecute, ChargeBill.State_Normal,
				sysDate, startDate);

		LogUtil.log(this.getClass(), "系统自动启动了{}条符合条件的医嘱执行条目", count);

		return count;
	}

	/**
	 * 完成医嘱执行条目
	 * 
	 * @param user
	 * @param execute
	 * @param params
	 * @throws OrderExecuteException
	 * @roseuid 584FB6AF013C
	 */
	public void finish(OrderExecute execute, Map<String, Object> params,
			AbstractUser user) throws OrderExecuteException {

		execute.finish(params, user);

		applicationContext.publishEvent(new OrderExecuteFinishedEvent(execute));

		LogUtil.log(this.getClass(), "用户[{}]完成了患者一次就诊[{}]的医嘱执行条目{},类型为[{}]",
				user.getId(), execute.getVisit().getName(), execute.getId(),
				execute.getType());
	}

	public OrderExecute find(String executeId) {
		return orderExecuteRepo.findOne(executeId);
	}

}
