package com.neusoft.hs.application.visit;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.VisitException;
import com.neusoft.hs.platform.exception.HsException;

public interface VisitAppService {
	
	/**
	 * 门诊离院
	 * 
	 * @param visitId
	 * @param user
	 * @throws VisitException
	 */
	public void leaveHospital(String visitId, AbstractUser user)
			throws HsException;

}
