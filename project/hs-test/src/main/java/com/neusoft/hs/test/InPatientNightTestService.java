package com.neusoft.hs.test;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.cost.CostException;
import com.neusoft.hs.domain.order.OrderExecuteException;

@Service
public class InPatientNightTestService extends DataIniter {

	public void calculate() throws OrderExecuteException, CostException {
		orderAppService.resolve();
		orderExecuteAppService.start();
		costDomainService.calculate();
	}

}
