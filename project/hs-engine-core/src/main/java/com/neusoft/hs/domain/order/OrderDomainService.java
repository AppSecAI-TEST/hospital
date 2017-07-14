//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderDomainService.java

package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Admin;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderDomainService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private OrderExecuteRepo orderExecuteRepo;

	@Autowired
	private OrderExecuteTeamRepo orderExecuteTeamRepo;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 创建医嘱条目
	 * 
	 * @param doctor
	 * @param order
	 * @throws OrderExecuteException
	 * @throws OrderException
	 * @roseuid 584E526102FB
	 */
	public List<Order> create(OrderCreateCommand orderCommand, Doctor doctor)
			throws OrderException, OrderExecuteException {

		// 更新为数据库最新的患者一次就诊信息
		Visit visit = visitDomainService.find(orderCommand.getVisit().getId());
		orderCommand.setVisit(visit);

		for (Order order : orderCommand.getOrders()) {
			if (order.getCreateDate() == null) {
				order.setCreateDate(DateUtil.getSysDate());
			}
			order.setCreator(doctor);
			order.setBelongDept(orderCommand.getVisit().getDept());
			if (order.isInPatient()) {
				order.setState(Order.State_Created);
			} else {
				order.setState(Order.State_Executing);
			}
		}
		if (orderCommand.getCreateDate() == null) {
			orderCommand.setCreateDate(DateUtil.getSysDate());
		}
		if (orderCommand.getCreator() == null) {
			orderCommand.setCreator(doctor);
		}
		orderCommand.check();
		// 保存医嘱
		orderCommand.save();

		// 对于门诊开立的医嘱自动分解
		for (Order order : orderCommand.getOrders()) {
			if (!order.isInPatient()) {
				order.resolve();
			}
		}

		applicationContext.publishEvent(new OrderCreatedEvent(orderCommand));

		List<String> orderIds = new ArrayList<String>();
		List<String> orderTypes = new ArrayList<String>();
		for (Order order : orderCommand.getOrders()) {
			order.create();
			orderIds.add(order.getId());
			orderTypes.add(order.getOrderType().getId());
		}

		LogUtil.log(this.getClass(), "医生[{}]给患者一次就诊[{}]创建医嘱条目{},医嘱类型为{}",
				doctor.getId(), orderCommand.getVisit().getName(), orderIds,
				orderTypes);

		return orderCommand.getOrders();
	}

	/**
	 * 合并医嘱
	 * 
	 * @param compsiteOrder
	 * @param doctor
	 * @throws OrderException
	 */
	public void comsite(CompsiteOrder compsiteOrder, Doctor doctor)
			throws OrderException {

		if (compsiteOrder.getCreateDate() == null) {
			compsiteOrder.setCreateDate(DateUtil.getSysDate());
		}
		if (compsiteOrder.getCreator() == null) {
			compsiteOrder.setCreator(doctor);
		}

		compsiteOrder.checkSelf();

		compsiteOrder.save();

		List<String> orderIds = new ArrayList<String>();
		for (Order order : compsiteOrder.getOrders()) {
			orderIds.add(order.getId());
		}

		LogUtil.log(this.getClass(), "医生[{}]将患者一次就诊[{}]的医嘱条目合并为一条组合医嘱[{}]",
				doctor.getId(), compsiteOrder.getVisit().getName(), orderIds,
				compsiteOrder.getId());
	}

	/**
	 * 核对医嘱条目
	 * 
	 * @param nurse
	 * @param order
	 * @throws OrderExecuteException
	 * @throws HsException
	 * @roseuid 584F489E03D2
	 */
	public Order verify(Order order, AbstractUser nurse) throws OrderException,
			OrderExecuteException {

		order.verify();

		applicationContext.publishEvent(new OrderVerifyedEvent(order));

		LogUtil.log(this.getClass(), "护士[{}]核对患者一次就诊[[{}]的医嘱条目[{}],类型为[{}]",
				nurse.getId(), order.getVisit().getName(), order.getId(), order
						.getOrderType().getId());

		return order;
	}

	/**
	 * 分解全部待分解的医嘱条目
	 * 
	 * @roseuid 584F49010391
	 */
	public int resolve(Admin admin) {
		// 获得执行中的住院长嘱
		List<LongOrder> longOrders = orderRepo.findLongOrder(
				Order.State_Executing, Order.PlaceType_InPatient);
		int count = 0;
		for (LongOrder longOrder : longOrders) {
			try {
				count += longOrder.resolve();
			} catch (OrderException e) {
				e.printStackTrace();
			} catch (OrderExecuteException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	/**
	 * 作废医嘱条目
	 * 
	 * @param doctor
	 * @param orderId
	 * @throws OrderException
	 * @roseuid 5850AE8E022C
	 */
	public void cancel(Order order, Doctor doctor) throws OrderException {
		try {
			order.cancel(doctor);
		} catch (OrderExecuteException e) {
			throw new OrderException(order, e);
		}

		applicationContext.publishEvent(new OrderCanceledEvent(order));

		LogUtil.log(this.getClass(), "医生[{}]作废了患者一次就诊[[{}]的医嘱条目{},类型为[{}]",
				doctor.getId(), order.getVisit().getName(), order.getId(),
				order.getOrderType().getId());

	}

	/**
	 * 停止一个长期医嘱
	 * 
	 * @param order
	 * @param doctor
	 * @throws OrderException
	 */
	public void stop(LongOrder order, Doctor doctor) throws OrderException {
		try {
			order.stop();
		} catch (OrderExecuteException e) {
			throw new OrderException(order, e);
		}

		applicationContext.publishEvent(new OrderStopedEvent(order));

		LogUtil.log(this.getClass(), "医生[{}]停止了患者一次就诊[[{}]的医嘱条目{},类型为[{}]",
				doctor.getId(), order.getVisit().getName(), order.getId(),
				order.getOrderType().getId());
	}

	/**
	 * 删除医嘱条目
	 * 
	 * @param orderId
	 * @param doctor
	 * @throws OrderException
	 */
	public void delete(Order order, Doctor doctor) throws OrderException {

		order.delete();

		applicationContext.publishEvent(new OrderDeletedEvent(order));

		LogUtil.log(this.getClass(), "医生[{}]删除了核对患者一次就诊[[{}]的医嘱条目{},类型为[{}]",
				doctor.getId(), order.getVisit().getName(), order.getId(),
				order.getOrderType().getId());
	}

	/**
	 * @roseuid 585250700266
	 */
	public Order find(String orderId) {
		return orderRepo.findOne(orderId);
	}

	/**
	 * @roseuid 585250700266
	 */
	public Iterator<Order> find(List<String> orderIds) {
		return orderRepo.findAll(orderIds).iterator();
	}

	public List<Order> find(Visit visit, Pageable pageable) {
		return orderRepo.findByVisit(visit, pageable);
	}

	public List<Order> findByBelongDept(Dept dept, Pageable pageable) {
		return orderRepo.findByBelongDept(dept, pageable);
	}
	
	public List<Order> findByBelongDepts(List<Dept> depts, Pageable pageable) {
		return orderRepo.findByBelongDeptIn(depts, pageable);
	}

	/**
	 * 得到指定用户可核对的医嘱列表
	 * 
	 * @param user
	 * @param pageable
	 * @return
	 */
	public List<Order> getNeedVerifyOrders(AbstractUser user, Pageable pageable) {
		return orderRepo.findByStateAndBelongDeptIn(Order.State_Created,
				user.getOperationDepts(), pageable);
	}

}
