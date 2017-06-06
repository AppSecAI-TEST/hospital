package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.ChargeOrderExecute;
import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("Oral")
public class OralOrderUseMode extends DrugUseMode {

	@Override
	public void resolve(Order order) {

		OrderExecuteTeam team = new OrderExecuteTeam();

		DrugOrderTypeApp drugOrderTypeApp = (DrugOrderTypeApp) order
				.getTypeApp();

		DrugType drugType = drugOrderTypeApp.getDrugType();
		ChargeItem chargeItem = drugType.getDrugTypeSpec().getChargeItem();
		Visit visit = order.getVisit();
		Pharmacy pharmacy = drugOrderTypeApp.getDrugType().getPharmacy();

		ChargeOrderExecute chargeOrderExecute = null;
		if (!order.isInPatient()) {
			// 收费执行条目
			chargeOrderExecute = new ChargeOrderExecute();
			chargeOrderExecute.setOrder(order);
			chargeOrderExecute.setVisit(visit);
			chargeOrderExecute.setBelongDept(order.getBelongDept());
			chargeOrderExecute.setType(OrderExecute.Type_Change);

			chargeOrderExecute.addChargeItem(chargeItem);
			chargeOrderExecute.setCount(order.getCount());

			chargeOrderExecute.setExecuteDept(visit.getDept().getOrg()
					.getOutChargeDept());
			chargeOrderExecute.setChargeDept(pharmacy);
			chargeOrderExecute.setChargeState(OrderExecute.ChargeState_NoApply);
			chargeOrderExecute.setCostState(OrderExecute.CostState_NoApply);
			chargeOrderExecute.setState(OrderExecute.State_Executing);

			team.addOrderExecute(chargeOrderExecute);
		}

		// 摆药执行条目
		DispensingDrugOrderExecute dispensingDrugExecute = new DispensingDrugOrderExecute();
		dispensingDrugExecute.setOrder(order);
		dispensingDrugExecute.setVisit(visit);
		dispensingDrugExecute.setBelongDept(order.getBelongDept());
		dispensingDrugExecute.setType(OrderExecute.Type_Dispense_Drug);
		dispensingDrugExecute.addChargeItem(chargeItem);
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
			chargeOrderExecute.setCharge(dispensingDrugExecute);
		}

		team.addOrderExecute(dispensingDrugExecute);

		// 取药执行条目
		TaskDrugOrderExecute taskDrugExecute = new TaskDrugOrderExecute();
		taskDrugExecute.setOrder(order);
		taskDrugExecute.setVisit(visit);
		taskDrugExecute.setBelongDept(order.getBelongDept());
		taskDrugExecute.setType(OrderExecute.Type_Take_Drug);
		taskDrugExecute.setExecuteDept(pharmacy);
		taskDrugExecute.setMain(true);
		taskDrugExecute.setState(OrderExecute.State_NeedExecute);
		taskDrugExecute.setChargeState(OrderExecute.ChargeState_NoApply);
		taskDrugExecute.setCostState(OrderExecute.CostState_NoApply);

		team.addOrderExecute(taskDrugExecute);

		order.addExecuteTeam(team);
	}

}
