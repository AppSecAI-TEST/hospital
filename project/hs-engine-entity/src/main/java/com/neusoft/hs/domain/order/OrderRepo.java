//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderRepo.java

package com.neusoft.hs.domain.order;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.visit.Visit;

interface OrderRepo extends PagingAndSortingRepository<Order, String> {

	List<Order> findByStateAndBelongDept(String state, Dept dept,
			Pageable pageable);

	List<Order> findByStateAndBelongDeptIn(String state, List<Dept> depts,
			Pageable pageable);

	List<Order> findByVisit(Visit visit, Pageable pageable);

	List<Order> findByBelongDept(Dept dept, Pageable pageable);

	List<Order> findByVisitAndOrderTypeAndState(Visit visit,
			OrderType orderType, String state);

	@Query("select o from LongOrder o where o.state = :state and o.placeType = :placeType")
	List<LongOrder> findLongOrder(@Param("state") String state,
			@Param("placeType") String placeType);

	@Query("select o from LongOrder o where o.visit = :visit and o.state in(:states)")
	List<LongOrder> findLongOrderByVisit(@Param("visit") Visit visit,
			@Param("states") List<String> states, Pageable pageable);

	@Query("select o from TemporaryOrder o where o.visit = :visit and o.state in(:states)")
	List<TemporaryOrder> findTemporaryOrder(@Param("visit") Visit visit,
			@Param("states") List<String> states, Pageable pageable);

}
