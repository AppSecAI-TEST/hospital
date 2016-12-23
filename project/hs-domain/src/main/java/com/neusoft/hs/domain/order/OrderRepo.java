//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderRepo.java

package com.neusoft.hs.domain.order;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.organization.Dept;

public interface OrderRepo extends PagingAndSortingRepository<Order, String> {

	List<Order> findByStateAndBelongDept(String state, Dept dept,
			Pageable pageable);

}
