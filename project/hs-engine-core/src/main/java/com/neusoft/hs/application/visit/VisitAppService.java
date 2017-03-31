package com.neusoft.hs.application.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.domain.visit.VisitException;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitAppService {

	@Autowired
	private VisitDomainService visitDomainService;

	/**
	 * 门诊离院
	 * 
	 * @param visitId
	 * @param user
	 * @throws VisitException
	 */
	public void leaveHospital(String visitId, AbstractUser user)
			throws HsException {

		Visit visit = visitDomainService.find(visitId);
		if (visit == null) {
			throw new HsException("visitId=[" + visitId + "]不存在");
		}

		visitDomainService.leaveHospital(visit, user);

	}

}
