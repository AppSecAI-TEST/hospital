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
import com.neusoft.hs.domain.organization.Admin;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderExecuteDomainService {

	@Autowired
	private OrderExecuteRepo orderExecuteRepo;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 得到需要发送的执行条目列表
	 *
	 * @param nurse
	 * @param planStartDate
	 * @param pageable
	 * @return
	 */
	public List<OrderExecute> getNeedSendOrderExecutes(AbstractUser nurse,
			Date planStartDate, Pageable pageable) {
		return orderExecuteRepo
				.findByStateAndBelongDeptInAndPlanStartDateLessThan(
						OrderExecute.State_NeedSend, nurse.getOperationDepts(),
						planStartDate, pageable);
	}

	/**
	 * 得到需要执行的执行条目列表
	 * 
	 * @param user
	 * @param planStartDate
	 * @param pageable
	 * @return
	 */
	public List<OrderExecute> getNeedExecuteOrderExecutes(AbstractUser user,
			Date planStartDate, Pageable pageable) {
		return orderExecuteRepo
				.findByStateAndExecuteDeptInAndPlanStartDateLessThan(
						OrderExecute.State_Executing, user.getOperationDepts(),
						planStartDate, pageable);
	}

	/**
	 * 得到需要执行的执行条目列表
	 * 
	 * @param visit
	 * @param type
	 * @param user
	 * @param planStartDate
	 * @param pageable
	 * @return
	 */
	public List<OrderExecute> getNeedExecuteOrderExecutes(Visit visit,
			String type, AbstractUser user, Date planStartDate,
			Pageable pageable) {
		return orderExecuteRepo
				.findByVisitAndTypeAndStateAndExecuteDeptAndPlanStartDateLessThan(
						visit, type, OrderExecute.State_Executing,
						user.getDept(), planStartDate, pageable);
	}

	/**
	 * 得到需要退费的执行条目列表
	 * 
	 * @param user
	 * @param pageable
	 * @return
	 */
	public List<OrderExecute> getNeedBackChargeOrderExecutes(Staff user,
			Pageable pageable) {
		return orderExecuteRepo.findByChargeState(
				OrderExecute.ChargeState_NeedBackCharge, pageable);
	}

	public OrderExecute find(String executeId) {
		return orderExecuteRepo.findOne(executeId);
	}

	public List<OrderExecute> findByState(String state, Pageable pageable) {
		return orderExecuteRepo.findByState(state, pageable);
	}

	public List<OrderExecute> findByChargeState(String chargeState,
			Pageable pageable) {
		return orderExecuteRepo.findByChargeState(chargeState, pageable);
	}

	/**
	 * 根据自定义条件查询执行条目列表
	 * 
	 * @param filter
	 * @param params
	 * @param user
	 * @param pageable
	 * @return
	 * @throws HsException
	 */
	public List<OrderExecute> find(OrderExecuteFilter filter,
			Map<String, Object> params, AbstractUser user, Pageable pageable)
			throws HsException {
		OrderExecuteFilterCondition condition = filter.createCondition(params,
				user);

		Date begin = condition.getBegin();
		Date end = condition.getEnd();
		List<String> types = condition.getTypes();
		String category = condition.getCategory();

		List<? extends Dept> belongDepts = condition.getBelongDepts();

		if (belongDepts.size() > 1) {
			if (types.size() > 1) {
				if (category == null) {
					return orderExecuteRepo
							.findByStateAndBelongDeptInAndTypeInAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing, belongDepts,
									types, begin, end, pageable);
				} else {
					return orderExecuteRepo
							.findByStateAndBelongDeptInAndTypeInAndOrderCategoryAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing, belongDepts,
									types, category, begin, end, pageable);
				}
			} else if (types.size() == 1) {
				if (category == null) {
					return orderExecuteRepo
							.findByStateAndBelongDeptInAndTypeAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing, belongDepts,
									types.get(0), begin, end, pageable);
				} else {
					return orderExecuteRepo
							.findByStateAndBelongDeptInAndTypeAndOrderCategoryAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing, belongDepts,
									types.get(0), category, begin, end,
									pageable);
				}
			} else {
				if (category == null) {
					return orderExecuteRepo
							.findByStateAndBelongDeptInAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing, belongDepts,
									begin, end, pageable);
				} else {
					return orderExecuteRepo
							.findByStateAndBelongDeptInAndOrderCategoryAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing, belongDepts,
									category, begin, end, pageable);
				}
			}
		} else if (belongDepts.size() > 1) {
			if (types.size() > 1) {
				if (category == null) {
					return orderExecuteRepo
							.findByStateAndBelongDeptAndTypeInAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing,
									belongDepts.get(0), types, begin, end,
									pageable);
				} else {
					return orderExecuteRepo
							.findByStateAndBelongDeptAndTypeInAndOrderCategoryAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing,
									belongDepts.get(0), types, category, begin,
									end, pageable);
				}
			} else if (types.size() == 1) {
				if (category == null) {
					return orderExecuteRepo
							.findByStateAndBelongDeptAndTypeAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing,
									belongDepts.get(0), types.get(0), begin,
									end, pageable);
				} else {
					return orderExecuteRepo
							.findByStateAndBelongDeptAndTypeAndOrderCategoryAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
									OrderExecute.State_Executing,
									belongDepts.get(0), types.get(0), category,
									begin, end, pageable);
				}
			} else {
				throw new HsException("代码未编写");
			}
		} else {
			throw new HsException("代码未编写");
		}
	}

	/**
	 * 发送医嘱执行条目
	 * 
	 * @param nurse
	 * @param executeId
	 * @throws OrderExecuteException
	 * @roseuid 584F6150022C
	 */
	public void send(OrderExecute execute, AbstractUser nurse)
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
	public int start(Admin admin) {
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
}
