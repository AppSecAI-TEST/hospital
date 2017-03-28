package com.neusoft.hs.domain.cost;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;

@Entity
@DiscriminatorValue("Charge")
public class ChargeOrderExecute extends OrderExecute {

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {
		super.doFinish(params, user);

		for (OrderExecute orderExecute : this.getTeamOrderExecutes()) {
			if (orderExecute.getChargeState().equals(
					OrderExecute.ChargeState_NoCharge)) {
				orderExecute.setChargeState(OrderExecute.ChargeState_Charge);
				orderExecute.save();
			}
		}
	}

}
