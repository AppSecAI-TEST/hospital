//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\listener\\ConstListenter.java

package com.neusoft.hs.listener.cost;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitOutWardedEvent;

@Service
public class VisitOutWardedEventListenter implements
		ApplicationListener<VisitOutWardedEvent> {

	@Override
	public void onApplicationEvent(VisitOutWardedEvent event) {

		Visit visit = (Visit) event.getSource();

		//visit.get
	}
}
