package com.neusoft.hs.application.visit;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.exception.HsException;
@Service
public class VisitAppServiceStub implements VisitAppService {

	@Override
	public void leaveHospital(String visitId, AbstractUser user)
			throws HsException {
		// TODO Auto-generated method stub

	}

}
