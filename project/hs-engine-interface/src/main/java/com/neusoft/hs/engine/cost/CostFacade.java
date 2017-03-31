package com.neusoft.hs.engine.cost;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

public interface CostFacade {

	/**
	 * 得到需要初始化财务账户的患者一次就诊列表
	 * 
	 * @param pageable
	 * @return
	 */
	public List<Visit> getNeedInitAccount(Pageable pageable);

	/**
	 * 创建财务账户
	 * 
	 * @throws HsException
	 * @roseuid 584DFD470092
	 */
	public ChargeBill createChargeBill(Visit visit, float balance,
			AbstractUser user) throws HsException;

	/**
	 * 预存费用
	 * 
	 * @param visit
	 * @param balance
	 * @param user
	 * @return
	 * @throws HsException
	 */
	public ChargeRecord addCost(Visit visit, float balance, AbstractUser user)
			throws HsException;

	/**
	 * 得到需要退费的执行条目列表
	 * 
	 * @param user
	 * @param pageable
	 * @return
	 */
	public List<OrderExecute> getNeedBackChargeOrderExecutes(Staff user,
			Pageable pageable);

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
