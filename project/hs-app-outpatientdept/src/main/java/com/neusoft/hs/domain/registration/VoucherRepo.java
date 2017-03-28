//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderRepo.java

package com.neusoft.hs.domain.registration;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;

interface VoucherRepo extends PagingAndSortingRepository<Voucher, String> {

	Voucher findByPlanRecordAndNumber(OutPatientPlanRecord record,
			Integer number);

}
