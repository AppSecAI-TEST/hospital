//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\cashier\\CashierAppService.java

package com.neusoft.hs.application.cashier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CashierAppService {

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private CostDomainService costDomainService;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	public List<Visit> getNeedInitAccountVisits(Pageable pageable) {
		return costDomainService.getNeedInitAccount(pageable);
	}

	public List<OrderExecute> getNeedChageExecutes(Visit visit,
			AbstractUser user, Pageable pageable) {
		return orderExecuteAppService.getNeedExecuteOrderExecutes(visit,
				OrderExecute.Type_Change, user, pageable);
	}

	/**
	 * @throws HsException
	 * @roseuid 584DFCB00142
	 */
	public ChargeBill initAccount(String visitId, float balance,
			AbstractUser user) throws HsException {
		Visit visit = visitDomainService.find(visitId);
		if (visit == null) {
			throw new HsException("visitId=[" + visitId + "]不存在");
		}
		ChargeBill chargeBill = costDomainService.createChargeBill(visit,
				balance, user);
		visit.setState(Visit.State_NeedIntoWard);
		return chargeBill;
	}

	public ChargeRecord addCost(String visitId, float balance, AbstractUser user)
			throws HsException {
		Visit visit = visitDomainService.find(visitId);
		if (visit == null) {
			throw new HsException("visitId=[" + visitId + "]不存在");
		}
		ChargeRecord chargeRecord = costDomainService.addCost(visit, balance,
				user);
		visit.setState(Visit.State_NeedIntoWard);
		return chargeRecord;
	}

	/**
	 * @roseuid 58533BBA0050
	 */
	public void closeAccount() {

	}
}
