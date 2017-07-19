package com.neusoft.hs.portal.businessview.visit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.application.outpatientdept.OutPatientDeptAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.outpatientoffice.OutPatientRoom;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class VisitBusinessView {

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private OutPatientDeptAppService outPatientDeptAppService;

	/**
	 * 得到指定医生可实施诊疗行为的患者列表
	 * 
	 * @param doctor
	 * @param pageable
	 * @return
	 */
	public List<Visit> findVisits(AbstractUser doctor, Pageable pageable) {

		List<Visit> visits = new ArrayList<Visit>();

		// 获取医生住院患者列表
		List<Visit> patientVisits = visitDomainService.findByStateAndDept(
				Visit.State_IntoWard, doctor.getDept(), pageable);
		visits.addAll(patientVisits);

		// 获取医生门诊患者列表
		OutPatientRoom outPatientRoom = outPatientDeptAppService
				.findOutPatientRoom(doctor, DateUtil.getSysDate());
		if (outPatientRoom != null) {
			List<Visit> outPatientVisits = visitDomainService
					.findByStateAndDept(Visit.State_Diagnosing, outPatientRoom,
							pageable);
			visits.addAll(outPatientVisits);
		}

		return visits;
	}
}
