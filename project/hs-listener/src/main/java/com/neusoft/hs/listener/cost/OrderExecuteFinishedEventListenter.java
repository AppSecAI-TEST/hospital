//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\listener\\ConstListenter.java

package com.neusoft.hs.listener.cost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteFinishedEvent;

@Service
public class OrderExecuteFinishedEventListenter implements
		ApplicationListener<OrderExecuteFinishedEvent> {

	@Autowired
	private CostDomainService costDomainService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(OrderExecuteFinishedEvent event) {

		OrderExecute execute = (OrderExecute) event.getSource();

		Float amount = costDomainService.charging(execute);

		logger.info("医嘱执行条目[{}]产生费用{}", execute.getId(), amount);
	}
}
