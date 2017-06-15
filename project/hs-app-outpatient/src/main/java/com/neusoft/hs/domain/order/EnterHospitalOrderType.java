package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("EnterHospital")
public class EnterHospitalOrderType extends OrderType {

	public static final String WardDept = "wardDept";
	
	public static final String WardArea = "wardArea";

	public static final String RespDoctor = "respDoctor";

	@Override
	protected void create(Order order) throws OrderException {

		Dept wardDept = (Dept) order.getParam(WardDept);
		if (wardDept == null) {
			throw new OrderException(null, "创建住院医嘱未指定住院科室");
		}
		
		Dept wardArea = (Dept) order.getParam(WardArea);
		if (wardArea == null) {
			throw new OrderException(null, "创建住院医嘱未指定住院病区");
		}

		Doctor respDoctor = (Doctor) order.getParam(RespDoctor);
		if (respDoctor == null) {
			throw new OrderException(null, "创建住院医嘱未指定责任医生");
		}

		Visit visit = order.getVisit();
		visit.setDept(wardDept);
		visit.setArea(wardArea);
		visit.setRespDoctor(respDoctor);
		visit.setState(Visit.State_WaitingEnterHospital);
		visit.save();
	}

	@Override
	public void resolveOrder(OrderTypeApp orderTypeApp) {

		Order order = orderTypeApp.getOrder();

		OrderExecuteTeam team = new OrderExecuteTeam();

		// 入院登记执行条目
		EnterHospitalOrderExecute enter = new EnterHospitalOrderExecute();
		enter.setOrder(order);
		enter.setVisit(order.getVisit());
		enter.setBelongDept(order.getBelongDept());
		enter.setType(OrderExecute.Type_Enter_Hospital_Register);
		enter.setMain(true);

		enter.setPlanStartDate(order.getPlanStartDate());
		enter.setPlanEndDate(order.getPlanStartDate());

		enter.setExecuteDept(order.getExecuteDept());
		enter.setState(OrderExecute.State_Executing);

		team.addOrderExecute(enter);

		order.addExecuteTeam(team);
	}

}
