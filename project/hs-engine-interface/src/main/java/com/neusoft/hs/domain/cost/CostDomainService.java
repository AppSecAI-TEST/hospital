//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\CostDomainService.java

package com.neusoft.hs.domain.cost;

import java.util.Date;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

@FeignClient("engine-service")
public interface CostDomainService {

	@RequestMapping(method = RequestMethod.GET, value = "/cost/visit/list/needInitAccount")
	public List<Visit> getNeedInitAccount(Pageable pageable);

	/**
	 * 创建病历夹
	 * 
	 * @throws HsException
	 * @roseuid 584DFD470092
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/cost/createChargeBill/{visit}/visit/{balance}/balance/{user}/user")
	public ChargeBill createChargeBill(@PathVariable("visit") Visit visit,
			@PathVariable("balance") float balance,
			@PathVariable("user") AbstractUser user) throws HsException;

	/**
	 * 预存费用
	 * 
	 * @param visit
	 * @param balance
	 * @param user
	 * @return
	 * @throws HsException
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/cost/addCost/{visit}/visit/{balance}/balance/{user}/user")
	public ChargeRecord addCost(@PathVariable("visit") Visit visit,
			@PathVariable("balance") float balance,
			@PathVariable("user") AbstractUser user) throws HsException;

	/**
	 * 创建自动收费项目
	 * 
	 * @param visit
	 * @roseuid 584E2630028C
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/cost/createVisitChargeItem/{visit}/visit/{item}/item/{startDate}/startDate")
	public void createVisitChargeItem(@PathVariable("visit") Visit visit,
			@PathVariable("item") ChargeItem item,
			@PathVariable("startDate") Date startDate);

	/**
	 * 根据医嘱执行条目创建费用条目
	 * 
	 * @param execute
	 * @roseuid 584FBC02036D
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/cost/charging")
	public ChargeResult charging(@RequestBody OrderExecute execute);

	/**
	 * 取消医嘱执行条目对应的费用条目
	 * 
	 * @param user
	 * @param execute
	 * @throws OrderExecuteException
	 * @roseuid 5850BC9B0098
	 */
	public void unCharging(OrderExecute execute, boolean isBackCost, Nurse nurse)
			throws OrderExecuteException;

	public List<OrderExecute> getNeedBackChargeOrderExecutes(Staff user,
			Pageable pageable);

	public void create(List<ChargeItem> chargeItems);

	public void clearChargeItems();

	public void clearCostRecords();

	public void clearChargeBill();

}
