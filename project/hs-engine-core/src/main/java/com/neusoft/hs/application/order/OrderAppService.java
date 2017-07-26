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

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderAppService {

	@Autowired
	private OrderDomainService orderDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	/**
	 * 创建医嘱
	 * 
	 * @param doctor
	 * @param order
	 * @throws OrderExecuteException
	 * @throws OrderException
	 * @roseuid 584E5239011A
	 */
	public List<Order> create(OrderCreateCommand orderCommand, Doctor doctor)
			throws OrderException, OrderExecuteException {
		return orderDomainService.create(orderCommand, doctor);
	}

	/**
	 * 创建医嘱
	 * 
	 * @param builder
	 * @param doctor
	 * @return
	 * @throws OrderException
	 * @throws OrderExecuteException
	 */
	public List<Order> create(OrderBuilder builder, Doctor doctor)
			throws OrderException, OrderExecuteException {
		OrderCreateCommand orderCreateCommand = builder.createCommand();
		return create(orderCreateCommand, doctor);
	}

	/**
	 * 合并医嘱
	 * 
	 * @param compsiteOrder
	 * @param doctor
	 * @throws OrderException
	 */
	public void compsite(String orderId1, String orderId2, Doctor doctor)
			throws OrderException {

		Order order1 = orderDomainService.find(orderId1);
		if (order1 == null) {
			throw new OrderException(null, "orderId=[" + orderId1 + "]不存在");
		}
		if (order1.getCompsiteOrder() != null) {
			throw new OrderException(null, "orderId=[" + orderId1 + "]已经成为组合医嘱");
		}

		Order order2 = orderDomainService.find(orderId2);
		if (order2 == null) {
			throw new OrderException(null, "orderId=[" + orderId2 + "]不存在");
		}
		if (order2.getCompsiteOrder() != null) {
			throw new OrderException(null, "orderId=[" + orderId2 + "]已经成为组合医嘱");
		}

		CompsiteOrder compsiteOrder = new CompsiteOrder();
		compsiteOrder.addOrder(order1);
		compsiteOrder.addOrder(order2);

		orderDomainService.comsite(compsiteOrder, doctor);
	}

	/**
	 * 核对医嘱
	 * 
	 * @param nurse
	 * @param orderId
	 * @throws OrderExecuteException
	 * @throws OrderException
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
	 * 取消医嘱
	 * 
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

	/**
	 * 删除医嘱
	 * 
	 * @param orderId
	 * @param doctor
	 * @throws OrderException
	 */
	public void delete(String orderId, Doctor doctor) throws OrderException {
		Order order = orderDomainService.find(orderId);
		if (order == null) {
			throw new OrderException(null, "orderId=[%s]不存在", orderId);
		}
		orderDomainService.delete(order, doctor);
	}

	/**
	 * 分解需要分解的医嘱集合
	 * 
	 * @roseuid 5850EE16024D
	 */
	public int resolve(Admin admin) {
		return orderDomainService.resolve(admin);
	}

	/**
	 * 停止医嘱
	 * 
	 * @param orderId
	 * @param doctor
	 * @throws OrderException
	 */
	public void stop(String orderId, Doctor doctor) throws OrderException {
		Order order = orderDomainService.find(orderId);
		if (order == null) {
			throw new OrderException(null, "orderId=[%s]不存在", orderId);
		} else if (!(order instanceof LongOrder)) {
			throw new OrderException(order, "orderId=[%s]不是长嘱，不能停止", orderId);
		}
		orderDomainService.stop((LongOrder) order, doctor);

	}

	/**
	 * 得到需核对的医嘱列表
	 * 
	 * @param nurse
	 * @param pageable
	 * @return
	 */
	public List<Order> getNeedVerifyOrders(AbstractUser nurse, Pageable pageable) {
		return orderDomainService.getNeedVerifyOrders(nurse, pageable);
	}

	/**
	 * 得到指定科室创建的医嘱列表
	 * 
	 * @param dept
	 * @param pageable
	 * @return
	 */
	public List<Order> findByBelongDept(Dept dept, Pageable pageable) {
		return orderDomainService.findByBelongDept(dept, pageable);
	}

	/**
	 * 得到指定科室创建的医嘱列表
	 * 
	 * @param dept
	 * @param pageable
	 * @return
	 */
	public List<Order> findByBelongDepts(List<Dept> depts, Pageable pageable) {
		return orderDomainService.findByBelongDepts(depts, pageable);
	}

}
