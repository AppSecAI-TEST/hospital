//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitRepo.java

package com.neusoft.hs.domain.visit;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.organization.Dept;

interface VisitRepo extends PagingAndSortingRepository<Visit, String> {

	List<Visit> findByState(String state, Pageable pageable);

	List<Visit> findByStateAndRespDept(String state, Dept dept,
			Pageable pageable);

	List<Visit> findByRespDept(Dept dept, Pageable pageable);
}
