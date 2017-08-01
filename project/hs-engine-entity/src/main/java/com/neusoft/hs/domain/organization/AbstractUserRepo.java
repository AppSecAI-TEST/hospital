//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitRepo.java

package com.neusoft.hs.domain.organization;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

interface AbstractUserRepo extends
		PagingAndSortingRepository<AbstractUser, String> {

	@Query("select d from Doctor d")
	List<Doctor> findDoctor(Pageable pageable);

	@Query("select n from Nurse n where n.dept in (:depts)")
	List<Nurse> findNurse(@Param("depts") List<Dept> depts, Pageable pageable);

	@Query("select a from Admin a")
	List<Admin> findAdmin(Pageable pageable);

	@Query("select d from Doctor d where id = :id")
	Doctor findDoctor(@Param("id") String id);

	@Query("select n from Nurse n where n.id = :id")
	Nurse findNurse(@Param("id") String id);
}
