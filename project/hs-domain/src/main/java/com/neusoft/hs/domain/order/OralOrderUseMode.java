package com.neusoft.hs.domain.order;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.pharmacy.DrugType;

@Entity
@DiscriminatorValue("Oral")
public class OralOrderUseMode extends OrderUseMode {

	@Override
	public void resolve(Order order, DrugOrderType drugOrderType) {

		OrderExecuteTeam team = new OrderExecuteTeam();

		// 摆药执行条目
		DispensingDrugOrderExecute dispensingDrugExecute = new DispensingDrugOrderExecute();
		dispensingDrugExecute.setOrder(order);
		dispensingDrugExecute.setVisit(order.getVisit());
		dispensingDrugExecute.setBelongDept(order.getBelongDept());
		dispensingDrugExecute.setType(OrderExecute.Type_Dispense_Drug);

		DrugType drugType = drugOrderType.getDrugType();
		dispensingDrugExecute.addChargeItem(drugType.getDrugTypeSpec()
				.getChargeItem());
		dispensingDrugExecute.setCount(order.getCount());
		dispensingDrugExecute.setDrugType(drugType);

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

		order.addExecutes(team.getExecutes());
	}

}
