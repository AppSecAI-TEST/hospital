package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("EnterHospital")
public class EnterHospitalOrderType extends OrderType {

	public static final String WardDept = "wardDept";

	public static final String WardArea = "wardArea";

	public static final String RespDoctor = "respDoctor";

	@Override
	protected void create(Order order) throws OrderException {

		Dept wardDept = (Dept) order.getParam(WardDept);
		if (wardDept == null) {
			throw new OrderException(null, "创建住院医嘱未指定住院科室");
		}

		Dept wardArea = (Dept) order.getParam(WardArea);
		if (wardArea == null) {
			throw new OrderException(null, "创建住院医嘱未指定住院病区");
		}

		Doctor respDoctor = (Doctor) order.getParam(RespDoctor);
		if (respDoctor == null) {
			throw new OrderException(null, "创建住院医嘱未指定责任医生");
		}

		Visit visit = order.getVisit();
		visit.setDept(wardDept);
		visit.setArea(wardArea);
		visit.setRespDoctor(respDoctor);
		visit.setState(Visit.State_WaitingEnterHospital);

		visit.save();
	}

	@Override
	public void resolveOrder(OrderTypeApp orderTypeApp) {

		Order order = orderTypeApp.getOrder();

		OrderExecuteTeam team = new OrderExecuteTeam();

		OrganizationAdminDomainService organizationAdminDomainService = this
				.getService(OrganizationAdminDomainService.class);

		Dept inPatientOfficeDept = organizationAdminDomainService
				.getInPatientOfficeDept(order.getExecuteDept());

		Dept inChargeDept = organizationAdminDomainService
				.getInChargeDept(order.getExecuteDept());

		// 入院登记执行条目
		EnterHospitalRegisterOrderExecute register = new EnterHospitalRegisterOrderExecute();
		register.setOrder(order);
		register.setVisit(order.getVisit());
		register.setBelongDept(order.getBelongDept());
		register.setType(OrderExecute.Type_Enter_Hospital_Register);

		register.setPlanStartDate(order.getPlanStartDate());
		register.setPlanEndDate(order.getPlanStartDate());

		register.setExecuteDept(inPatientOfficeDept);
		register.setState(OrderExecute.State_Executing);

		team.addOrderExecute(register);

		// 预存住院费执行条目
		EnterHospitalSupplyCostOrderExecute supply = new EnterHospitalSupplyCostOrderExecute();
		supply.setOrder(order);
		supply.setVisit(order.getVisit());
		supply.setBelongDept(order.getBelongDept());
		supply.setType(OrderExecute.Type_Enter_Hospital_SupplyCost);

		supply.setPlanStartDate(order.getPlanStartDate());
		supply.setPlanEndDate(order.getPlanStartDate());

		supply.setExecuteDept(inChargeDept);
		supply.setState(OrderExecute.State_NeedExecute);

		team.addOrderExecute(supply);

		// 接诊执行条目
		EnterHospitalIntoWardOrderExecute intoWard = new EnterHospitalIntoWardOrderExecute();
		intoWard.setOrder(order);
		intoWard.setVisit(order.getVisit());
		intoWard.setBelongDept(order.getBelongDept());
		intoWard.setType(OrderExecute.Type_Enter_Hospital_InWard);
		intoWard.setMain(true);

		intoWard.setPlanStartDate(order.getPlanStartDate());
		intoWard.setPlanEndDate(order.getPlanStartDate());

		intoWard.setExecuteDept(order.getExecuteDept());
		intoWard.setState(OrderExecute.State_NeedExecute);

		team.addOrderExecute(intoWard);

		order.addExecuteTeam(team);
	}

}
