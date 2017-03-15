//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitDomainService.java

package com.neusoft.hs.domain.visit;

import java.util.Date;
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
		// 修改原患者一次就诊为非最新
		Visit oldVisit = visitRepo.findByLastAndCardNumber(true,
				createVisitVO.getCardNumber());
		if (oldVisit != null) {
			oldVisit.setLast(false);
			oldVisit.save();
		}

		Visit visit = new Visit();

		visit.setCardNumber(createVisitVO.getCardNumber());
		visit.setName(createVisitVO.getName());
		visit.setCreateDate(DateUtil.getSysDate());
		visit.setState(createVisitVO.getState());
		visit.setDept(createVisitVO.getDept());
		visit.setRespDoctor(createVisitVO.getRespDoctor());
		visit.setLast(true);

		visit.setPatient(patient);

		visit.save();

		MedicalRecordClip medicalRecordClip = new MedicalRecordClip();
		medicalRecordClip.setVisit(visit);
		medicalRecordClip.setState(MedicalRecordClip.State_Writing);

		medicalRecordClip.save();

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(visit);
		visitLog.setType(VisitLog.Type_Create);
		visitLog.setOperator(createVisitVO.getOperator());
		visitLog.setCreateDate(DateUtil.getSysDate());

		visitLog.save();

		return visit;

	}

	/**
	 * @param user
	 * @param receiveVisitVO
	 * @throws HsException
	 * @roseuid 584E135F0389
	 */
	public void intoWard(ReceiveVisitVO receiveVisitVO, AbstractUser user)
			throws HsException {

		Visit visit = visitRepo.findOne(receiveVisitVO.getVisitId());
		if (visit == null) {
			throw new HsException("visitId=[" + receiveVisitVO.getVisitId()
					+ "]不存在");
		}

		visit.intoWard(receiveVisitVO, user);

		applicationContext.publishEvent(new VisitIntoWardedEvent(visit));

	}

	/**
	 * @roseuid 5852564401AC
	 */
	public void leaveWard() {

	}

	/**
	 * @param visitId
	 * @roseuid 584E03140020
	 */
	public Visit find(String visitId) {
		return visitRepo.findOne(visitId);
	}

	public Visit findLastVisit(String cardNumber) {
		return visitRepo.findByLastAndCardNumber(true, cardNumber);
	}

	public List<Visit> findByState(String state, Pageable pageable) {
		return visitRepo.findByState(state, pageable);
	}

	public List<Visit> findByStateAndDept(String state, Dept dept,
			Pageable pageable) {
		return visitRepo.findByStateAndDept(state, dept, pageable);
	}

	public List<Visit> listVisit(Dept respDept, Pageable pageable) {
		return visitRepo.findByDept(respDept, pageable);
	}

	public List<Visit> listInPatientVisit(Pageable pageable) {
		return visitRepo.findAll(pageable).getContent();
	}

	public void clear() {
		visitRepo.deleteAll();
	}

	/**
	 * 自动切换患者一次就诊状态
	 * 
	 * @return
	 */
	public int changeVisitState() {
		Date changeDate = DateUtil.reduceHour(DateUtil.getSysDate(), 10);
		return visitRepo.changeVisitState(Visit.State_LeaveHospital,
				Visit.State_Diagnosed_Executing, changeDate);
	}

}
