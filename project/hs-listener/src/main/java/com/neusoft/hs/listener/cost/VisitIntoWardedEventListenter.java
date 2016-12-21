//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\listener\\ConstListenter.java

package com.neusoft.hs.listener.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitIntoWardedEvent;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class VisitIntoWardedEventListenter implements
		ApplicationListener<VisitIntoWardedEvent> {

	@Autowired
	private CostDomainService costDomainService;

	@Override
	public void onApplicationEvent(VisitIntoWardedEvent event) {

		ChargeItem item = new ChargeItem();

//		costDomainService.createVisitChargeItem((Visit) event.getSource(),
//				item, DateUtil.getSysDate());
	}
}
