//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderExecuteDomainService.java

package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Pageable;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.visit.Visit;

@FeignClient("engine-service")
public interface OrderExecuteDomainService {

	public List<OrderExecute> getNeedSendOrderExecutes(Nurse nurse,
			Date planStartDate, Pageable pageable);

	public List<OrderExecute> getNeedExecuteOrderExecutes(AbstractUser user,
			Date planStartDate, Pageable pageable);

	public List<OrderExecute> getNeedExecuteOrderExecutes(Visit visit,
			String type, AbstractUser user, Date planStartDate,
			Pageable pageable);

	public List<OrderExecute> getNeedBackChargeOrderExecutes(Staff user,
			Pageable pageable);

	/**
	 * 发送医嘱执行条目
	 * 
	 * @param nurse
	 * @param executeId
	 * @throws OrderExecuteException
	 * @roseuid 584F6150022C
	 */
	public void send(OrderExecute execute, Nurse nurse)
			throws OrderExecuteException;

	/**
	 * 启动符合条件的医嘱执行条目
	 * 
	 * @roseuid 584F691702B2
	 */
	public int start() throws OrderExecuteException;

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
			AbstractUser user) throws OrderExecuteException;

	public OrderExecute find(String executeId);
}
