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

	@Query("select o from LongOrder o where o.state = :state and o.placeType = :placeType")
	List<LongOrder> findLongOrderByStateAndPlaceType(
			@Param("state") String state, @Param("placeType") String placeType);

	@Query("select o from LongOrder o where o.visit = :visit")
	List<LongOrder> findLongOrderByVisit(@Param("visit") Visit visit);

	@Query("select o from TemporaryOrder o where o.visit = :visit")
	List<TemporaryOrder> findTemporaryOrderByVisit(@Param("visit") Visit visit,
			Pageable pageable);

}
