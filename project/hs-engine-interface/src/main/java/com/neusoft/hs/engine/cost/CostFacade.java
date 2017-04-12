package com.neusoft.hs.engine.cost;

import java.util.List;

import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

public interface CostFacade {

	/**
	 * 得到需要初始化财务账户的患者一次就诊列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<Visit> getNeedInitAccount(int pageNumber, int pageSize);

	/**
	 * 创建财务账户
	 * 
	 * @param chargeBillCreateDto
	 * @throws HsException
	 * @roseuid 584DFD470092
	 */
	public ChargeBill createChargeBill(ChargeBillCreateDTO chargeBillCreateDto)
			throws HsException;

	/**
	 * 预存费用
	 * 
	 * @param chargeBillAddCostDto
	 * @return
	 * @throws HsException
	 */
	public ChargeRecord addCost(ChargeBillAddCostDTO chargeBillAddCostDto)
			throws HsException;

	/**
	 * 得到需要退费的执行条目列表
	 * 
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws HsException
	 */
	public List<OrderExecute> getNeedBackChargeOrderExecutes(String userId,
			int pageNumber, int pageSize) throws HsException;

	/**
	 * 取消医嘱执行条目对应的费用条目
	 * 
	 * @param unChargingDto
	 * @throws OrderExecuteException
	 * @roseuid 5850BC9B0098
	 */
	public void unCharging(UnChargingDTO unChargingDto)
			throws OrderExecuteException;

}
