package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.ChargeOrderExecute;
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

		Visit visit = order.getVisit();
		Pharmacy pharmacy = drugOrderTypeApp.getPharmacy();

		ChargeOrderExecute chargeOrderExecute = null;
		if (!order.isInPatient()) {
			// 收费执行条目
			chargeOrderExecute = new ChargeOrderExecute();
			chargeOrderExecute.setOrder(order);
			chargeOrderExecute.setVisit(visit);
			chargeOrderExecute.setBelongDept(order.getBelongDept());
			chargeOrderExecute.setType(OrderExecute.Type_Change);

			chargeOrderExecute.setExecuteDept(visit.getDept().getOrg()
					.getOutChargeDept());
			chargeOrderExecute.setChargeDept(pharmacy);
			chargeOrderExecute.setState(OrderExecute.State_Executing);

			team.addOrderExecute(chargeOrderExecute);
		}

		// 摆药执行条目
		DispensingDrugOrderExecute dispensingDrugExecute = new DispensingDrugOrderExecute();
		dispensingDrugExecute.setOrder(order);
		dispensingDrugExecute.setVisit(visit);
		dispensingDrugExecute.setBelongDept(order.getBelongDept());
		dispensingDrugExecute.setType(OrderExecute.Type_Dispense_Drug);
		dispensingDrugExecute.addChargeItem(drugOrderTypeApp.getDrugTypeSpec()
				.getChargeItem());
		dispensingDrugExecute.setCount(order.getCount());

		dispensingDrugExecute.setExecuteDept(pharmacy);
		dispensingDrugExecute.setChargeDept(pharmacy);

		if (order.isInPatient()) {
			dispensingDrugExecute.setState(OrderExecute.State_NeedSend);
		} else {
			dispensingDrugExecute.setState(OrderExecute.State_NeedExecute);
			chargeOrderExecute.setCharge(dispensingDrugExecute);
		}
		dispensingDrugExecute.setPharmacy(pharmacy);
		dispensingDrugExecute.setDrugTypeSpec(drugOrderTypeApp.getDrugTypeSpec());
		

		team.addOrderExecute(dispensingDrugExecute);

		// 发药执行条目
		DistributeDrugOrderExecute distributeDrugExecute = new DistributeDrugOrderExecute();
		distributeDrugExecute.setOrder(order);
		distributeDrugExecute.setVisit(visit);
		distributeDrugExecute.setBelongDept(order.getBelongDept());
		distributeDrugExecute.setType(OrderExecute.Type_Distribute_Drug);
		distributeDrugExecute.setExecuteDept(pharmacy);
		distributeDrugExecute.setMain(true);
		distributeDrugExecute.setState(OrderExecute.State_NeedExecute);
		
		distributeDrugExecute.setPharmacy(pharmacy);
		distributeDrugExecute.setDrugTypeSpec(drugOrderTypeApp.getDrugTypeSpec());

		team.addOrderExecute(distributeDrugExecute);

		order.addExecuteTeam(team);
	}

}
