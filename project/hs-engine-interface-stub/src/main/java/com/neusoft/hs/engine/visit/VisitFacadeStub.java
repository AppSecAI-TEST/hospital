package com.neusoft.hs.engine.visit;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neusoft.hs.platform.exception.HsException;

@FeignClient("engine-service")
public interface VisitFacadeStub extends VisitFacade {
	
	@RequestMapping(method = RequestMethod.POST, value = "/visit/leave/hospital")
	public void leaveHospital(@RequestBody LeaveHospitalDTO leaveHospitalDTO)
			throws HsException;

}
