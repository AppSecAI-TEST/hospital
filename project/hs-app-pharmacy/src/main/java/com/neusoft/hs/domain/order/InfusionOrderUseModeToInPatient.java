package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.DrugUseModeAssistMaterial;
import com.neusoft.hs.domain.pharmacy.Pharmacy;

@Entity
@DiscriminatorValue("Infusion_InPatient")
public class InfusionOrderUseModeToInPatient extends DrugUseMode {

	public static final String transportFluid = "transportFluid";

	@Override
	public void resolve(Order order) {
		OrderExecuteTeam team = new OrderExecuteTeam();

		DrugOrderTypeApp drugOrderTypeApp = (DrugOrderTypeApp) order
				.getTypeApp();

		Pharmacy pharmacy = drugOrderTypeApp.getPharmacy();

		// 配液执行条目
		ConfigureFluidOrderExecute configureFluidDrugExecute = new ConfigureFluidOrderExecute();
		configureFluidDrugExecute.setOrder(order);
		configureFluidDrugExecute.setVisit(order.getVisit());
		configureFluidDrugExecute.setBelongDept(order.getBelongDept());
		configureFluidDrugExecute.setType(OrderExecute.Type_Configure_Fluid);
		configureFluidDrugExecute.addChargeItem(drugOrderTypeApp
				.getDrugTypeSpec().getChargeItem());
		configureFluidDrugExecute.setCount(order.getCount());
	
		configureFluidDrugExecute.setExecuteDept(pharmacy);
		configureFluidDrugExecute.setChargeDept(pharmacy);
		configureFluidDrugExecute.setState(OrderExecute.State_NeedSend);
		
		configureFluidDrugExecute.setPharmacy(pharmacy);
		configureFluidDrugExecute.setDrugTypeSpec(drugOrderTypeApp.getDrugTypeSpec());
		
		team.addOrderExecute(configureFluidDrugExecute);
		
		// 发药执行条目
		DistributeDrugOrderExecute distributeDrugExecute = new DistributeDrugOrderExecute();
		distributeDrugExecute.setOrder(order);
		distributeDrugExecute.setVisit(order.getVisit());
		distributeDrugExecute.setBelongDept(order.getBelongDept());
		distributeDrugExecute.setType(OrderExecute.Type_Distribute_Drug);
		distributeDrugExecute.setExecuteDept(pharmacy);
		distributeDrugExecute.setCount(order.getCount());
		distributeDrugExecute.setState(OrderExecute.State_NeedExecute);

		team.addOrderExecute(distributeDrugExecute);

		// 输液执行条目
		TransportFluidOrderExecute transportFluidExecute = new TransportFluidOrderExecute();
		transportFluidExecute.setOrder(order);
		transportFluidExecute.setVisit(order.getVisit());
		transportFluidExecute.setBelongDept(order.getBelongDept());
		transportFluidExecute.setType(OrderExecute.Type_Transport_Fluid);

		// 处理辅材产生的费用
		DrugUseModeAssistMaterial orderUseModeAssistMaterial = this
				.getTheOrderUseModeChargeItem(transportFluid);
		if (orderUseModeAssistMaterial != null) {
			if (drugOrderTypeApp.getDrugTypeSpec().isTransportFluidCharge()) {
				if (orderUseModeAssistMaterial.getChargeMode().equals(
						DrugUseModeAssistMaterial.everyOne)) {
					transportFluidExecute
							.addChargeItem(orderUseModeAssistMaterial
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
		}

		transportFluidExecute.setExecuteDept(order.getBelongDept());
		transportFluidExecute.setChargeDept(order.getBelongDept());
		transportFluidExecute.setState(OrderExecute.State_NeedExecute);
		
		transportFluidExecute.setPharmacy(pharmacy);
		transportFluidExecute.setDrugTypeSpec(drugOrderTypeApp.getDrugTypeSpec());

		team.addOrderExecute(transportFluidExecute);

		order.addExecuteTeam(team);
	}

}
