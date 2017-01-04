package com.neusoft.hs.domain.order;

import java.util.List;

public class OralOrderResolver implements DrugTypeOrderResolver {

	@Override
	public List<OrderExecute> resolve(Order order, DrugOrderType drugOrderType) {

		OrderExecuteTeam team = new OrderExecuteTeam();

		// 摆药执行条目
		DispensingDrugOrderExecute dispensingDrugExecute = new DispensingDrugOrderExecute();
		dispensingDrugExecute.setOrder(order);
		dispensingDrugExecute.setVisit(order.getVisit());
		dispensingDrugExecute.setBelongDept(order.getBelongDept());
		dispensingDrugExecute.setType(OrderExecute.Type_Dispense_Drug);

		DrugOrderType type = (DrugOrderType) order.getType();
		dispensingDrugExecute.addChargeItem(type.getDrugType()
				.getDrugTypeSpec().getChargeItem());
		dispensingDrugExecute.setCount(order.getCount());

		dispensingDrugExecute.setExecuteDept(drugOrderType.getDrugType()
				.getPharmacy());
		dispensingDrugExecute.setState(OrderExecute.State_NeedSend);
		dispensingDrugExecute.setChargeState(OrderExecute.ChargeState_NoCharge);
		dispensingDrugExecute.setCostState(OrderExecute.CostState_NoCost);

		team.addOrderExecute(dispensingDrugExecute);

		// 取药执行条目
		OrderExecute taskDrugExecute = new OrderExecute();
		taskDrugExecute.setOrder(order);
		taskDrugExecute.setVisit(order.getVisit());
		taskDrugExecute.setBelongDept(order.getBelongDept());
		taskDrugExecute.setType(OrderExecute.Type_Take_Drug);

		taskDrugExecute.setExecuteDept(order.getBelongDept());
		taskDrugExecute.setState(OrderExecute.State_NeedExecute);
		taskDrugExecute.setChargeState(OrderExecute.ChargeState_NoCharge);
		taskDrugExecute.setCostState(OrderExecute.CostState_NoCost);

		team.addOrderExecute(taskDrugExecute);

		return team.getExecutes();
	}

}
