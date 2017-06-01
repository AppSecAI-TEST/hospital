//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\app\\pharmacy\\domain\\ConfigureFluidDomainService.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.ConfigureFluidOrderExecute;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.InPatientAreaDept;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigureFluidDomainService {

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
		fluidOrder.setPharmacy((Pharmacy)user.getDept());

		fluidOrder.save();

		return fluidOrder;

	}
}
