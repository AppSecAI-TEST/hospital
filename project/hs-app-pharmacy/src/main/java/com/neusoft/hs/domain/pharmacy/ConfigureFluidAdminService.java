//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\app\\pharmacy\\domain\\ConfigureFluidDomainService.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigureFluidAdminService {

	@Autowired
	private ConfigureFluidBatchRepo configureFluidBatchRepo;

	public List<ConfigureFluidBatch> findConfigureFluidBatchs(Pharmacy pharmacy) {
		return configureFluidBatchRepo.findByPharmacy(pharmacy);
	}

}
