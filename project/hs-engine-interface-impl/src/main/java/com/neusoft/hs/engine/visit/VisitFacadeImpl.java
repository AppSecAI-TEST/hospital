package com.neusoft.hs.engine.visit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.visit.VisitAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitFacadeImpl implements VisitFacade {

	@Autowired
	private VisitAppService visitAppService;

	@Autowired
	private UserAdminDomainService userAdminDomainService;

	@Override
	public void leaveHospital(LeaveHospitalDTO leaveHospitalDTO)
			throws HsException {
		AbstractUser user = userAdminDomainService.find(leaveHospitalDTO
				.getOperatorId());
		if (user == null) {
			throw new HsException("operatorId=["
					+ leaveHospitalDTO.getOperatorId() + "]不存在");
		}
		String visitId = leaveHospitalDTO.getVisitId();

		visitAppService.leaveHospital(visitId, user);
	}
}
