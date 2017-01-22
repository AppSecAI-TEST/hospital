package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@DiscriminatorValue("LeaveHospitalRegister")
public class LeaveHospitalRegisterOrderExecute extends OrderExecute {

	@Override
	protected void doFinish(AbstractUser user) throws OrderExecuteException {

		Visit visit = this.getVisit();

		for (Order order : visit.getOrders()) {
			if (!this.getOrder().getId().equals(order.getId())) {
				if (order.getState().equals(order.State_Executing)) {
					if (order instanceof LongOrder) {
						((LongOrder) order).stop();
					} else {
						throw new OrderExecuteException(this, "医嘱["
								+ order.getName() + "]状态处于执行中，不能办理出院登记");
					}
				}
			}
		}
		try {
			visit.leaveWard(user);
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}
	}

}
