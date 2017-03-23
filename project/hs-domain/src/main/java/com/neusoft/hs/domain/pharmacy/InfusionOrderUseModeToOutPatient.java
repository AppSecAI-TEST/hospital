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
@DiscriminatorValue("Infusion_OutPatient")
public class InfusionOrderUseModeToOutPatient extends DrugUseMode {

	public static final String transportFluid = "transportFluid";

	@Override
	public void resolve(Order order, DrugOrderType drugOrderType) {
		OrderExecuteTeam team = new OrderExecuteTeam();

		DrugType drugType = drugOrderType.getDrugType();
		ChargeItem chargeItem = drugType.getDrugTypeSpec().getChargeItem();
		Visit visit = order.getVisit();
		Pharmacy pharmacy = drugOrderType.getDrugType().getPharmacy();

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
		chargeOrderExecute.setChargeState(OrderExecute.ChargeState_NoCharge);
		chargeOrderExecute.setCostState(OrderExecute.CostState_NoCost);
		chargeOrderExecute.setState(OrderExecute.State_Executing);

		team.addOrderExecute(chargeOrderExecute);

		// 摆药执行条目
		DispensingDrugOrderExecute dispensingDrugExecute = new DispensingDrugOrderExecute();
		dispensingDrugExecute.setOrder(order);
		dispensingDrugExecute.setVisit(visit);
		dispensingDrugExecute.setBelongDept(order.getBelongDept());
		dispensingDrugExecute.setType(OrderExecute.Type_Dispense_Drug);
		dispensingDrugExecute.setCount(order.getCount());
		dispensingDrugExecute.setDrugType(drugType);

		dispensingDrugExecute.setExecuteDept(pharmacy);
		dispensingDrugExecute.setChargeDept(pharmacy);
		dispensingDrugExecute.setChargeState(OrderExecute.ChargeState_NoCharge);
		dispensingDrugExecute.setCostState(OrderExecute.CostState_NoCost);
		dispensingDrugExecute.setState(OrderExecute.State_NeedExecute);

		team.addOrderExecute(dispensingDrugExecute);

		// 输液执行条目
		TransportFluidOrderExecute transportFluidExecute = new TransportFluidOrderExecute();
		transportFluidExecute.setOrder(order);
		transportFluidExecute.setVisit(order.getVisit());
		transportFluidExecute.setBelongDept(order.getBelongDept());
		transportFluidExecute.setType(OrderExecute.Type_Transport_Fluid);
		transportFluidExecute.setDrugType(drugType);

		// 处理辅材产生的费用
		DrugUseModeAssistMaterial orderUseModeAssistMaterial = this
				.getTheOrderUseModeChargeItem(transportFluid);
		if (orderUseModeAssistMaterial != null) {
			transportFluidExecute.addChargeItem(orderUseModeAssistMaterial
					.getAssistMaterial().getChargeItem());
		}

		transportFluidExecute.setExecuteDept(order.getBelongDept());
		transportFluidExecute.setChargeDept(order.getBelongDept());
		transportFluidExecute.setState(OrderExecute.State_NeedExecute);
		transportFluidExecute.setChargeState(OrderExecute.ChargeState_NoCharge);
		transportFluidExecute.setCostState(OrderExecute.CostState_NoCost);

		team.addOrderExecute(transportFluidExecute);

		order.addExecutes(team.getExecutes());
	}
}
