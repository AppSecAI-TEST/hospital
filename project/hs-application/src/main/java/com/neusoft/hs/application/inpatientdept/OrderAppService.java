//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\inpatientdept\\OrderAppService.java

package com.neusoft.hs.application.inpatientdept;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderDomainService;
import com.neusoft.hs.domain.organization.Doctor;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderAppService {

	/**
	 * @param user002
	 * @param order
	 * @roseuid 584E5239011A
	 */
	public void create(Order order, Doctor user002) {

	}

	/**
	 * @roseuid 584F48660279
	 */
	public void check() {

	}

	/**
	 * @roseuid 5850ADFE001C
	 */
	public void cancel() {

	}

	/**
	 * @roseuid 5850EE16024D
	 */
	public void resolve() {

	}
}
