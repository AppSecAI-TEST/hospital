package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.AbstractUser;

@Entity
@DiscriminatorValue("LeaveHospitalRegister")
public class LeaveHospitalRegisterOrderExecute extends OrderExecute {

	@Override
	protected void doFinish(AbstractUser user) throws OrderExecuteException {
		for (Order order : this.getVisit().getOrders()) {
			if (order.getState().equals(order.State_Executing)) {
				throw new OrderExecuteException(this, "医嘱[" + order.getName()
						+ "]状态处于执行中");
			}
		}
	}

}
