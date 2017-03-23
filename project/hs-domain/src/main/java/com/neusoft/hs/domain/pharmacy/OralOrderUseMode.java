package com.neusoft.hs.domain.pharmacy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.ChargeOrderExecute;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteTeam;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("Oral")
public class OralOrderUseMode extends DrugUseMode {

	@Override
	public void resolve(Order order, DrugOrderType drugOrderType) {

		OrderExecuteTeam team = new OrderExecuteTeam();

		DrugType drugType = drugOrderType.getDrugType();
		ChargeItem chargeItem = drugType.getDrugTypeSpec().getChargeItem();
		Visit visit = order.getVisit();
		Pharmacy pharmacy = drugOrderType.getDrugType().getPharmacy();

		if (!order.isInPatient()) {
			// 收费执行条目
			ChargeOrderExecute chargeOrderExecute = new ChargeOrderExecute();
			chargeOrderExecute.setOrder(order);
			chargeOrderExecute.setVisit(visit);
			chargeOrderExecute.setBelongDept(order.getBelongDept());
			chargeOrderExecute.setType(OrderExecute.Type_Change);

			chargeOrderExecute.addChargeItem(chargeItem);
			chargeOrderExecute.setCount(order.getCount());

			chargeOrderExecute.setExecuteDept(visit.getDept().getOrg()
					.getOutChargeDept());
			chargeOrderExecute.setChargeDept(pharmacy);
			chargeOrderExecute
					.setChargeState(OrderExecute.ChargeState_NoCharge);
			chargeOrderExecute.setCostState(OrderExecute.CostState_NoCost);
			chargeOrderExecute.setState(OrderExecute.State_Executing);

			team.addOrderExecute(chargeOrderExecute);
		}

		// 摆药执行条目
		DispensingDrugOrderExecute dispensingDrugExecute = new DispensingDrugOrderExecute();
		dispensingDrugExecute.setOrder(order);
		dispensingDrugExecute.setVisit(visit);
		dispensingDrugExecute.setBelongDept(order.getBelongDept());
		dispensingDrugExecute.setType(OrderExecute.Type_Dispense_Drug);
		// 住院自动扣预交金
		if (order.isInPatient()) {
			dispensingDrugExecute.addChargeItem(chargeItem);
		}
		dispensingDrugExecute.setCount(order.getCount());
		dispensingDrugExecute.setDrugType(drugType);

		dispensingDrugExecute.setExecuteDept(pharmacy);
		dispensingDrugExecute.setChargeDept(pharmacy);
		dispensingDrugExecute.setChargeState(OrderExecute.ChargeState_NoCharge);
		dispensingDrugExecute.setCostState(OrderExecute.CostState_NoCost);

		if (order.isInPatient()) {
			dispensingDrugExecute.setState(OrderExecute.State_NeedSend);
		} else {
			dispensingDrugExecute.setState(OrderExecute.State_NeedExecute);
		}

		team.addOrderExecute(dispensingDrugExecute);

		// 取药执行条目
		TaskDrugOrderExecute taskDrugExecute = new TaskDrugOrderExecute();
		taskDrugExecute.setOrder(order);
		taskDrugExecute.setVisit(visit);
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
