package com.neusoft.hs.application.cost;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.Nurse;
@Service
public class CostAppServiceStub implements CostAppService {

	@Override
	public void unCharging(String executeId, boolean isBackCost, Nurse nurse)
			throws OrderExecuteException {
		// TODO Auto-generated method stub

	}

}
