//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\CostDomainService.java

package com.neusoft.hs.domain.cost;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.domain.visit.VisitLog;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostDomainService {
	@Autowired
	private VisitDomainService visitDomainService;

	public List<Visit> getNeedInitAccount(Pageable pageable) {
		return visitDomainService.findByState(Visit.State_NeedInitAccount,
				pageable);
	}

	/**
	 * @throws HsException
	 * @roseuid 584DFD470092
	 */
	public ChargeBill createChargeBill(String visitId, float balance,
			AbstractUser user) throws HsException {
		Visit visit = visitDomainService.find(visitId);
		if (visit == null) {
			throw new HsException("visitId=[" + visitId + "]不存在");
		}

		return visit.initAccount(balance, user);
	}

	/**
	 * @roseuid 584E2630028C
	 */
	public void createVisitChargeItem() {

	}

	/**
	 * @roseuid 584FBC02036D
	 */
	public void charging() {

	}

	/**
	 * @roseuid 5850BD170158
	 */
	public void unCharging() {

	}

	/**
	 * @roseuid 58533C8201B8
	 */
	public void balance() {

	}

}
