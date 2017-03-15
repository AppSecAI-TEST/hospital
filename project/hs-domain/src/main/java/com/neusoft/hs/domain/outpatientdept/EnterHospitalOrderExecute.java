package com.neusoft.hs.domain.outpatientdept;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("EnterHospital")
public class EnterHospitalOrderExecute extends OrderExecute {

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {
		Visit visit = this.getVisit();
		visit.setState(Visit.State_NeedInitAccount);
	}

}
