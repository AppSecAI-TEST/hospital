//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderRepo.java

package com.neusoft.hs.domain.outpatientoffice;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

interface OutPatientPlanRecordRepo extends
		PagingAndSortingRepository<OutPatientPlanRecord, String> {

	@Query("select r from OutPatientPlanRecord r where r.planStartDate <= :date and r.planEndDate >= :date and r.maxAllotNumber >= r.currentAllotNumber")
	List<OutPatientPlanRecord> findNotFullPlanRecord(@Param("date") Date date);

}
