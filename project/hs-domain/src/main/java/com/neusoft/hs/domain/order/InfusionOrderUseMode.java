package com.neusoft.hs.domain.order;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.pharmacy.DrugType;

@Entity
@DiscriminatorValue("Infusion")
public class InfusionOrderUseMode extends OrderUseMode {

	public static final String transportFluid = "transportFluid";

	@Override
	public List<OrderExecute> resolve(Order order, DrugOrderType drugOrderType) {
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
		transportFluidExecute.addChargeItem(this
				.getTheChargeItem(transportFluid));
		transportFluidExecute.setDrugType(drugType);

		transportFluidExecute.setExecuteDept(order.getBelongDept());
		transportFluidExecute.setState(OrderExecute.State_NeedExecute);
		transportFluidExecute.setChargeState(OrderExecute.ChargeState_NoCharge);
		transportFluidExecute.setCostState(OrderExecute.CostState_NoCost);

		team.addOrderExecute(transportFluidExecute);

		return team.getExecutes();
	}

}
