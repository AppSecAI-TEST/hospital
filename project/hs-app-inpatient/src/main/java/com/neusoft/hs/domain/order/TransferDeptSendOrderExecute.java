package com.neusoft.hs.domain.order;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.domain.visit.VisitException;

@Entity
@DiscriminatorValue("TransferDeptSend")
public class TransferDeptSendOrderExecute extends OrderExecute {

	
	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {
		Visit visit = this.getVisit();

		try {
			this.getService(VisitDomainService.class).transferDeptSend(
					visit, this.getOrder(), user);
		} catch (VisitException e) {
			e.printStackTrace();
			throw new OrderExecuteException(this, e);
		}
	}

}
