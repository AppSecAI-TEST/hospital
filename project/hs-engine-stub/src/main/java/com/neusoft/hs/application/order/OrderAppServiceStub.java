package com.neusoft.hs.application.order;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
@Service
public class OrderAppServiceStub implements OrderAppService {

	@Override
	public List<Order> create(OrderCreateCommand orderCommand, Doctor doctor)
			throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getNeedVerifyOrders(Nurse nurse, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order verify(String orderId, Nurse nurse) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancel(String orderId, Doctor doctor) throws OrderException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String orderId, Doctor doctor) throws OrderException {
		// TODO Auto-generated method stub

	}

	@Override
	public int resolve() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<OrderExecute> getNeedSendOrderExecutes(Nurse nurse,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
