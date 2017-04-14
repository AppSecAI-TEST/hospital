package com.neusoft.hs.engine.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

@RestController
public class VisitFrontRestController {

	@Autowired
	private VisitFacade visitFacade;

	@RequestMapping(method = RequestMethod.POST, value = "/visit/leave/hospital")
	public void leaveHospital(@RequestBody LeaveHospitalDTO leaveHospitalDTO)
			throws HsException {
		visitFacade.leaveHospital(leaveHospitalDTO);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/visit/{visitId}/find")
	public Visit find(@PathVariable("visitId") String visitId) {
		return visitFacade.find(visitId);
	}

}
