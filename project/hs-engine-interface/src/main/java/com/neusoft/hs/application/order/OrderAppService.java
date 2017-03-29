//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\inpatientdept\\OrderAppService.java

package com.neusoft.hs.application.order;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.platform.exception.HsException;

public interface OrderAppService {

	/**
	 * @param doctor
	 * @param order
	 * @throws HsException
	 * @roseuid 584E5239011A
	 */
	public List<Order> create(OrderCreateCommand orderCommand, Doctor doctor)
			throws OrderException;

	public List<Order> getNeedVerifyOrders(Nurse nurse, Pageable pageable);

	/**
	 * @param nurse
	 * @param orderId
	 * @throws HsException
	 * @roseuid 584F48660279
	 */
	public Order verify(String orderId, Nurse nurse) throws OrderException;

	/**
	 * @param doctor
	 * @param orderId
	 * @throws OrderException
	 * @roseuid 5850ADFE001C
	 */
	public void cancel(String orderId, Doctor doctor) throws OrderException;

	public void delete(String orderId, Doctor doctor) throws OrderException;

	/**
	 * @roseuid 5850EE16024D
	 */
	public int resolve();

	public List<OrderExecute> getNeedSendOrderExecutes(Nurse nurse,
			Pageable pageable);
}
