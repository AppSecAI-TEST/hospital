//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitDomainService.java

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
import com.neusoft.hs.domain.patient.Patient;
import com.neusoft.hs.domain.patient.PatientDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitDomainService {
	@Autowired
	private VisitRepo visitRepo;

	@Autowired
	private InPatientVisitRepo inPatientVisitRepo;

	@Autowired
	private VisitLogRepo visitLogRepo;

	@Autowired
	private PatientDomainService patientDomainService;

	@Autowired
	private ApplicationContext applicationContext;

	public Visit create(CreateVisitVO createVisitVO) {

		Patient patient = patientDomainService.findByCardNumber(createVisitVO
				.getCardNumber());
		if (patient == null) {
			patient = new Patient();
			patient.setCardNumber(createVisitVO.getCardNumber());
			patient.setCreateDate(DateUtil.getSysDate());
		}
		patient.setName(createVisitVO.getName());
		patient.setSex(createVisitVO.getSex());
		patient.setBirthday(createVisitVO.getBirthday());

		patient.save();

		Visit visit = createVisitVO.getVisit();

		visit.setName(createVisitVO.getName());
		visit.setCreateDate(DateUtil.getSysDate());

		visit.setPatient(patient);

		// visit.setState(Visit.State_NeedInitAccount);
		visit.save();

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(visit);
		visitLog.setType(VisitLog.Type_Create);
		visitLog.setOperator(createVisitVO.getOperator());
		visitLog.setCreateDate(DateUtil.getSysDate());

		visitLog.save();

		return visit;

	}

	/**
	 * @param visitId
	 * @roseuid 584E03140020
	 */
	public Visit find(String visitId) {
		return visitRepo.findOne(visitId);
	}

	public List<? extends Visit> findByState(String state, Pageable pageable) {
		return visitRepo.findByState(state, pageable);
	}

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

	public void clear() {
		visitRepo.deleteAll();
	}

	public List<InPatientVisit> listVisit(Dept respDept, Pageable pageable) {
		return inPatientVisitRepo.findByRespDept(respDept, pageable);
	}

	public List<InPatientVisit> listInPatientVisit(Pageable pageable) {
		return inPatientVisitRepo.findAll(pageable).getContent();
	}
}
