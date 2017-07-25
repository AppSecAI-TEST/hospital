//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\app\\pharmacy\\domain\\ConfigureFluidDomainService.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.ConfigureFluidOrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.platform.log.LogUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigureFluidDomainService {

	@Autowired
	private ConfigureFluidBatchRepo configureFluidBatchRepo;

	@Autowired
	private ConfigureFluidOrderRepo configureFluidOrderRepo;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * @param executes
	 * @param area
	 * @param batch
	 * @param pharmacy
	 * @roseuid 592E6DFF034D
	 */
	public ConfigureFluidOrder createOrder(InPatientAreaDept area,
			ConfigureFluidBatch batch,
			List<ConfigureFluidOrderExecute> executes, AbstractUser user) {
		ConfigureFluidOrder fluidOrder = new ConfigureFluidOrder();

		fluidOrder.setExecutes(executes);
		fluidOrder.setCreator(user);
		fluidOrder.setArea(area);
		fluidOrder.setBatch(batch);
		fluidOrder.setPharmacy((Pharmacy) user.getDept());
		fluidOrder.setState(ConfigureFluidOrder.State_NeedExecute);

		fluidOrder.save();

		applicationContext.publishEvent(new ConfigureFluidOrderCreatedEvent(
				fluidOrder));

		LogUtil.log(this.getClass(), "人员[{}]创建了住院病区[{}]批次为[{}]的配液单[{}]",
				user.getId(), area.getName(), batch.getName(),
				fluidOrder.getId());

		return fluidOrder;

	}

	/**
	 * @param fluidOrder
	 * @throws OrderExecuteException
	 * @roseuid 5930F4D20354
	 */
	public void finishOrder(ConfigureFluidOrder fluidOrder, AbstractUser user)
			throws OrderExecuteException {
		for (ConfigureFluidOrderExecute execute : fluidOrder.getExecutes()) {
			orderExecuteDomainService.finish(execute, null, user);
		}

		fluidOrder.finish(user);

		applicationContext.publishEvent(new ConfigureFluidOrderFinishedEvent(
				fluidOrder));

		LogUtil.log(this.getClass(), "人员[{}]将配液单[{}]设置为完成", user.getId(),
				fluidOrder.getId());

	}

	public ConfigureFluidOrder getConfigureFluidOrder(String id) {
		return this.configureFluidOrderRepo.findOne(id);
	}

	public void clearConfigureFluidOrder() {
		configureFluidOrderRepo.deleteAll();
	}

	public void createConfigureFluidBatchs(List<ConfigureFluidBatch> batchs) {
		configureFluidBatchRepo.save(batchs);
	}

	public void clearConfigureFluidBatch() {
		configureFluidBatchRepo.deleteAll();
	}

	public List<ConfigureFluidOrder> findConfigureFluidOrder(
			InPatientAreaDept area, ConfigureFluidBatch batch, Pageable pageable) {
		return this.configureFluidOrderRepo.findByAreaAndBatch(area, batch,
				pageable);
	}

}
