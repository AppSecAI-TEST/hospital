package com.neusoft.hs.domain.order;

import com.neusoft.hs.application.order.AbstractOrderBuilder;

public class TransferDeptOrderBuilder extends AbstractOrderBuilder {

	@Override
	public OrderCreateCommand createCommand() throws OrderException {
		TemporaryOrder order = new TemporaryOrder();
		
		order.setVisit(visit);
		order.setName(orderType.getName());
		order.setOrderType(orderType);
		order.setPlanStartDate(getPlanStartDate());
		order.setPlaceType(OrderCreateCommand.PlaceType_InPatient);
		
		order.setExecuteDept(executeDept);
		
		return order;
	}

}
