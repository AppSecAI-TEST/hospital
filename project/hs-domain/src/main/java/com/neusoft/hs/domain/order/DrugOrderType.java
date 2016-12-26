//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DrugOrderType.java

package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.domain.pharmacy.DrugTypeSpec;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("DrugOrderType")
public class DrugOrderType extends OrderType {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_id")
	private DrugType drugType;

	@Transient
	private String drugTypeSpecId;

	@Override
	public void check(Order order) throws OrderException {
		if (drugTypeSpecId == null) {
			throw new OrderException(order, "drugTypeSpecId不能为空");
		}

		DrugTypeSpec drugTypeSpec = this
				.getService(PharmacyDomainService.class).findDrugTypeSpec(
						drugTypeSpecId);

		if (drugTypeSpec == null) {
			throw new OrderException(order, "drugTypeSpecId=[" + drugTypeSpecId
					+ "]不存在");
		}

		List<DrugType> drugTypes = this.getService(PharmacyDomainService.class)
				.findByDrugTypeSpec(drugTypeSpec);

		L: for (DrugType drugType : drugTypes) {
			if (drugType.getStock() >= order.getCount()) {
				this.drugType = drugType;
				break L;
			}
		}
		if (this.drugType == null) {
			throw new OrderException(order, "drugTypeSpecId=[" + drugTypeSpecId
					+ "]库存不足");
		}

		try {
			this.drugType.withhold(order.getCount());
		} catch (HsException e) {
			throw new OrderException(order, e);
		}
		// 根据计算的药品类型找到合适的医嘱类型
		order.setType(this.drugType.getDrugOrderType());
	}

	@Override
	public List<OrderExecute> resolveOrder(Order order) {
		List<OrderExecute> orderExecutes = new ArrayList<OrderExecute>();
		OrderExecute orderExecute;
		Date sysDate;
		
		String teamId = UUID.randomUUID().toString();

		// 摆药执行条目
		orderExecute = new OrderExecute();
		orderExecute.setOrder(order);
		orderExecute.setVisit(order.getVisit());
		orderExecute.setBelongDept(order.getBelongDept());
		orderExecute.setType(OrderExecute.Type_Dispense_Drug);
		orderExecute.setTeamId(teamId);
		orderExecute.setTeamFirst(true);

		sysDate = DateUtil.getSysDate();
		orderExecute.setPlanStartDate(sysDate);
		orderExecute.setPlanEndDate(sysDate);

		orderExecute.setExecuteDept(drugType.getPharmacy());
		orderExecute.setState(OrderExecute.State_NeedSend);
		orderExecute.setChargeState(OrderExecute.ChargeState_NoCharge);
		orderExecute.setCostState(OrderExecute.CostState_NoCost);

		orderExecutes.add(orderExecute);

		// 取药执行条目
		orderExecute = new OrderExecute();
		orderExecute.setOrder(order);
		orderExecute.setVisit(order.getVisit());
		orderExecute.setBelongDept(order.getBelongDept());
		orderExecute.setType(OrderExecute.Type_Take_Drug);
		orderExecute.setTeamId(teamId);
		orderExecute.setTeamFirst(false);

		sysDate = DateUtil.getSysDate();
		orderExecute.setPlanStartDate(sysDate);
		orderExecute.setPlanEndDate(sysDate);

		orderExecute.setExecuteDept(order.getBelongDept());
		orderExecute.setState(OrderExecute.State_NeedExecute);
		orderExecute.setChargeState(OrderExecute.ChargeState_NoCharge);
		orderExecute.setCostState(OrderExecute.CostState_NoCost);

		orderExecutes.add(orderExecute);

		return orderExecutes;
	}

	public DrugType getDrugType() {
		return drugType;
	}

	public void setDrugType(DrugType drugType) {
		this.drugType = drugType;
	}

	public String getDrugTypeSpecId() {
		return drugTypeSpecId;
	}

	public void setDrugTypeSpecId(String drugTypeSpecId) {
		this.drugTypeSpecId = drugTypeSpecId;
	}

}
