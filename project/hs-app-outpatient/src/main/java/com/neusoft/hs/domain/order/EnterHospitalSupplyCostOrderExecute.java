package com.neusoft.hs.domain.order;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@DiscriminatorValue("EnterHospitalSupplyCost")
public class EnterHospitalSupplyCostOrderExecute extends OrderExecute {

	public final static String Balance = "Balance";

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {
		if (params == null) {
			throw new OrderExecuteException(this, "params没有设置参数");
		}
		Float balance = (Float) params.get(Balance);
		if (balance == null) {
			throw new OrderExecuteException(this, "没有向params.[%s]设置预存金额",
					Balance);
		}
		Visit visit = this.getVisit();
		try {
			this.getService(CostDomainService.class).addCost(visit, balance,
					user);
			visit.setState(Visit.State_NeedIntoWard);
		} catch (HsException e) {
			e.printStackTrace();
			throw new OrderExecuteException(this, e);
		}

	}

}
