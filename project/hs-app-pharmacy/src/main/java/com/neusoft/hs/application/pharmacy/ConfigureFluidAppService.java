//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\app\\pharmacy\\application\\ConfigureFluidAppService.java

package com.neusoft.hs.application.pharmacy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.ConfigureFluidBatchFilter;
import com.neusoft.hs.domain.order.ConfigureFluidOrderExecute;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.domain.pharmacy.ConfigureFluidBatch;
import com.neusoft.hs.domain.pharmacy.ConfigureFluidDomainService;
import com.neusoft.hs.domain.pharmacy.ConfigureFluidOrder;
import com.neusoft.hs.domain.pharmacy.PharmacyException;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.print.PrintDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigureFluidAppService {

	@Autowired
	private ConfigureFluidDomainService configureFluidDomainService;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private PrintDomainService printDomainService;

	/**
	 * @param batch
	 * @param inpatientAreaDept
	 * @throws HsException
	 * @roseuid 592E613203CB
	 */
	public ConfigureFluidOrder print(InPatientAreaDept area,
			ConfigureFluidBatch batch, AbstractUser user) throws HsException {
		ConfigureFluidBatchFilter filter = new ConfigureFluidBatchFilter();

		filter.setArea(area);
		filter.setBatch(batch);

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<OrderExecute> executes = orderExecuteAppService.find(filter, null,
				user, pageable);

		if (executes == null || executes.size() == 0) {
			return null;
		}

		List<ConfigureFluidOrderExecute> fluidOrderExecutes = new ArrayList<ConfigureFluidOrderExecute>();
		executes.forEach(item -> {
			if (item instanceof ConfigureFluidOrderExecute) {
				fluidOrderExecutes.add((ConfigureFluidOrderExecute) item);
			} else {
				throw new IllegalArgumentException(item.getId() + " 的类型为["
						+ item.getClass().getName()
						+ "]不是ConfigureFluidOrderExecute", null);
			}
		});

		ConfigureFluidOrder fluidOrder = configureFluidDomainService
				.createOrder(area, batch, fluidOrderExecutes, user);

		printDomainService.print(fluidOrder);

		return fluidOrder;
	}

	/**
	 * 完成配液单
	 * 
	 * @param fluidOrderId
	 * @throws PharmacyException
	 * @throws OrderExecuteException
	 * @roseuid 5930F45401DC
	 */
	public ConfigureFluidOrder finish(String fluidOrderId, AbstractUser user)
			throws PharmacyException, OrderExecuteException {
		ConfigureFluidOrder fluidOrder = configureFluidDomainService
				.getConfigureFluidOrder(fluidOrderId);
		if (fluidOrder == null) {
			throw new PharmacyException("fluidOrderId=[%s]不存在", fluidOrderId);
		}
		configureFluidDomainService.finishOrder(fluidOrder, user);

		return fluidOrder;
	}

	/**
	 * 发送配液单
	 * 
	 * @param fluidOrderId
	 * @throws PharmacyException
	 * @throws OrderExecuteException
	 * @roseuid 5930F45401DC
	 */
	public ConfigureFluidOrder distribute(String fluidOrderId, AbstractUser user)
			throws PharmacyException, OrderExecuteException {
		ConfigureFluidOrder fluidOrder = configureFluidDomainService
				.getConfigureFluidOrder(fluidOrderId);
		if (fluidOrder == null) {
			throw new PharmacyException("fluidOrderId=[%s]不存在", fluidOrderId);
		}
		configureFluidDomainService.distributeOrder(fluidOrder, user);

		return fluidOrder;
	}

	public List<ConfigureFluidOrder> find(InPatientAreaDept area,
			ConfigureFluidBatch batch, Pageable pageable) {
		return configureFluidDomainService.findConfigureFluidOrder(area, batch,
				pageable);
	}
}
