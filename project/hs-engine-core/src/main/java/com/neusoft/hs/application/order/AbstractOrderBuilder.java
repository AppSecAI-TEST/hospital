package com.neusoft.hs.application.order;

import java.util.Date;

import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.util.DateUtil;

public abstract class AbstractOrderBuilder implements OrderBuilder {

	protected Visit visit;

	protected OrderType orderType;

	protected String placeType;

	protected Date planStartDate;

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public Date getPlanStartDate() {
		if (planStartDate == null) {
			return DateUtil.getSysDate();
		} else {
			return planStartDate;
		}
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
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
