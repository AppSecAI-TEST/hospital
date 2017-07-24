//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\VisitChargeItemRepo.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

interface DispenseDrugWinRepo extends PagingAndSortingRepository<DispenseDrugWin, String> {

	List<DispenseDrugWin> findByPharmacy(Pharmacy pharmacy);

}
