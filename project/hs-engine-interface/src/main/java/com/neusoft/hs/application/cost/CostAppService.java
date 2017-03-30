package com.neusoft.hs.application.cost;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.Nurse;

@FeignClient("engine-service")
public interface CostAppService {

	/**
	 * 取消医嘱执行条目对应的费用条目
	 * 
	 * @param user
	 * @param executeId
	 * @throws OrderExecuteException
	 * @roseuid 5850BC9B0098
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/cost/unCharging/{executeId}/executeId/{isBackCost}/isBackCost/{nurse}/nurse")
	public void unCharging(@PathVariable("executeId") String executeId,
			@PathVariable("isBackCost") boolean isBackCost,
			@PathVariable("nurse") Nurse nurse) throws OrderExecuteException;

}
