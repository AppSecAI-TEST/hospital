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
	public OrderExecuteTeam resolveOrder(Order order) {
		OrderExecuteTeam team = new OrderExecuteTeam();
		OrderExecute execute;
		Date sysDate;

		// 摆药执行条目
		execute = new OrderExecute();
		execute.setOrder(order);
		execute.setVisit(order.getVisit());
		execute.setBelongDept(order.getBelongDept());
		execute.setType(OrderExecute.Type_Dispense_Drug);

		sysDate = DateUtil.getSysDate();
		execute.setPlanStartDate(sysDate);
		execute.setPlanEndDate(sysDate);

		execute.setExecuteDept(drugType.getPharmacy());
		execute.setState(OrderExecute.State_NeedSend);
		execute.setChargeState(OrderExecute.ChargeState_NoCharge);
		execute.setCostState(OrderExecute.CostState_NoCost);

		team.addOrderExecute(execute);

		// 取药执行条目
		execute = new OrderExecute();
		execute.setOrder(order);
		execute.setVisit(order.getVisit());
		execute.setBelongDept(order.getBelongDept());
		execute.setType(OrderExecute.Type_Take_Drug);

		sysDate = DateUtil.getSysDate();
		execute.setPlanStartDate(sysDate);
		execute.setPlanEndDate(sysDate);

		execute.setExecuteDept(order.getBelongDept());
		execute.setState(OrderExecute.State_NeedExecute);
		execute.setChargeState(OrderExecute.ChargeState_NoCharge);
		execute.setCostState(OrderExecute.CostState_NoCost);

		team.addOrderExecute(execute);

		return team;
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
