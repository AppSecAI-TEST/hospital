//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitLogRepo.java

package com.neusoft.hs.domain.visit;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

interface VisitLogRepo extends PagingAndSortingRepository<VisitLog, String> {

	List<VisitLog> findByVisit(Visit visit, Pageable pageable);

}
