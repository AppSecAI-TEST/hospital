//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\listener\\ConstListenter.java

package com.neusoft.hs.listener.cost;

import java.util.Date;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.cost.VisitChargeItem;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitOutWardedEvent;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class VisitOutWardedEventListenter implements
		ApplicationListener<VisitOutWardedEvent> {

	@Override
	public void onApplicationEvent(VisitOutWardedEvent event) {

		Visit visit = (Visit) event.getSource();
		
		Date sysDate = DateUtil.getSysDate();

		for (VisitChargeItem visitChargeItem : visit.getChargeItems()) {
			visitChargeItem.setState(VisitChargeItem.State_Stop);
			visitChargeItem.setEndDate(sysDate);
		}
	}
}
