package com.neusoft.hs.domain.order;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.ChargeOrderExecute;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.DrugUseModeAssistMaterial;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("Infusion_OutPatient")
public class InfusionOrderUseModeToOutPatient extends DrugUseMode {

	public static final String transportFluid = "transportFluid";

	@Override
	public void resolve(Order order) {
		OrderExecuteTeam team = new OrderExecuteTeam();
		
		DrugOrderTypeApp drugOrderTypeApp = (DrugOrderTypeApp) order
				.getTypeApp();

		DrugType drugType = drugOrderTypeApp.getDrugType();
		ChargeItem chargeItem = drugType.getDrugTypeSpec().getChargeItem();
		Visit visit = order.getVisit();
		Pharmacy pharmacy = drugOrderTypeApp.getDrugType().getPharmacy();
		Date sysDate = DateUtil.getSysDate();
		Dept chargeDept = visit.getDept().getOrg().getOutChargeDept();

		ChargeItem assistMaterialChargeItem = null;

		// 收药品费执行条目
		ChargeOrderExecute chargeOrderExecute = new ChargeOrderExecute();
		chargeOrderExecute.setOrder(order);
		chargeOrderExecute.setVisit(visit);
		chargeOrderExecute.setBelongDept(order.getBelongDept());
		chargeOrderExecute.setType(OrderExecute.Type_Change);

		chargeOrderExecute.setExecuteDept(chargeDept);
		chargeOrderExecute.setChargeDept(pharmacy);
		chargeOrderExecute.setState(OrderExecute.State_Executing);
		// 统一缴费
		chargeOrderExecute.setPlanStartDate(sysDate);
		chargeOrderExecute.setPlanEndDate(sysDate);

		team.addOrderExecute(chargeOrderExecute);

		// 收辅材费执行条目
		ChargeOrderExecute assistMaterialChargeOrderExecute = null;

		DrugUseModeAssistMaterial orderUseModeAssistMaterial = this
				.getTheOrderUseModeChargeItem(transportFluid);
		if (orderUseModeAssistMaterial != null) {
			// 判断在指定药品规格上绑定材料费进行收费
			if (drugType.getDrugTypeSpec().isTransportFluidCharge()) {
				assistMaterialChargeOrderExecute = new ChargeOrderExecute();
				assistMaterialChargeOrderExecute.setOrder(order);
				assistMaterialChargeOrderExecute.setVisit(visit);
				assistMaterialChargeOrderExecute.setBelongDept(order
						.getBelongDept());
				assistMaterialChargeOrderExecute
						.setType(OrderExecute.Type_Change);
				// 记录辅材收费项目
				assistMaterialChargeItem = orderUseModeAssistMaterial
						.getAssistMaterial().getChargeItem();
				assistMaterialChargeOrderExecute.setExecuteDept(chargeDept);
				assistMaterialChargeOrderExecute.setChargeDept(pharmacy);
				assistMaterialChargeOrderExecute
						.setState(OrderExecute.State_Executing);
				// 统一缴费
				assistMaterialChargeOrderExecute.setPlanStartDate(sysDate);
				assistMaterialChargeOrderExecute.setPlanEndDate(sysDate);

				team.addOrderExecute(assistMaterialChargeOrderExecute);
			}
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

		dispensingDrugExecute.setState(OrderExecute.State_NeedExecute);
		// 统一摆药
		dispensingDrugExecute.setPlanStartDate(sysDate);
		dispensingDrugExecute.setPlanEndDate(sysDate);

		team.addOrderExecute(dispensingDrugExecute);
		chargeOrderExecute.setCharge(dispensingDrugExecute);

		// 取药执行条目
		DistributeDrugOrderExecute distributeDrugExecute = new DistributeDrugOrderExecute();
		distributeDrugExecute.setOrder(order);
		distributeDrugExecute.setVisit(visit);
		distributeDrugExecute.setBelongDept(order.getBelongDept());
		distributeDrugExecute.setType(OrderExecute.Type_Distribute_Drug);
		distributeDrugExecute.setExecuteDept(pharmacy);
		distributeDrugExecute.setState(OrderExecute.State_NeedExecute);

		// 统一发药
		distributeDrugExecute.setPlanStartDate(sysDate);
		distributeDrugExecute.setPlanEndDate(sysDate);

		team.addOrderExecute(distributeDrugExecute);

		// 输液执行条目
		TransportFluidOrderExecute transportFluidExecute = new TransportFluidOrderExecute();
		transportFluidExecute.setOrder(order);
		transportFluidExecute.setVisit(order.getVisit());
		transportFluidExecute.setBelongDept(order.getBelongDept());
		transportFluidExecute.setType(OrderExecute.Type_Transport_Fluid);
		transportFluidExecute.setDrugType(drugType);
		if (assistMaterialChargeItem != null) {
			transportFluidExecute.addChargeItem(assistMaterialChargeItem);
		}
		transportFluidExecute.setExecuteDept(order.getExecuteDept());
		transportFluidExecute.setChargeDept(order.getBelongDept());
		transportFluidExecute.setState(OrderExecute.State_NeedExecute);

		transportFluidExecute.setMain(true);

		team.addOrderExecute(transportFluidExecute);
		if (assistMaterialChargeOrderExecute != null) {
			assistMaterialChargeOrderExecute.setCharge(transportFluidExecute);
		}

		order.addExecuteTeam(team);
	}
}
