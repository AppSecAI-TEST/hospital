//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\inpatientdept\\OrderAppService.java

package com.neusoft.hs.application.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.CompsiteOrder;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderDomainService;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Admin;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderAppService {

	@Autowired
	private OrderDomainService orderDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

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

	public List<Order> create(OrderBuilder builder, Doctor doctor)
			throws OrderException, OrderExecuteException {
		OrderCreateCommand orderCreateCommand = builder.createCommand();
		return create(orderCreateCommand, doctor);
	}

	public void compsite(CompsiteOrder compsiteOrder, Doctor doctor)
			throws OrderException {
		orderDomainService.comsite(compsiteOrder, doctor);
	}

	public List<Order> getNeedVerifyOrders(AbstractUser nurse, Pageable pageable) {
		return orderDomainService.getNeedVerifyOrders(nurse, pageable);
	}

	/**
	 * @param nurse
	 * @param orderId
	 * @throws OrderExecuteException
	 * @throws HsException
	 * @roseuid 584F48660279
	 */
	public Order verify(String orderId, AbstractUser nurse)
			throws OrderException, OrderExecuteException {
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
			throw new OrderException(null, "orderId=[%s]不存在", orderId);
		}
		orderDomainService.cancel(order, doctor);
	}

	public void delete(String orderId, Doctor doctor) throws OrderException {
		Order order = orderDomainService.find(orderId);
		if (order == null) {
			throw new OrderException(null, "orderId=[%s]不存在", orderId);
		}
		orderDomainService.delete(order, doctor);
	}

	/**
	 * @roseuid 5850EE16024D
	 */
	public int resolve(Admin admin) {
		return orderDomainService.resolve(admin);
	}

	public void stop(String orderId, Doctor doctor) throws OrderException {
		Order order = orderDomainService.find(orderId);
		if (order == null) {
			throw new OrderException(null, "orderId=[%s]不存在", orderId);
		} else if (!(order instanceof LongOrder)) {
			throw new OrderException(order, "orderId=[%s]不是长嘱，不能停止", orderId);
		}
		orderDomainService.stop((LongOrder) order, doctor);

	}

	public List<Order> findByBelongDept(Dept dept, Pageable pageable) {
		return orderDomainService.findByBelongDept(dept, pageable);
	}

}
