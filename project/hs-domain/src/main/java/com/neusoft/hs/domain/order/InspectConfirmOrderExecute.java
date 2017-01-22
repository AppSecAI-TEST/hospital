package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("InspectConfirm")
public class InspectConfirmOrderExecute extends OrderExecute {

	@Override
	protected void doFinish(AbstractUser user) throws OrderExecuteException {
		super.doFinish(user);
		this.getOrder().getApply().setExecuteDate(DateUtil.getSysDate());
	}

}
