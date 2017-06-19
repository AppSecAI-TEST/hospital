package com.neusoft.hs.application.order;

import java.util.Date;

import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.visit.Visit;

public interface OrderBuilder {

	public Visit getVisit();

	public OrderType getOrderType();

	public String getPlaceType();

	public Date getPlanStartDate();

	public Dept getExecuteDept();

	public OrderCreateCommand createCommand() throws OrderException;
}
