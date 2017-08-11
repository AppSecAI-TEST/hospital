package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TransferDept")
public class TransferDeptOrderType extends OrderType {

	@Override
	protected void check(Order order) throws OrderException,
			OrderExecuteException {
		if (order.getExecuteDept() == null) {
			throw new OrderException(order, "没有指定转科科室");
		}
	}

	@Override
	public void resolveOrder(OrderTypeApp orderTypeApp) throws OrderException {
		Order order = orderTypeApp.getOrder();

		OrderExecuteTeam team = new OrderExecuteTeam();

		// 转科确认执行条目
		TransferDeptConfirmOrderExecute transferConfirm = new TransferDeptConfirmOrderExecute();
		transferConfirm.setOrder(order);
		transferConfirm.setVisit(order.getVisit());
		transferConfirm.setBelongDept(order.getBelongDept());
		transferConfirm.setType(OrderExecute.Type_Transfer_Dept_Confirm);
		transferConfirm.setMain(true);

		transferConfirm.setPlanStartDate(order.getPlanStartDate());
		transferConfirm.setPlanEndDate(order.getPlanStartDate());

		transferConfirm.setExecuteDept(order.getExecuteDept());
		transferConfirm.setState(OrderExecute.State_NeedSend);

		team.addOrderExecute(transferConfirm);

		order.addExecuteTeam(team);
	}

}
