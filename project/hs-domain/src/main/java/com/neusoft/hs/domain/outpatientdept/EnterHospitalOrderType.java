package com.neusoft.hs.domain.outpatientdept;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteTeam;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OrderTypeApp;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("EnterHospital")
public class EnterHospitalOrderType extends OrderType {

	@Override
	protected void create(Order order) throws OrderException {
		//是否在外部编写？
		order.getVisit().setDept((Dept)order.getParam("wardDept"));
		
		order.getVisit().setState(Visit.State_WaitingEnterHospital);
		order.getVisit().save();
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

		enter.setPlanStartDate(order.getPlanStartDate());
		enter.setPlanEndDate(order.getPlanStartDate());

		enter.setExecuteDept(order.getExecuteDept());
		enter.setState(OrderExecute.State_Executing);
		enter.setChargeState(OrderExecute.ChargeState_NoApply);
		enter.setCostState(OrderExecute.CostState_NoApply);

		team.addOrderExecute(enter);

		order.addExecutes(team.getExecutes());
	}

}
