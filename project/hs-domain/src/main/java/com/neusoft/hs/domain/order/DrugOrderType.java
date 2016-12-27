//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DrugOrderType.java

package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.Hibernate;

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

		Date sysDate;

		// 摆药执行条目
		DispensingDrugOrderExecute dispensingDrugExecute = new DispensingDrugOrderExecute();
		dispensingDrugExecute.setOrder(order);
		dispensingDrugExecute.setVisit(order.getVisit());
		dispensingDrugExecute.setBelongDept(order.getBelongDept());
		dispensingDrugExecute.setType(OrderExecute.Type_Dispense_Drug);

		dispensingDrugExecute.setPrice(((DrugOrderType) order.getType())
				.getDrugType().getDrugTypeSpec().getChargeItem().getPrice());
		dispensingDrugExecute.setCount(order.getCount());

		sysDate = DateUtil.getSysDate();
		dispensingDrugExecute.setPlanStartDate(sysDate);
		dispensingDrugExecute.setPlanEndDate(sysDate);

		dispensingDrugExecute.setExecuteDept(drugType.getPharmacy());
		dispensingDrugExecute.setState(OrderExecute.State_NeedSend);
		dispensingDrugExecute.setChargeState(OrderExecute.ChargeState_NoCharge);
		dispensingDrugExecute.setCostState(OrderExecute.CostState_NoCost);

		team.addOrderExecute(dispensingDrugExecute);

		// 取药执行条目
		OrderExecute taskDrugExecute = new OrderExecute();
		taskDrugExecute.setOrder(order);
		taskDrugExecute.setVisit(order.getVisit());
		taskDrugExecute.setBelongDept(order.getBelongDept());
		taskDrugExecute.setType(OrderExecute.Type_Take_Drug);

		sysDate = DateUtil.getSysDate();
		taskDrugExecute.setPlanStartDate(sysDate);
		taskDrugExecute.setPlanEndDate(sysDate);

		taskDrugExecute.setExecuteDept(order.getBelongDept());
		taskDrugExecute.setState(OrderExecute.State_NeedExecute);
		taskDrugExecute.setChargeState(OrderExecute.ChargeState_NoCharge);
		taskDrugExecute.setCostState(OrderExecute.CostState_NoCost);

		team.addOrderExecute(taskDrugExecute);

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
