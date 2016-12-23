//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderExecuteRepo.java

package com.neusoft.hs.domain.order;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.organization.InPatientDept;

public interface OrderExecuteRepo extends
		PagingAndSortingRepository<OrderExecute, String> {

	List<OrderExecute> findByStateAndBelongDept(String state, InPatientDept dept);

}
