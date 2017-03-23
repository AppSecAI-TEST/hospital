package com.neusoft.hs.domain.pharmacy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteTeam;

@Entity
@DiscriminatorValue("Infusion_InPatient")
public class InfusionOrderUseModeToInPatient extends DrugUseMode {

	public static final String transportFluid = "transportFluid";

	@Override
	public void resolve(Order order, DrugOrderType drugOrderType) {
		OrderExecuteTeam team = new OrderExecuteTeam();

		DrugType drugType = drugOrderType.getDrugType();
		// 配液执行条目
		ConfigureFluidOrderExecute configureFluidDrugExecute = new ConfigureFluidOrderExecute();
		configureFluidDrugExecute.setOrder(order);
		configureFluidDrugExecute.setVisit(order.getVisit());
		configureFluidDrugExecute.setBelongDept(order.getBelongDept());
		configureFluidDrugExecute.setType(OrderExecute.Type_Configure_Fluid);

		configureFluidDrugExecute.addChargeItem(drugType.getDrugTypeSpec()
				.getChargeItem());
		configureFluidDrugExecute.setCount(order.getCount());
		configureFluidDrugExecute.setDrugType(drugType);

		configureFluidDrugExecute.setExecuteDept(drugOrderType.getDrugType()
				.getPharmacy());
		configureFluidDrugExecute.setChargeDept(drugOrderType.getDrugType()
				.getPharmacy());
		configureFluidDrugExecute.setState(OrderExecute.State_NeedSend);
		configureFluidDrugExecute
				.setChargeState(OrderExecute.ChargeState_NoCharge);
		configureFluidDrugExecute.setCostState(OrderExecute.CostState_NoCost);

		team.addOrderExecute(configureFluidDrugExecute);

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
			if (orderUseModeAssistMaterial.getChargeMode().equals(
					DrugUseModeAssistMaterial.everyOne)) {
				transportFluidExecute.addChargeItem(orderUseModeAssistMaterial
						.getAssistMaterial().getChargeItem());
			} else if (orderUseModeAssistMaterial.getChargeMode().equals(
					DrugUseModeAssistMaterial.onlyOne)) {
				if (order.getOrderExecutes().size() == 0) {
					transportFluidExecute
							.addChargeItem(orderUseModeAssistMaterial
									.getAssistMaterial().getChargeItem());
				}
			}
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
