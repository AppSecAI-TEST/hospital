//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\DrugTypeRepo.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface DrugTypeRepo extends
		PagingAndSortingRepository<DrugType, String> {

	List<DrugType> findByDrugTypeSpec(DrugTypeSpec drugTypeSpec);

}
