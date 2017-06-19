package com.neusoft.hs.application.order;

import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.visit.Visit;

public interface OrderBuilder {

	public Visit getVisit();

	public OrderType getOrderType();

	public String getPlaceType();

	public OrderCreateCommand createCommand();

}
