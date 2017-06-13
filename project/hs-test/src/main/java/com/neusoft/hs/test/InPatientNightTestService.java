package com.neusoft.hs.test;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.cost.CostException;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.Admin;

@Service
public class InPatientNightTestService extends DataIniter {

	public void calculate(Admin admin) throws OrderExecuteException, CostException {
		orderAppService.resolve(admin);
		orderExecuteAppService.start(admin);
		costDomainService.calculate(admin);
	}

}
