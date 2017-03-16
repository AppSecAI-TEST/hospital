package com.neusoft.hs.domain.inpatientdept;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("LeaveHospitalBalance")
public class LeaveHospitalBalanceOrderExecute extends OrderExecute {

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {
		super.doFinish(params, user);

		Visit visit = this.getVisit();
		try {
			visit.balance(user);
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}

		Order order = this.getOrder();
		if (order instanceof TemporaryOrder) {
			TemporaryOrder temporaryOrder = (TemporaryOrder) order;
			temporaryOrder.setExecuteDate(DateUtil.getSysDate());
			temporaryOrder.setExecuteUser(user);
		}
	}
}
