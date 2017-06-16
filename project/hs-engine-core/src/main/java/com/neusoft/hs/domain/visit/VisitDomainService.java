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
import com.neusoft.hs.domain.organization.Admin;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.patient.Patient;
import com.neusoft.hs.domain.patient.PatientDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.log.LogUtil;
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

	/**
	 * 创建患者一次就诊
	 * 
	 * @param createVisitVO
	 * @return
	 */
	public Visit create(CreateVisitVO createVisitVO) {

		Patient patient = patientDomainService.findByCardNumber(createVisitVO
				.getCardNumber());
		boolean newPatient;
		if (patient == null) {
			patient = new Patient();
			patient.setCardNumber(createVisitVO.getCardNumber());
			patient.setCreateDate(DateUtil.getSysDate());
			newPatient = true;
		} else {
			newPatient = false;
		}
		patient.setName(createVisitVO.getName());
		patient.setSex(createVisitVO.getSex());
		patient.setBirthday(createVisitVO.getBirthday());

		patient.save();

		if (newPatient) {
			LogUtil.log(this.getClass(), "用户[{}]创建了患者[{}]", createVisitVO
					.getOperator().getId(), patient.getName());
		}

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
		visit.setArea(createVisitVO.getArea());
		visit.setRespDoctor(createVisitVO.getRespDoctor());
		visit.setLast(true);

		visit.setPatient(patient);

		visit.save();

		MedicalRecordClip medicalRecordClip = new MedicalRecordClip();
		medicalRecordClip.setVisit(visit);
		medicalRecordClip.setState(MedicalRecordClip.State_Writing);

		medicalRecordClip.save();

		LogUtil.log(this.getClass(), "用户[{}]创建了患者一次就诊[{}]的病历夹[{}]",
				createVisitVO.getOperator().getId(), visit.getName(),
				medicalRecordClip.getId());

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(visit);
		visitLog.calType(createVisitVO.isInPatient());
		visitLog.setOperator(createVisitVO.getOperator());
		visitLog.setCreateDate(DateUtil.getSysDate());

		visitLog.save();

		LogUtil.log(this.getClass(), "用户[{}]创建了患者一次就诊[{}]", createVisitVO
				.getOperator().getId(), visit.getName());

		return visit;

	}

	/**
	 * 门诊复诊
	 * 
	 * @param createVisitVO
	 * @return
	 */
	public Visit repeat(CreateVisitVO createVisitVO) throws VisitException {

		Visit visit = this.findLastVisit(createVisitVO.getCardNumber());
		if (visit == null) {
			throw new VisitException(null, "未发现号码为["
					+ createVisitVO.getCardNumber() + "]的就诊记录");
		}

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(visit);
		visitLog.setType(VisitLog.Type_Repeat);
		visitLog.setOperator(createVisitVO.getOperator());
		visitLog.setCreateDate(DateUtil.getSysDate());

		visitLog.save();

		return visit;
	}

	/**
	 * 进入病房
	 * 
	 * @param user
	 * @param receiveVisitVO
	 * @throws HsException
	 * @roseuid 584E135F0389
	 */
	public void intoWard(ReceiveVisitVO receiveVisitVO, AbstractUser user)
			throws HsException {

		Visit visit = visitRepo.findOne(receiveVisitVO.getVisit().getId());
		if (visit == null) {
			throw new HsException("visitId=["
					+ receiveVisitVO.getVisit().getId() + "]不存在");
		}

		visit.intoWard(receiveVisitVO, user);

		applicationContext.publishEvent(new VisitIntoWardedEvent(visit));

		LogUtil.log(this.getClass(), "用户[{}]将患者一次就诊[{}]登记到病房[{}],床位号[{}]",
				user.getId(), visit.getName(), visit.getDept().getId(),
				visit.getBed());
	}

	/**
	 * 门诊离院
	 * 
	 * @param visitId
	 * @param user
	 * @throws HsException
	 */
	public void leaveHospital(Visit visit, AbstractUser user)
			throws VisitException {

		visit.leaveHospital(user);

		applicationContext.publishEvent(new VisitLeaveHospitalEvent(visit));

		LogUtil.log(this.getClass(), "用户[{}]将患者一次就诊[{}]设置为离院状态", user.getId(),
				visit.getName());

	}

	/**
	 * @roseuid 5852564401AC
	 */
	private void leaveWard() {

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
	
	public List<Visit> findByStateAndArea(String state, Dept area,
			Pageable pageable) {
		return visitRepo.findByStateAndArea(state, area, pageable);
	}

	public List<Visit> listVisit(Dept respDept, Pageable pageable) {
		return visitRepo.findByDept(respDept, pageable);
	}

	public List<Visit> listInPatientVisit(Pageable pageable) {
		return visitRepo.findAll(pageable).getContent();
	}

	/**
	 * 自动切换患者一次就诊状态
	 * 
	 * @return
	 */
	public int changeVisitState(Admin admin) {
		Date changeDate = DateUtil.reduceHour(DateUtil.getSysDate(), 10);
		List<Visit> visits = visitRepo.findByStateAndVoucherDateLessThan(
				Visit.State_Diagnosed_Executing, changeDate);
		for (Visit visit : visits) {
			visit.setState(Visit.State_LeaveHospital);
			visit.save();

			VisitLog visitLog = new VisitLog();
			visitLog.setVisit(visit);
			visitLog.setType(VisitLog.Type_LeaveHospital);
			visitLog.setOperator(admin);
			visitLog.setCreateDate(DateUtil.getSysDate());

			visitLog.save();

		}

		LogUtil.log(this.getClass(), "系统修改患者状态由[{}]到[{}]{}个",
				Visit.State_Diagnosed_Executing, Visit.State_LeaveHospital,
				visits.size());

		return visits.size();
	}

}
