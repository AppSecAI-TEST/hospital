//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitRepo.java

package com.neusoft.hs.domain.visit;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.neusoft.hs.domain.organization.Dept;

interface VisitRepo extends PagingAndSortingRepository<Visit, String> {

	List<Visit> findByState(String state, Pageable pageable);

	List<Visit> findByStateAndDept(String state, Dept dept, Pageable pageable);

	List<Visit> findByDept(Dept dept, Pageable pageable);

	@Modifying
	@Query("update Visit v set v.state = :newState where v.voucherDate < :changeDate AND v.state = :oldState")
	int changeVisitState(@Param("newState") String newState,
			@Param("oldState") String oldState,
			@Param("changeDate") Date changeDate);

	Visit findByLastAndCardNumber(Boolean last, String cardNumber);
}
