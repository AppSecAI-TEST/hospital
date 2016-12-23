//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderDomainService.java

package com.neusoft.hs.domain.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
public class OrderDomainService {

	@Autowired
	private OrderTypeRepo orderTypeRepo;

	@Autowired
	private PharmacyDomainService pharmacyDomainService;

	@Autowired
	private VisitDomainService visitDomainService;

	/**
	 * @param doctor
	 * @param order
	 * @throws HsException
	 * @roseuid 584E526102FB
	 */
	public void create(Order order, Doctor doctor) throws HsException {

		order.check();
		
		order.setCreateDate(DateUtil.getSysDate());
		order.setCreator(doctor);
		order.setState(Order.State_Created);
		
		order.save();
	}

	/**
	 * @roseuid 584F489E03D2
	 */
	public void check() {

	}

	/**
	 * @roseuid 584F49010391
	 */
	public void resolve() {

	}

	/**
	 * @roseuid 5850AE8E022C
	 */
	public void cancel() {

	}

	/**
	 * @roseuid 585250700266
	 */
	public void find() {

	}

	public void createOrderTypes(List<OrderType> orderTypes) {
		orderTypeRepo.save(orderTypes);
	}

	public void clearOrderTypes() {
		orderTypeRepo.deleteAll();
	}
}
