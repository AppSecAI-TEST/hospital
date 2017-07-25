//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\app\\pharmacy\\application\\ConfigureFluidAppService.java

package com.neusoft.hs.application.pharmacy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.DispensingDrugBatchFilter;
import com.neusoft.hs.domain.order.DispensingDrugOrderExecute;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.domain.pharmacy.DispensingDrugBatch;
import com.neusoft.hs.domain.pharmacy.DispensingDrugOrder;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;
import com.neusoft.hs.domain.pharmacy.PharmacyException;
import com.neusoft.hs.platform.print.PrintDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class InPatientPharmacyAppService {

	@Autowired
	private PharmacyDomainService pharmacyDomainService;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private PrintDomainService printDomainService;

	/**
	 * @param batch
	 * @param inpatientAreaDept
	 * @throws OrderExecuteException
	 * @roseuid 592E613203CB
	 */
	public DispensingDrugOrder print(InPatientAreaDept area,
			DispensingDrugBatch batch, AbstractUser user)
			throws OrderExecuteException {
		DispensingDrugBatchFilter filter = new DispensingDrugBatchFilter();

		filter.setArea(area);
		filter.setBatch(batch);

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<OrderExecute> executes = orderExecuteAppService.find(filter, null,
				user, pageable);

		if (executes == null || executes.size() == 0) {
			return null;
		}

		List<DispensingDrugOrderExecute> orderExecutes = new ArrayList<DispensingDrugOrderExecute>();
		executes.forEach(item -> {
			if (item instanceof DispensingDrugOrderExecute) {
				orderExecutes.add((DispensingDrugOrderExecute) item);
			} else {
				throw new IllegalArgumentException(item.getId() + " 的类型为["
						+ item.getClass().getName()
						+ "]不是ConfigureFluidOrderExecute", null);
			}
		});

		DispensingDrugOrder dispensingDrugOrder = pharmacyDomainService
				.createOrder(area, batch, orderExecutes, user);

		printDomainService.print(dispensingDrugOrder);

		return dispensingDrugOrder;
	}

	/**
	 * @param dispensingDrugOrderId
	 * @throws PharmacyException
	 * @throws OrderExecuteException
	 * @roseuid 5930F45401DC
	 */
	public DispensingDrugOrder dispense(String dispensingDrugOrderId,
			AbstractUser user) throws PharmacyException, OrderExecuteException {
		DispensingDrugOrder dispensingDrugOrder = pharmacyDomainService
				.getDispensingDrugOrder(dispensingDrugOrderId);
		if (dispensingDrugOrder == null) {
			throw new PharmacyException("dispensingDrugOrderId=[%s]不存在",
					dispensingDrugOrderId);
		}
		pharmacyDomainService.dispenseOrder(dispensingDrugOrder, user);

		return dispensingDrugOrder;
	}

	public DispensingDrugOrder distribute(String dispensingDrugOrderId,
			AbstractUser user) throws PharmacyException, OrderExecuteException {

		DispensingDrugOrder dispensingDrugOrder = pharmacyDomainService
				.getDispensingDrugOrder(dispensingDrugOrderId);
		if (dispensingDrugOrder == null) {
			throw new PharmacyException("dispensingDrugOrderId=[%s]不存在",
					dispensingDrugOrderId);
		}
		pharmacyDomainService.distributeOrder(dispensingDrugOrder, user);

		return dispensingDrugOrder;
	}

	public List<DispensingDrugOrder> find(InPatientAreaDept area,
			DispensingDrugBatch batch, Pageable pageable) {
		return pharmacyDomainService.findDispensingDrugOrder(area, batch,
				pageable);
	}
}
