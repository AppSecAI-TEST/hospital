//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\CostDomainService.java

package com.neusoft.hs.domain.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostDomainService {
	@Autowired
	private VisitDomainService visitDomainService;

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
		if (!Visit.State_NeedInitAccount.equals(visit.getState())) {
			throw new HsException("visit=[" + visit.getName() + "]的状态应为["
					+ Visit.State_NeedInitAccount + "]");
		}

		ChargeBill chargeBill = new ChargeBill();
		chargeBill.setBalance(balance);
		chargeBill.setState(ChargeBill.State_Normal);
		chargeBill.setVisit(visit);

		chargeBill.save();

		visit.setState(Visit.State_NeedIntoWard);
		visit.save();

		return chargeBill;

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
