package com.neusoft.hs.domain.order;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("LeaveHospital")
public class LeaveHospitalOrderType extends OrderType {

	@Override
	public List<OrderExecute> resolveOrder(Order order) {

		OrderExecuteTeam team = new OrderExecuteTeam();

		// 出院登记执行条目
		LeaveHospitalRegisterOrderExecute register = new LeaveHospitalRegisterOrderExecute();
		register.setOrder(order);
		register.setVisit(order.getVisit());
		register.setBelongDept(order.getBelongDept());
		register.setType(OrderExecute.Type_Leave_Hospital_Register);

		register.setPlanStartDate(order.getPlanStartDate());
		register.setPlanEndDate(order.getPlanStartDate());

		register.setExecuteDept(order.getBelongDept());
		register.setState(OrderExecute.State_NeedExecute);
		register.setChargeState(OrderExecute.ChargeState_NoCharge);
		register.setCostState(OrderExecute.CostState_NoCost);

		team.addOrderExecute(register);

		// 出院结算执行条目
		LeaveHospitalBalanceOrderExecute balance = new LeaveHospitalBalanceOrderExecute();
		balance.setOrder(order);
		balance.setVisit(order.getVisit());
		balance.setBelongDept(order.getBelongDept());
		balance.setType(OrderExecute.Type_Leave_Hospital_Balance);

		balance.setPlanStartDate(order.getPlanStartDate());
		balance.setPlanEndDate(order.getPlanStartDate());

		balance.setExecuteDept(order.getExecuteDept());
		balance.setState(OrderExecute.State_NeedExecute);
		balance.setChargeState(OrderExecute.ChargeState_NoCharge);
		balance.setCostState(OrderExecute.CostState_NoCost);

		team.addOrderExecute(balance);

		return team.getExecutes();
	}

}
