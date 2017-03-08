package com.neusoft.hs.domain.order;

import java.util.List;

import com.neusoft.hs.domain.visit.Visit;

public interface OrderCreateCommand {

	public List<Order> getOrders();

	public String getPlaceType();

	public Visit getVisit();

	public void save();

	public static final String PlaceType_OutPatient = "门诊";

	public static final String PlaceType_InPatient = "住院";

}
