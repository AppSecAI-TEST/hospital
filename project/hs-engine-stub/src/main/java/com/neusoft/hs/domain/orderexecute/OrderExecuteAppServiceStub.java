package com.neusoft.hs.domain.orderexecute;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.visit.Visit;
@Service
public class OrderExecuteAppServiceStub implements OrderExecuteAppService {

	@Override
	public void send(String executeId, Nurse nurse)
			throws OrderExecuteException {
		// TODO Auto-generated method stub

	}

	@Override
	public int start() throws OrderExecuteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<OrderExecute> getNeedExecuteOrderExecutes(AbstractUser user,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderExecute> getNeedExecuteOrderExecutes(Visit visit,
			String type, AbstractUser user, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void finish(String executeId, Map<String, Object> params,
			AbstractUser user) throws OrderExecuteException {
		// TODO Auto-generated method stub

	}

}
