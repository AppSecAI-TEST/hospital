package com.neusoft.hs.domain.visit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.medicalrecord.MedicalRecordClip;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class InPatientVisitDomainService {

	@Autowired
	private InPatientVisitRepo inPatientVisitRepo;

	@Autowired
	private ApplicationContext applicationContext;

	public List<InPatientVisit> findByStateAndRespDept(String state, Dept dept,
			Pageable pageable) {
		return inPatientVisitRepo.findByStateAndRespDept(state, dept, pageable);
	}

	/**
	 * @param user
	 * @param receiveVisitVO
	 * @throws HsException
	 * @roseuid 584E135F0389
	 */
	public void intoWard(ReceiveVisitVO receiveVisitVO, AbstractUser user)
			throws HsException {

		InPatientVisit visit = inPatientVisitRepo.findOne(receiveVisitVO
				.getVisitId());
		if (visit == null) {
			throw new HsException("visitId=[" + receiveVisitVO.getVisitId()
					+ "]不存在");
		}

		visit.intoWard(receiveVisitVO, user);

		MedicalRecordClip medicalRecordClip = new MedicalRecordClip();
		medicalRecordClip.setVisit(visit);
		medicalRecordClip.setState(MedicalRecordClip.State_InWard);
		medicalRecordClip.save();

		applicationContext.publishEvent(new VisitIntoWardedEvent(visit));

	}

	/**
	 * @roseuid 5852564401AC
	 */
	public void leaveWard() {

	}

	public List<InPatientVisit> listVisit(Dept respDept, Pageable pageable) {
		return inPatientVisitRepo.findByRespDept(respDept, pageable);
	}

	public List<InPatientVisit> listInPatientVisit(Pageable pageable) {
		return inPatientVisitRepo.findAll(pageable).getContent();
	}

}
