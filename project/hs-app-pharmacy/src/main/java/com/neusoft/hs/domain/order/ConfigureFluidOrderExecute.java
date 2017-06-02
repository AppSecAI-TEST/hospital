package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.pharmacy.ConfigureFluidOrder;
import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("ConfigureFluid")
public class ConfigureFluidOrderExecute extends OrderExecute {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_id")
	private DrugType drugType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fluid_order_id")
	private ConfigureFluidOrder fluidOrder;

	public static int PlanDateAdvanceHours = 2;// 相对输液配液提前执行小时数

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {
		try {
			drugType.send(getCount());
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}
	}

	@Override
	protected void doCancel() throws OrderExecuteException {
		try {
			drugType.unSend(getCount());
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}
	}

	@Override
	public void fillPlanDate(Date planStartDate, Date planEndDate) {
		super.fillPlanDate(
				DateUtil.addHour(planStartDate, -PlanDateAdvanceHours),
				DateUtil.addHour(planEndDate, -PlanDateAdvanceHours));
	}

	public DrugType getDrugType() {
		return drugType;
	}

	public void setDrugType(DrugType drugType) {
		this.drugType = drugType;
	}

	public ConfigureFluidOrder getFluidOrder() {
		return fluidOrder;
	}

	public void setFluidOrder(ConfigureFluidOrder fluidOrder) {
		this.fluidOrder = fluidOrder;
	}
}
