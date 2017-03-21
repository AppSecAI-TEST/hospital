//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\pharmacy\\PharmacyAppService.java

package com.neusoft.hs.application.pharmacy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;

@Service
public class PharmacyAppService {

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	/**
	 * @roseuid 5850FF230373
	 */
	public List<OrderExecute> taskDrug(Visit visit, AbstractUser user,
			Pageable pageable) {
		return orderExecuteAppService.getNeedExecuteOrderExecutes(visit,
				OrderExecute.Type_Take_Drug, user, pageable);
	}
}
