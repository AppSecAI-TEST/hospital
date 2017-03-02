//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\inpatientdept\\InPatientAppService.java

package com.neusoft.hs.application.inpatientdept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.visit.InPatientVisit;
import com.neusoft.hs.domain.visit.InPatientVisitDomainService;
import com.neusoft.hs.domain.visit.ReceiveVisitVO;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class InPatientAppService {

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private InPatientVisitDomainService inPatientVisitDomainService;

	public List<InPatientVisit> getNeedReceiveVisits(Staff staff,
			Pageable pageable) {
		return inPatientVisitDomainService.findByStateAndRespDept(
				InPatientVisit.State_NeedIntoWard, staff.getDept(), pageable);
	}

	public List<InPatientVisit> InWardVisits(Dept dept, Pageable pageable) {
		return inPatientVisitDomainService.findByStateAndRespDept(
				InPatientVisit.State_IntoWard, dept, pageable);
	}

	/**
	 * @param user
	 * @param receiveVisitVO
	 * @throws HsException
	 * @roseuid 584E114B01A1
	 */
	public void receive(ReceiveVisitVO receiveVisitVO, AbstractUser user)
			throws HsException {
		inPatientVisitDomainService.intoWard(receiveVisitVO, user);
	}

	public List<InPatientVisit> listVisit(AbstractUser user, Pageable pageable) {
		return inPatientVisitDomainService.listVisit(user.getDept(), pageable);
	}

	/**
	 * @roseuid 58524F350096
	 */
	public void leave() {

	}

	/**
	 * @roseuid 58524FAE0056
	 */
	public void checkLeave() {

	}

}
