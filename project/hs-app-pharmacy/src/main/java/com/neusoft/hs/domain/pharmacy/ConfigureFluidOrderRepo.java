//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\app\\pharmacy\\domain\\ConfigureFluidOrderRepo.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.organization.InPatientAreaDept;

interface ConfigureFluidOrderRepo extends
		PagingAndSortingRepository<ConfigureFluidOrder, String> {

	List<ConfigureFluidOrder> findByAreaAndBatch(InPatientAreaDept area,
			ConfigureFluidBatch batch, Pageable pageable);
}
