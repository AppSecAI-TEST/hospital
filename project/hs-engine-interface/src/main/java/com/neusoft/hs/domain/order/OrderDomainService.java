//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderDomainService.java

package com.neusoft.hs.domain.order;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

public interface OrderDomainService {

	/**
	 * 创建医嘱条目
	 * 
	 * @param doctor
	 * @param order
	 * @throws HsException
	 * @roseuid 584E526102FB
	 */
	public List<Order> create(OrderCreateCommand orderCommand, Doctor doctor)
			throws OrderException;

	public List<Order> getNeedVerifyOrders(Nurse nurse, Pageable pageable);

	/**
	 * 核对医嘱条目
	 * 
	 * @param nurse
	 * @param order
	 * @throws HsException
	 * @roseuid 584F489E03D2
	 */
	public Order verify(Order order, Nurse nurse) throws OrderException;

	/**
	 * 分解全部待分解的医嘱条目
	 * 
	 * @roseuid 584F49010391
	 */
	public int resolve();

	/**
	 * 作废医嘱条目
	 * 
	 * @param doctor
	 * @param orderId
	 * @throws OrderException
	 * @roseuid 5850AE8E022C
	 */
	public void cancel(Order order, Doctor doctor) throws OrderException;

	/**
	 * 删除医嘱条目
	 * 
	 * @param orderId
	 * @param doctor
	 * @throws OrderException
	 */
	public void delete(Order order, Doctor doctor) throws OrderException;

	public List<Prescription> findPrescriptions(Visit visit);

	public Prescription findThePrescription(Order order);

	/**
	 * @roseuid 585250700266
	 */
	public Order find(String orderId);

	/**
	 * @roseuid 585250700266
	 */
	public Iterator<Order> find(List<String> orderIds);

	public void createOrderTypes(List<OrderType> orderTypes);

	public void createOrderFrequencyTypes(
			List<OrderFrequencyType> orderFrequencyTypes);

	public void clearOrders();

	public void clearOrderTypes();

	public void clearOrderTypeApps();

	public void clearCompsiteOrdes();

	public void clearPrescriptions();

	public void clearOrderFrequencyTypes();

	public void createAssistMaterials(List<AssistMaterial> assistMaterials);
}
