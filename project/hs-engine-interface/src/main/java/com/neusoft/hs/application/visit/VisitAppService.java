package com.neusoft.hs.application.visit;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.VisitException;
import com.neusoft.hs.platform.exception.HsException;

@FeignClient("engine-service")
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
