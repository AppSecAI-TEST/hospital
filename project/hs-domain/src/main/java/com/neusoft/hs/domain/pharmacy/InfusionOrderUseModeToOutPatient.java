package com.neusoft.hs.domain.pharmacy;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.ChargeOrderExecute;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteTeam;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.util.DateUtil;

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
		Date sysDate = DateUtil.getSysDate();

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
		// 统一缴费
		chargeOrderExecute.setPlanStartDate(sysDate);
		chargeOrderExecute.setPlanEndDate(sysDate);

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
		// 统一摆药
		dispensingDrugExecute.setPlanStartDate(sysDate);
		dispensingDrugExecute.setPlanEndDate(sysDate);

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
		// 统一取药
		taskDrugExecute.setPlanStartDate(sysDate);
		taskDrugExecute.setPlanEndDate(sysDate);

		team.addOrderExecute(taskDrugExecute);

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
