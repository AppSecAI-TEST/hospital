//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\register\\RegisterAppService.java

package com.neusoft.hs.application.register;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.InPatientVisit;
import com.neusoft.hs.domain.visit.InPatientVisitDomainService;
import com.neusoft.hs.domain.visit.VisitDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class RegisterAppService {

	@Autowired
	private VisitDomainService visitDomainService;
	
	@Autowired
	private InPatientVisitDomainService inPatientVisitDomainService;

	/**
	 * @roseuid 584A697D031B
	 */
	public InPatientVisit register(CreateVisitVO createVisitVO) {
		return (InPatientVisit)visitDomainService.create(createVisitVO);
	}

	public List<InPatientVisit> listVisit(Pageable pageable) {
		return inPatientVisitDomainService.listInPatientVisit(pageable);
	}

	public List<RegisterCount> getRegisterCount() {
		// TODO Auto-generated method stub
		return null;
	}
}
