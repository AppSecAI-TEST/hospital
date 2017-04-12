package com.neusoft.hs.engine.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.hs.platform.exception.HsException;

@RestController
public class VisitRestController {

	@Autowired
	private VisitFacade visitFacade;

	@RequestMapping(method = RequestMethod.POST, value = "/visit/leave/hospital")
	public void leaveHospital(@RequestBody LeaveHospitalDTO leaveHospitalDTO)
			throws HsException {
		visitFacade.leaveHospital(leaveHospitalDTO);
	}

}
