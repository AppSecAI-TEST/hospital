package com.neusoft.hs.application.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.outpatientoffice.VoucherException;
import com.neusoft.hs.domain.registration.RegistrationDomainService;
import com.neusoft.hs.domain.registration.Voucher;
import com.neusoft.hs.domain.visit.CreateVisitVO;

@Service
@Transactional(rollbackFor = Exception.class)
public class RegistrationAppService {

	@Autowired
	private RegistrationDomainService registrationDomainService;

	public Voucher register(CreateVisitVO createVisitVO, String planRecordId,
			AbstractUser user) throws VoucherException {
		return registrationDomainService.register(createVisitVO, planRecordId,
				user);
	}

	public void repeatOccupy(Voucher voucher, String planRecordId,
			AbstractUser user) throws VoucherException {
		registrationDomainService.repeatOccupy(voucher, planRecordId, user);
	}
}
