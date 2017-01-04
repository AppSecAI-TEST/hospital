//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderExecuteRepo.java

package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.InPatientDept;

interface OrderExecuteRepo extends
		PagingAndSortingRepository<OrderExecute, String> {

	List<OrderExecute> findByStateAndBelongDeptAndPlanStartDateLessThan(
			String state, InPatientDept dept, Date planStartDate,
			Pageable pageable);

	List<OrderExecute> findByStateAndExecuteDept(String state, Dept dept,
			Pageable pageable);

	List<OrderExecute> findByChargeState(String chargeState, Pageable pageable);

	@Modifying
	@Query("update OrderExecute e set e.state = :newState, e.startDate = :sysDate where e.planStartDate < :startDate AND e.state = :oldState AND e.previousId = null AND e.visit in (select b.visit from ChargeBill b where b.balance > 0 AND b.state = :chargeBillState)")
	int start(@Param("newState") String newState,
			@Param("oldState") String oldState,
			@Param("chargeBillState") String chargeBillState,
			@Param("sysDate") Date sysDate, @Param("startDate") Date startDate);

	@Query("select e from OrderExecute e where e.order.id = :orderId and e.nextId = null")
	OrderExecute findByLastExecute(@Param("orderId") String orderId);

}
