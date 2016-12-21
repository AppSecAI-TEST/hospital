//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\inpatientdept\\InPatientAppService.java

package com.neusoft.hs.application.inpatientdept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.ReceiveVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class InPatientAppService {

	@Autowired
	private VisitDomainService visitDomainService;

	public List<Visit> getNeedReceive(Pageable pageable) {
		return visitDomainService.findByState(Visit.State_NeedIntoWard,
				pageable);
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
