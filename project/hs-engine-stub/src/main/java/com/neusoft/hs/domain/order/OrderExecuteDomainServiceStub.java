package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.visit.Visit;
@Service
public class OrderExecuteDomainServiceStub implements OrderExecuteDomainService {

	@Override
	public List<OrderExecute> getNeedSendOrderExecutes(Nurse nurse,
			Date planStartDate, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderExecute> getNeedExecuteOrderExecutes(AbstractUser user,
			Date planStartDate, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderExecute> getNeedExecuteOrderExecutes(Visit visit,
			String type, AbstractUser user, Date planStartDate,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderExecute> getNeedBackChargeOrderExecutes(Staff user,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(OrderExecute execute, Nurse nurse)
			throws OrderExecuteException {
		// TODO Auto-generated method stub

	}

	@Override
	public int start() throws OrderExecuteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void finish(OrderExecute execute, Map<String, Object> params,
			AbstractUser user) throws OrderExecuteException {
		// TODO Auto-generated method stub

	}

	@Override
	public OrderExecute find(String executeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
