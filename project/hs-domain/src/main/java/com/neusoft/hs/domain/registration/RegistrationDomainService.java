//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\registration\\RegistrationDomainService.java

package com.neusoft.hs.domain.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanDomainService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.domain.outpatientoffice.VoucherException;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class RegistrationDomainService {

	@Autowired
	private VoucherRepo voucherRepo;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private OutPatientPlanDomainService outPatientPlanDomainService;

	/**
	 * 挂号
	 * 
	 * @param createVisitVO
	 * @param planRecordId
	 * @param user
	 * @return
	 * @throws VoucherException
	 */
	public Voucher register(CreateVisitVO createVisitVO, String planRecordId,
			AbstractUser user) throws VoucherException {

		OutPatientPlanRecord planRecord = outPatientPlanDomainService
				.findPlanRecord(planRecordId);
		if (planRecord == null) {
			throw new VoucherException("门诊医生排班记录[" + planRecordId + "]不存在");
		}

		Voucher voucher = new Voucher();
		voucher.setRepeatVisit(createVisitVO.isRepeatVisit());
		voucher.setCreateDate(DateUtil.getSysDate());

		createVisitVO.setState(Visit.State_WaitingDiagnose);
		createVisitVO.setDept(planRecord.getRoom().getDept());

		Visit visit = null;
		if (!voucher.getRepeatVisit()) {
			visit = visitDomainService.create(createVisitVO);
			try {
				ChargeBill chargeBill = visit.initAccount(0, user);
				chargeBill.save();
			} catch (HsException e) {
				throw new VoucherException(e);
			}
		} else {
			visit = visitDomainService.findLastVisit(createVisitVO
					.getCardNumber());
			if (visit == null) {
				throw new VoucherException("未发现号码为["
						+ createVisitVO.getCardNumber() + "]的就诊记录");
			}
		}

		voucher.setVisit(visit);

		visit.setState(Visit.State_WaitingDiagnose);
		visit.setVoucherDate(voucher.getCreateDate());
		visit.save();

		planRecord.occupy(voucher);

		if (!voucher.getRepeatVisit()) {
			visit.getChargeBill().save();
		}

		voucherRepo.save(voucher);

		LogUtil.log(this.getClass(), "用户[{}]为患者一次就诊[{}]挂号，号码是[{}], 诊室为[{}]",
				user.getId(), visit.getId(), voucher.getNumber(), planRecord
						.getRoom().getId());

		return voucher;
	}

	/**
	 * 重新排号
	 * 
	 * @param voucher
	 * @param planRecordId
	 * @param user
	 * @throws VoucherException
	 */
	public void repeatOccupy(Voucher voucher, String planRecordId,
			AbstractUser user) throws VoucherException {
		OutPatientPlanRecord planRecord = outPatientPlanDomainService
				.findPlanRecord(planRecordId);
		if (planRecord == null) {
			throw new VoucherException("门诊医生排班记录[" + planRecordId + "]不存在");
		}

		planRecord.repeatOccupy(voucher);

		voucherRepo.save(voucher);

		LogUtil.log(this.getClass(), "用户[{}]为患者一次就诊[{}]重新排号，号码是[{}], 诊室为[{}]",
				user.getId(), voucher.getVisit().getId(), voucher.getNumber(),
				planRecord.getRoom().getId());
	}

	public Voucher getTheVoucher(OutPatientPlanRecord record, Integer number) {
		return voucherRepo.findByPlanRecordAndNumber(record, number);
	}

	public void clearVoucher() {
		voucherRepo.deleteAll();
	}
}
