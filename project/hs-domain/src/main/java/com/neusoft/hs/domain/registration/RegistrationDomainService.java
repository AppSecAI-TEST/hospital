//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\registration\\RegistrationDomainService.java

package com.neusoft.hs.domain.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.domain.outpatientoffice.VoucherException;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.OutPatientVisit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class RegistrationDomainService {

	@Autowired
	private VoucherRepo voucherRepo;

	@Autowired
	private VisitDomainService visitDomainService;

	public OutPatientVisit register(CreateVisitVO createVisitVO,
			OutPatientPlanRecord planRecord, AbstractUser user)
			throws VoucherException {

		Voucher voucher = new Voucher();

		createVisitVO.getVisit()
				.setState(OutPatientVisit.State_WaitingDiagnose);

		OutPatientVisit visit = (OutPatientVisit) visitDomainService
				.create(createVisitVO);

		try {
			visit.initAccount(0, user);
		} catch (HsException e) {
			throw new VoucherException(e);
		}

		voucher.setVisit(visit);

		planRecord.occupy(voucher);

		voucherRepo.save(voucher);

		return visit;
	}

	public void clearVoucher() {
		voucherRepo.deleteAll();
	}
}
