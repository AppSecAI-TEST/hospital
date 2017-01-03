//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\inpatientdept\\OrderAppService.java

package com.neusoft.hs.application.inpatientdept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderDomainService;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderAppService {

	@Autowired
	private OrderDomainService orderDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	public static final int NeedSendOrderExecuteDay = 2;// 今天和明天

	/**
	 * @param doctor
	 * @param order
	 * @throws HsException
	 * @roseuid 584E5239011A
	 */
	public Order create(Order order, Doctor doctor) throws OrderException {
		return orderDomainService.create(order, doctor);
	}

	public List<Order> getNeedVerifyOrders(Nurse nurse, Pageable pageable) {
		return orderDomainService.getNeedVerifyOrders(nurse, pageable);
	}

	/**
	 * @param nurse
	 * @param orderId
	 * @throws HsException
	 * @roseuid 584F48660279
	 */
	public Order verify(String orderId, Nurse nurse) throws OrderException {
		return orderDomainService.verify(orderId, nurse);
	}

	/**
	 * @param doctor
	 * @param orderId
	 * @throws OrderException
	 * @roseuid 5850ADFE001C
	 */
	public void cancel(String orderId, Doctor doctor) throws OrderException {
		orderDomainService.cancel(orderId, doctor);
	}

	/**
	 * @roseuid 5850EE16024D
	 */
	public int resolve() {
		return orderDomainService.resolve();
	}

	public List<OrderExecute> getNeedSendOrderExecutes(Nurse nurse,
			Pageable pageable) {
		return orderExecuteDomainService.getNeedSendOrderExecutes(nurse,
				NeedSendOrderExecuteDay, pageable);
	}
}
