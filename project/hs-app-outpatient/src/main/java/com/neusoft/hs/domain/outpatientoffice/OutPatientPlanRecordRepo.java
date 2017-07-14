//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderRepo.java

package com.neusoft.hs.domain.outpatientoffice;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.neusoft.hs.domain.organization.AbstractUser;

interface OutPatientPlanRecordRepo extends
		PagingAndSortingRepository<OutPatientPlanRecord, String> {

	@Query("select r from OutPatientPlanRecord r where r.planStartDate <= :date and r.planEndDate >= :date and r.maxAllotNumber >= r.currentAllotNumber")
	List<OutPatientPlanRecord> findNotFullPlanRecord(@Param("date") Date date);

	OutPatientPlanRecord findByDoctorAndPlanStartDateLessThanAndPlanEndDateGreaterThan(
			AbstractUser doctor, Date date, Date date2);

	@Query("select r from OutPatientPlanRecord r where r.doctor = :doctor and (r.planStartDate < :planEndDate and r.planEndDate > :planStartDate)")
	List<OutPatientPlanRecord> find(@Param("doctor") AbstractUser doctor,
			@Param("planStartDate") Date planStartDate,
			@Param("planEndDate") Date planEndDate);

}
