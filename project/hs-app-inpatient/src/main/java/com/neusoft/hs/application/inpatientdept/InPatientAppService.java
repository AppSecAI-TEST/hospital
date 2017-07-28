//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\inpatientdept\\InPatientAppService.java

package com.neusoft.hs.application.inpatientdept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.medicalrecord.MedicalRecordAppService;
import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordAdminDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordBuilder;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordTypeBuilder;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
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

	@Autowired
	private MedicalRecordAdminDomainService medicalRecordAdminDomainService;

	@Autowired
	private CostDomainService costDomainService;

	@Autowired
	private OrganizationAdminDomainService organizationAdminDomainService;

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

		MedicalRecordType type = medicalRecordAdminDomainService
				.getMedicalRecordType(typeId);

		MedicalRecordBuilder builder = new MedicalRecordTypeBuilder(type, visit);

		MedicalRecord medicalRecord = medicalRecordAppService.create(builder,
				visit, type, user);

		medicalRecordAppService.save(medicalRecord);

		medicalRecordDomainService.fix(medicalRecord, user);
	}

	public void transfer(String visitId, AbstractUser user)
			throws MedicalRecordException, VisitException, UnitException {

		Dept dept = organizationAdminDomainService.getRecordRoomDept(user
				.getDept());
		if (dept == null) {
			throw new UnitException(user.getDept().getOrg(), "组织[%s]没有配置病案室",
					user.getDept().getOrg().getName());
		}
		Visit visit = visitDomainService.find(visitId);
		medicalRecordDomainService.transfer(visit.getMedicalRecordClip(), dept,
				user);
	}

	/**
	 * 住院科室退费
	 * 
	 * @param recordId
	 * @param user
	 * @throws HsException
	 */
	public void retreat(String recordId, boolean isBackCost, AbstractUser user)
			throws HsException {
		ChargeRecord record = costDomainService.findChargeRecord(recordId);
		if (record == null) {
			throw new HsException("id=[%s]的收费记录不存在", recordId);
		}
		if (!user.getOperationDepts().contains(record.getBelongDept())) {
			throw new HsException("收费记录id=[%s]的所属部门[%s]不在操作者可以操作的部门里",
					recordId, record.getBelongDept().getName(),
					Dept.getNames(user.getOperationDepts()));
		}

		costDomainService.retreat(record, isBackCost, user);
	}
}
