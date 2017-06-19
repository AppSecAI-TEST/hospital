package com.neusoft.hs.domain.order;

import com.neusoft.hs.application.order.AbstractOrderBuilder;
import com.neusoft.hs.platform.util.DateUtil;

public class NursingOrderBuilder extends AbstractOrderBuilder {
	
	private OrderFrequencyType frequencyType;
	
	public void setFrequencyType(OrderFrequencyType frequencyType) {
		this.frequencyType = frequencyType;
	}

	@Override
	public OrderCreateCommand createCommand() {
		LongOrder order = new LongOrder();
		
		order.setVisit(visit);
		order.setName(orderType.getName());
		order.setOrderType(orderType);
		order.setPlanStartDate(DateUtil.getSysDate());
		order.setFrequencyType(frequencyType);
		order.setPlaceType(OrderCreateCommand.PlaceType_InPatient);
		
		return order;
	}
}
