//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitDomainService.java

package com.neusoft.hs.domain.visit;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitDomainService {
	@Autowired
	private VisitRepo visitRepo;

	@Autowired
	private VisitRepo visitLogRepo;

	/**
	 * @roseuid 584A6AAC03AB
	 */
	public Visit create(Visit visit, AbstractUser user) {

		visit.setState(Visit.State_NeedInitAccount);
		visit.save();

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(visit);
		visitLog.setType(VisitLog.Type_Create);
		visitLog.setOperator(user);
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

	public List<Visit> findByState(String state, Pageable pageable) {
		return visitLogRepo.findByState(state, pageable);
	}

	/**
	 * @roseuid 584E135F0389
	 */
	public void intoWard() {

	}

	/**
	 * @roseuid 5852564401AC
	 */
	public void leaveWard() {

	}

	public void clear() {
		visitRepo.deleteAll();
	}
}
