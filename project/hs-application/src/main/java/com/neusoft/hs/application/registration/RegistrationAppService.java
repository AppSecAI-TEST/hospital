package com.neusoft.hs.application.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.domain.outpatientoffice.VoucherException;
import com.neusoft.hs.domain.registration.RegistrationDomainService;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.Visit;

@Service
@Transactional(rollbackFor = Exception.class)
public class RegistrationAppService {

	@Autowired
	private RegistrationDomainService registrationDomainService;

	public Visit register(CreateVisitVO createVisitVO,
			OutPatientPlanRecord planRecord, AbstractUser user)
			throws VoucherException {
		return registrationDomainService.register(createVisitVO, planRecord,
				user);
	}
}
