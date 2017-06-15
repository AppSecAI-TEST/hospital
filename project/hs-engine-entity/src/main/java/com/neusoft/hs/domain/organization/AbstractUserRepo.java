//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitRepo.java

package com.neusoft.hs.domain.organization;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

interface AbstractUserRepo extends
		PagingAndSortingRepository<AbstractUser, String> {

	@Query("select d from Doctor d")
	List<Doctor> findDoctor(Pageable pageable);

	@Query("select n from Nurse n")
	List<Nurse> findNurse(Pageable pageable);
}
