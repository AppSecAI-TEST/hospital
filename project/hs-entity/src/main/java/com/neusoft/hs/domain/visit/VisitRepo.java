//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitRepo.java

package com.neusoft.hs.domain.visit;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

interface VisitRepo extends PagingAndSortingRepository<Visit, String> {

	List<Visit> findByState(String state, Pageable pageable);
}
