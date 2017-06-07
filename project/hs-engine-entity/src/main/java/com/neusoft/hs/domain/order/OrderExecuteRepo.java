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
import com.neusoft.hs.domain.visit.Visit;

interface OrderExecuteRepo extends
		PagingAndSortingRepository<OrderExecute, String> {
	
	List<OrderExecute> findByState(String state, Pageable pageable);

	List<OrderExecute> findByStateAndBelongDeptAndPlanStartDateLessThan(
			String state, Dept dept, Date planStartDate, Pageable pageable);

	List<OrderExecute> findByStateAndBelongDeptInAndPlanStartDateLessThan(
			String state, List<Dept> depts, Date planStartDate,
			Pageable pageable);

	List<OrderExecute> findByStateAndExecuteDeptAndPlanStartDateLessThan(
			String state, Dept dept, Date planStartDate, Pageable pageable);

	List<OrderExecute> findByStateAndExecuteDeptInAndPlanStartDateLessThan(
			String state, List<Dept> depts, Date planStartDate,
			Pageable pageable);

	List<OrderExecute> findByVisitAndTypeAndStateAndExecuteDeptAndPlanStartDateLessThan(
			Visit visit, String type, String state, Dept dept,
			Date planStartDate, Pageable pageable);

	List<OrderExecute> findByChargeState(String chargeState, Pageable pageable);

	List<OrderExecute> findByStateAndBelongDeptInAndTypeInAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
			String state, List<? extends Dept> belongDepts, List<String> types,
			Date begin, Date end, Pageable pageable);

	List<OrderExecute> findByStateAndBelongDeptInAndTypeAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
			String state, List<? extends Dept> belongDepts, String type,
			Date begin, Date end, Pageable pageable);

	List<OrderExecute> findByStateAndBelongDeptAndTypeInAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
			String state, Dept belongDept, List<String> types, Date begin,
			Date end, Pageable pageable);

	List<OrderExecute> findByStateAndBelongDeptAndTypeAndPlanStartDateGreaterThanAndPlanEndDateLessThan(
			String state, Dept belongDept, String type, Date begin, Date end,
			Pageable pageable);

	@Modifying
	@Query("update OrderExecute e set e.state = :newState, e.startDate = :sysDate where e.planStartDate < :startDate AND e.state = :oldState AND e.previous = null AND e.visit in (select b.visit from ChargeBill b where b.balance > 0 AND b.state = :chargeBillState)")
	int start(@Param("newState") String newState,
			@Param("oldState") String oldState,
			@Param("chargeBillState") String chargeBillState,
			@Param("sysDate") Date sysDate, @Param("startDate") Date startDate);



}
