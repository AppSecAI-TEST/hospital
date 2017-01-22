package com.neusoft.hs.domain.order;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.AbstractUser;

@Entity
@DiscriminatorValue("InspectArrange")
public class InspectArrangeOrderExecute extends OrderExecute {

	@Override
	protected void doFinish(AbstractUser user) throws OrderExecuteException {

		Date planExecuteDate = this.getOrder().getApply().getPlanExecuteDate();

		OrderExecute next = this.getNext();
		next.setPlanStartDate(planExecuteDate);
		next.setPlanEndDate(planExecuteDate);

		next.save();
	}

}
