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
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderDomainServiceImpl implements OrderDomainService{

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private OrderTypeRepo orderTypeRepo;

	@Autowired
	private OrderTypeAppRepo orderTypeAppRepo;

	@Autowired
	private CompsiteOrderRepo compsiteOrderRepo;

	@Autowired
	private OrderFrequencyTypeRepo orderFrequencyTypeRepo;

	@Autowired
	private AssistMaterialRepo assistMaterialRepo;

	@Autowired
	private PrescriptionRepo prescriptionRepo;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 创建医嘱条目
	 * 
	 * @param doctor
	 * @param order
	 * @throws HsException
	 * @roseuid 584E526102FB
	 */
	public List<Order> create(OrderCreateCommand orderCommand, Doctor doctor)
			throws OrderException {

		List<Order> orders = new ArrayList<Order>();

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

			order.check();

			orders.add(order);
		}
		if (orderCommand.getCreateDate() == null) {
			orderCommand.setCreateDate(DateUtil.getSysDate());
		}
		if (orderCommand.getCreator() == null) {
			orderCommand.setCreator(doctor);
		}
		// 保存医嘱
		orderCommand.save();

		// 对于门诊开立的医嘱自动分解
		for (Order order : orderCommand.getOrders()) {
			if (!order.isInPatient()) {
				order.resolve();
				order.save();
			}
		}

		applicationContext.publishEvent(new OrderCreatedEvent(orderCommand));

		List<String> orderIds = new ArrayList<String>();
		List<String> orderTypes = new ArrayList<String>();
		for (Order order : orderCommand.getOrders()) {
			order.create();
			orderIds.add(order.getId());
			orderTypes.add(order.getTypeApp().getOrderType().getId());
		}

		LogUtil.log(this.getClass(), "医生[{}]给患者一次就诊[{}]创建医嘱条目{},医嘱类型为{}",
				doctor.getId(), orderCommand.getVisit().getName(), orderIds,
				orderTypes);

		return orders;
	}

	public List<Order> getNeedVerifyOrders(Nurse nurse, Pageable pageable) {
		return orderRepo.findByStateAndBelongDept(Order.State_Created,
				nurse.getDept(), pageable);
	}

	/**
	 * 核对医嘱条目
	 * 
	 * @param nurse
	 * @param order
	 * @throws HsException
	 * @roseuid 584F489E03D2
	 */
	public Order verify(Order order, Nurse nurse) throws OrderException {

		order.verify();

		applicationContext.publishEvent(new OrderVerifyedEvent(order));

		LogUtil.log(this.getClass(), "护士[{}]核对患者一次就诊[[{}]的医嘱条目[{}],类型为[{}]",
				nurse.getId(), order.getVisit().getName(), order.getId(), order
						.getTypeApp().getOrderType().getId());

		return order;
	}

	/**
	 * 分解全部待分解的医嘱条目
	 * 
	 * @roseuid 584F49010391
	 */
	public int resolve() {
		// 获得执行中的住院长嘱
		List<LongOrder> longOrders = orderRepo
				.findLongOrderByStateAndPlaceType(Order.State_Executing,
						Order.PlaceType_InPatient);
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

		LogUtil.log(this.getClass(), "医生[{}]作废了核对患者一次就诊[[{}]的医嘱条目{},类型为[{}]",
				doctor.getId(), order.getVisit().getName(), order.getId(),
				order.getTypeApp().getOrderType().getId());

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
				order.getTypeApp().getOrderType().getId());
	}

	public List<Prescription> findPrescriptions(Visit visit) {
		return prescriptionRepo.findByVisit(visit);
	}

	public Prescription findThePrescription(Order order) {
		return prescriptionRepo.findByOrdersIn(order);
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

	public void createOrderFrequencyTypes(
			List<OrderFrequencyType> orderFrequencyTypes) {
		orderFrequencyTypeRepo.save(orderFrequencyTypes);
	}

	public void clearOrders() {
		orderRepo.deleteAll();
	}

	public void clearOrderTypes() {
		orderTypeRepo.deleteAll();
	}

	public void clearOrderTypeApps() {
		orderTypeAppRepo.deleteAll();
	}

	public void clearCompsiteOrdes() {
		compsiteOrderRepo.deleteAll();
	}

	public void clearPrescriptions() {
		prescriptionRepo.deleteAll();
	}

	public void clearOrderFrequencyTypes() {
		orderFrequencyTypeRepo.deleteAll();
	}

	public void createAssistMaterials(List<AssistMaterial> assistMaterials) {
		assistMaterialRepo.save(assistMaterials);
	}
}
