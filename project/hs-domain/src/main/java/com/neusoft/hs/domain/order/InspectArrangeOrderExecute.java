package com.neusoft.hs.domain.order;

import java.util.Date;

import com.neusoft.hs.domain.organization.AbstractUser;

public class InspectArrangeOrderExecute extends OrderExecute {

	@Override
	protected void doFinish(AbstractUser user) throws OrderExecuteException {

		Date planExecuteDate = this.getOrder().getApply().getPlanExecuteDate();

		this.getNext().setPlanStartDate(planExecuteDate);
		this.getNext().setPlanEndDate(planExecuteDate);
	}

}
