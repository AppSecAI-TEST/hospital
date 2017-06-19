package com.neusoft.hs.application.order;

import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.visit.Visit;

public abstract class AbstractOrderBuilder implements OrderBuilder {

	protected Visit visit;

	protected OrderType orderType;

	protected String placeType;

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public Visit getVisit() {
		return visit;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public String getPlaceType() {
		return placeType;
	}

}
