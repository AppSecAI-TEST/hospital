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

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderDomainService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private OrderTypeRepo orderTypeRepo;

	@Autowired
	private OrderUseModeRepo orderUseModeRepo;

	@Autowired
	private CompsiteOrderRepo compsiteOrderRepo;

	@Autowired
	private OrderFrequencyTypeRepo orderFrequencyTypeRepo;

	@Autowired
	private AssistMaterialRepo assistMaterialRepo;

	@Autowired
	private OrderUseModeAssistMaterialRepo orderUseModeAssistMaterialRepo;

	@Autowired
	private InspectItemRepo inspectItemRepo;

	@Autowired
	private PharmacyDomainService pharmacyDomainService;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * @param doctor
	 * @param order
	 * @throws HsException
	 * @roseuid 584E526102FB
	 */
	public List<Order> create(OrderCreateCommand orderCommand, Doctor doctor)
			throws OrderException {

		List<Order> orders = new ArrayList<Order>();

		for (Order order : orderCommand.getOrders()) {
			order.setCreateDate(DateUtil.getSysDate());
			order.setCreator(doctor);
			order.setBelongDept(doctor.getDept());
			order.setState(Order.State_Created);

			order.check();

			orders.add(order);
		}

		orderCommand.save();

		applicationContext.publishEvent(new OrderCreatedEvent(orderCommand));

		return orders;
	}

	public List<Order> getNeedVerifyOrders(Nurse nurse, Pageable pageable) {
		return orderRepo.findByStateAndBelongDept(Order.State_Created,
				nurse.getDept(), pageable);
	}

	/**
	 * @param nurse
	 * @param orderId
	 * @throws HsException
	 * @roseuid 584F489E03D2
	 */
	public Order verify(String orderId, Nurse nurse) throws OrderException {
		Order order = orderRepo.findOne(orderId);
		if (order == null) {
			throw new OrderException(null, "orderId=[" + orderId + "]不存在");
		}

		order.verify();

		applicationContext.publishEvent(new OrderVerifyedEvent(order));

		return order;
	}

	/**
	 * @roseuid 584F49010391
	 */
	public int resolve() {
		List<LongOrder> longOrders = orderRepo
				.findLongOrderByState(Order.State_Executing);
		int count = 0;
		for (LongOrder longOrder : longOrders) {
			try {
				count += longOrder.resolve();
			} catch (OrderException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	/**
	 * @param doctor
	 * @param orderId
	 * @throws OrderException
	 * @roseuid 5850AE8E022C
	 */
	public void cancel(String orderId, Doctor doctor) throws OrderException {
		Order order = orderRepo.findOne(orderId);
		if (order == null) {
			throw new OrderException(null, "orderId=[" + orderId + "]不存在");
		}

		try {
			order.cancel(doctor);
		} catch (OrderExecuteException e) {
			throw new OrderException(order, e);
		}

		applicationContext.publishEvent(new OrderCanceledEvent(order));

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

	public void createOrderTypes(List<OrderType> orderTypes) {
		orderTypeRepo.save(orderTypes);
	}

	public void createOrderUseModes(List<OrderUseMode> orderUseModes) {
		orderUseModeRepo.save(orderUseModes);
	}

	public void createOrderFrequencyTypes(
			List<OrderFrequencyType> orderFrequencyTypes) {
		orderFrequencyTypeRepo.save(orderFrequencyTypes);
	}

	public void clearOrderTypes() {
		orderTypeRepo.deleteAll();
	}

	public void clearCompsiteOrdes() {
		compsiteOrderRepo.deleteAll();
	}

	public void clearOrderUseModes() {
		orderUseModeRepo.deleteAll();
	}

	public void clearOrderFrequencyTypes() {
		orderFrequencyTypeRepo.deleteAll();
	}

	public void createAssistMaterials(List<AssistMaterial> assistMaterials) {
		assistMaterialRepo.save(assistMaterials);
	}

	public void createOrderUseModeAssistMaterials(
			List<OrderUseModeAssistMaterial> orderUseModeAssistMaterials) {
		orderUseModeAssistMaterialRepo.save(orderUseModeAssistMaterials);
	}

	public void createOrderUseModeAssistMaterial(
			OrderUseModeAssistMaterial orderUseModeAssistMaterial) {
		orderUseModeAssistMaterialRepo.save(orderUseModeAssistMaterial);
	}

	public void createInspectItems(List<InspectItem> inspectItems) {
		inspectItemRepo.save(inspectItems);
	}

	public void clearInspectItems() {
		inspectItemRepo.deleteAll();
	}

}
