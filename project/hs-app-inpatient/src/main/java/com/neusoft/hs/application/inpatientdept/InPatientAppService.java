//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\inpatientdept\\InPatientAppService.java

package com.neusoft.hs.application.inpatientdept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.medicalrecord.MedicalRecordAppService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.UnitException;
import com.neusoft.hs.domain.visit.ReceiveVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.domain.visit.VisitException;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class InPatientAppService {

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private MedicalRecordAppService medicalRecordAppService;

	@Autowired
	private MedicalRecordDomainService medicalRecordDomainService;

	public List<Visit> getNeedReceiveVisits(AbstractUser user, Pageable pageable) {
		return visitDomainService.findByStateAndArea(Visit.State_NeedIntoWard,
				user.getDept(), pageable);
	}

	public List<Visit> InWardVisits(Dept dept, Pageable pageable) {
		return visitDomainService.findByStateAndDept(Visit.State_IntoWard,
				dept, pageable);
	}

	/**
	 * @param user
	 * @param receiveVisitVO
	 * @throws HsException
	 * @roseuid 584E114B01A1
	 */
	public void receive(ReceiveVisitVO receiveVisitVO, AbstractUser user)
			throws HsException {
		visitDomainService.intoWard(receiveVisitVO, user);
	}

	public List<Visit> listVisit(AbstractUser user, Pageable pageable) {
		return visitDomainService.findByStateAndDepts(Visit.State_IntoWard,
				user.getOperationDepts(), pageable);
	}

	public void arrangementMedicalRecord(String typeId, Visit visit,
			AbstractUser user) throws HsException {
		MedicalRecord medicalRecord = medicalRecordAppService
				.createMedicalRecord(typeId, visit, user);

		medicalRecordDomainService.fix(medicalRecord, user);
	}

	public void transfer(String visitId, AbstractUser user)
			throws MedicalRecordException, VisitException, UnitException {

		Dept dept = user.getDept().getOrg().getRecordRoomDept();
		if (dept == null) {
			throw new UnitException(user.getDept().getOrg(), "组织[%s]没有配置病案室",
					user.getDept().getOrg().getName());
		}
		Visit visit = visitDomainService.find(visitId);
		medicalRecordDomainService.transfer(visit, dept, user);
	}
}
