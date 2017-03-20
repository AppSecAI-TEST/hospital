package com.neusoft.hs.domain.pharmacy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.ChargeOrderExecute;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteTeam;

@Entity
@DiscriminatorValue("Oral")
public class OralOrderUseMode extends DrugUseMode {

	@Override
	public void resolve(Order order, DrugOrderType drugOrderType) {

		OrderExecuteTeam team = new OrderExecuteTeam();

		DrugType drugType = drugOrderType.getDrugType();
		ChargeItem chargeItem = drugType.getDrugTypeSpec().getChargeItem();

//		if (!order.isInPatient()) {
//			// 收费执行条目
//			ChargeOrderExecute chargeOrderExecute = new ChargeOrderExecute();
//			chargeOrderExecute.setOrder(order);
//			chargeOrderExecute.setVisit(order.getVisit());
//			chargeOrderExecute.setBelongDept(order.getBelongDept());
//			chargeOrderExecute.setType(OrderExecute.Type_Change);
//
//			chargeOrderExecute.addChargeItem(chargeItem);
//			chargeOrderExecute.setCount(order.getCount());
//			
//			chargeOrderExecute.setExecuteDept(executeDept);
//		}

		// 摆药执行条目
		DispensingDrugOrderExecute dispensingDrugExecute = new DispensingDrugOrderExecute();
		dispensingDrugExecute.setOrder(order);
		dispensingDrugExecute.setVisit(order.getVisit());
		dispensingDrugExecute.setBelongDept(order.getBelongDept());
		dispensingDrugExecute.setType(OrderExecute.Type_Dispense_Drug);

		dispensingDrugExecute.addChargeItem(chargeItem);
		dispensingDrugExecute.setCount(order.getCount());
		dispensingDrugExecute.setDrugType(drugType);

		dispensingDrugExecute.setExecuteDept(drugOrderType.getDrugType()
				.getPharmacy());
		dispensingDrugExecute.setChargeState(OrderExecute.ChargeState_NoCharge);
		dispensingDrugExecute.setCostState(OrderExecute.CostState_NoCost);
		if (order.isInPatient()) {
			dispensingDrugExecute.setState(OrderExecute.State_NeedSend);
		} else {
			dispensingDrugExecute.setState(OrderExecute.State_Executing);
		}

		team.addOrderExecute(dispensingDrugExecute);

		// 取药执行条目
		TaskDrugOrderExecute taskDrugExecute = new TaskDrugOrderExecute();
		taskDrugExecute.setOrder(order);
		taskDrugExecute.setVisit(order.getVisit());
		taskDrugExecute.setBelongDept(order.getBelongDept());
		taskDrugExecute.setType(OrderExecute.Type_Take_Drug);
		if (order.isInPatient()) {
			taskDrugExecute.setExecuteDept(order.getBelongDept());
		} else {
			taskDrugExecute.setExecuteDept(drugOrderType.getDrugType()
					.getPharmacy());
		}
		taskDrugExecute.setState(OrderExecute.State_NeedExecute);
		taskDrugExecute.setChargeState(OrderExecute.ChargeState_NoCharge);
		taskDrugExecute.setCostState(OrderExecute.CostState_NoCost);

		team.addOrderExecute(taskDrugExecute);

		order.addExecutes(team.getExecutes());
	}

}
