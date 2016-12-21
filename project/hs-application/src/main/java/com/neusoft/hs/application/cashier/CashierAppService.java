//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\cashier\\CashierAppService.java

package com.neusoft.hs.application.cashier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CashierAppService {

	@Autowired
	private CostDomainService costDomainService;

	/**
	 * @roseuid 58573EC603E5
	 */
	public CashierAppService() {

	}

	/**
	 * @throws HsException
	 * @roseuid 584DFCB00142
	 */
	public ChargeBill initAccount(String visitId, float balance,
			AbstractUser user) throws HsException {
		return costDomainService.createChargeBill(visitId, balance, user);
	}

	/**
	 * @roseuid 58533BBA0050
	 */
	public void closeAccount() {

	}
}
