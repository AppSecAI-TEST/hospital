//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\app\\pharmacy\\domain\\ConfigureFluidOrderRepo.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

interface DispensingDrugBatchRepo extends
		PagingAndSortingRepository<DispensingDrugBatch, String> {

	List<DispensingDrugBatch> findByPharmacy(Pharmacy pharmacy);
}
