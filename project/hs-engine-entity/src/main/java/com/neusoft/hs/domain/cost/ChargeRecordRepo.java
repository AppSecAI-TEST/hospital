//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\ChargeRecordRepo.java

package com.neusoft.hs.domain.cost;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.visit.Visit;

interface ChargeRecordRepo extends
		PagingAndSortingRepository<ChargeRecord, String> {

	List<ChargeRecord> findByVisit(Visit visit, Pageable pageable);

	List<ChargeRecord> findByVisitAndBelongDeptIn(Visit visit,
			List<Dept> depts, Pageable pageable);

	@Query(value = "select r from ChargeRecord r where r.visit = :visit and r.chargeItem = :chargeItem and r.createDate >= :startDate and r.createDate < :endDate")
	List<ChargeRecord> findByVisitAndChargeItemAndCreateDate(
			@Param("visit") Visit visit,
			@Param("chargeItem") ChargeItem chargeItem,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
