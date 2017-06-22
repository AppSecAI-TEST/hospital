package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("OutHospital")
public class OutHospitalOrderType extends OrderType {

	@Override
	protected void verify(Order order) throws OrderException {
		Visit visit = order.getVisit();
		visit.setPlanLeaveWardDate(order.getPlanStartDate());
	}

	@Override
	public void resolveOrder(OrderTypeApp orderTypeApp) {
		
		Order order = orderTypeApp.getOrder();

		OrderExecuteTeam team = new OrderExecuteTeam();

		// 出院登记执行条目
		OutHospitalRegisterOrderExecute register = new OutHospitalRegisterOrderExecute();
		register.setOrder(order);
		register.setVisit(order.getVisit());
		register.setBelongDept(order.getBelongDept());
		register.setType(OrderExecute.Type_Leave_Hospital_Register);
		register.setMain(true);

		register.setPlanStartDate(order.getPlanStartDate());
		register.setPlanEndDate(order.getPlanStartDate());

		register.setExecuteDept(order.getBelongDept());
		register.setState(OrderExecute.State_NeedExecute);

		team.addOrderExecute(register);

		// 出院结算执行条目
		OutHospitalBalanceOrderExecute balance = new OutHospitalBalanceOrderExecute();
		balance.setOrder(order);
		balance.setVisit(order.getVisit());
		balance.setBelongDept(order.getBelongDept());
		balance.setType(OrderExecute.Type_Leave_Hospital_Balance);

		balance.setPlanStartDate(order.getPlanStartDate());
		balance.setPlanEndDate(order.getPlanStartDate());

		balance.setExecuteDept(order.getBelongDept().getOrg().getInChargeDept());
		balance.setState(OrderExecute.State_NeedExecute);

		team.addOrderExecute(balance);

		order.addExecuteTeam(team);
	}

}
