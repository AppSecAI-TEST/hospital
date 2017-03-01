//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\entity\\visit\\OutPatientVisit.java

package com.neusoft.hs.domain.visit;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@DiscriminatorValue("OutPatient")
public class OutPatientVisit extends Visit {

	/**
	 * @roseuid 58B66D1001E4
	 */
	public OutPatientVisit() {

	}

	@Override
	public ChargeBill initAccount(float balance, AbstractUser user)
			throws HsException {
		// TODO Auto-generated method stub
		return null;
	}
}
