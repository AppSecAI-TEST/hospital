//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderRepo.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

interface ConfigureFluidBatchRepo extends
		PagingAndSortingRepository<ConfigureFluidBatch, String> {

	List<ConfigureFluidBatch> findByPharmacy(Pharmacy pharmacy);

}
