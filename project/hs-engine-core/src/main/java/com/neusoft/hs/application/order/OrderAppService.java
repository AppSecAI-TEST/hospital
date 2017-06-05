//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\inpatientdept\\OrderAppService.java

package com.neusoft.hs.application.order;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderDomainService;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderAppService {

	@Autowired
	private OrderDomainService orderDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	public static final int NeedSendOrderExecuteDay = 1;// 今天

	public static final int NeedSendOrderExecuteHour = 36;// 明天12：00之前的医嘱

	/**
	 * @param doctor
	 * @param order
	 * @throws OrderExecuteException
	 * @throws HsException
	 * @roseuid 584E5239011A
	 */
	public List<Order> create(OrderCreateCommand orderCommand, Doctor doctor)
			throws OrderException, OrderExecuteException {
		return orderDomainService.create(orderCommand, doctor);
	}

	public List<Order> getNeedVerifyOrders(Nurse nurse, Pageable pageable) {
		return orderDomainService.getNeedVerifyOrders(nurse, pageable);
	}

	/**
	 * @param nurse
	 * @param orderId
	 * @throws OrderExecuteException
	 * @throws HsException
	 * @roseuid 584F48660279
	 */
	public Order verify(String orderId, Nurse nurse) throws OrderException,
			OrderExecuteException {
		Order order = orderDomainService.find(orderId);
		if (order == null) {
			throw new OrderException(null, "orderId=[" + orderId + "]不存在");
		}
		return orderDomainService.verify(order, nurse);
	}

	/**
	 * @param doctor
	 * @param orderId
	 * @throws OrderException
	 * @roseuid 5850ADFE001C
	 */
	public void cancel(String orderId, Doctor doctor) throws OrderException {
		Order order = orderDomainService.find(orderId);
		if (order == null) {
			throw new OrderException(null, "orderId=[" + orderId + "]不存在");
		}
		orderDomainService.cancel(order, doctor);
	}

	public void delete(String orderId, Doctor doctor) throws OrderException {
		Order order = orderDomainService.find(orderId);
		if (order == null) {
			throw new OrderException(null, "orderId=[" + orderId + "]不存在");
		}
		orderDomainService.delete(order, doctor);
	}

	/**
	 * @roseuid 5850EE16024D
	 */
	public int resolve() {
		return orderDomainService.resolve();
	}

	public List<OrderExecute> getNeedSendOrderExecutes(Nurse nurse,
			Pageable pageable) {
		Date date = DateUtil.addHour(DateUtil.getSysDateStart(),
				NeedSendOrderExecuteHour);
		return orderExecuteDomainService.getNeedSendOrderExecutes(nurse, date,
				pageable);
	}

	public void stop(String orderId, Doctor doctor) throws OrderException {
		Order order = orderDomainService.find(orderId);
		if (order == null) {
			throw new OrderException(null, "orderId=[" + orderId + "]不存在");
		} else if (!(order instanceof LongOrder)) {
			throw new OrderException(order, "orderId=[" + orderId
					+ "]不是长嘱，不能停止");
		}
		orderDomainService.stop((LongOrder) order, doctor);

	}
}
