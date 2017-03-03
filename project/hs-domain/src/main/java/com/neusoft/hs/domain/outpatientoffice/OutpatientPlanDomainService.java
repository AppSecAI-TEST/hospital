//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\outpatientoffice\\OutpatientPlanDomainService.java

package com.neusoft.hs.domain.outpatientoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OutPatientPlanDomainService {

	@Autowired
	private OutPatientPlanRecordRepo outpatientPlanRecordRepo;

	/**
	 * @roseuid 58B7C812025D
	 */
	public void createPlanRecord(OutPatientPlanRecord planRecord) {
		outpatientPlanRecordRepo.save(planRecord);
	}

	/**
	 * @roseuid 58B7C82C00AD
	 */
	public void listPlanRecord() {

	}
}
