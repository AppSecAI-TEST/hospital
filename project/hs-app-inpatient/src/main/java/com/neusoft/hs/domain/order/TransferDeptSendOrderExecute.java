package com.neusoft.hs.domain.order;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.AbstractUser;

@Entity
@DiscriminatorValue("TransferDeptSend")
public class TransferDeptSendOrderExecute extends OrderExecute {

	
	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {

	}

}
