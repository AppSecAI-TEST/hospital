package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;

public interface OrderCreateCommand {

	public List<Order> getOrders();

	public String getPlaceType();

	public Visit getVisit();

	public void save();

	public Doctor getCreator();

	public void setCreator(Doctor creator);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public static final String PlaceType_OutPatient = "门诊";

	public static final String PlaceType_InPatient = "住院";

}
