//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\outpatientoffice\\OutpatientPlanDomainService.java

package com.neusoft.hs.domain.outpatientoffice;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

	public void createPlanRecords(List<OutPatientPlanRecord> planRecords) {
		outpatientPlanRecordRepo.save(planRecords);
	}

	/**
	 * @roseuid 58B7C82C00AD
	 */
	public List<OutPatientPlanRecord> listPlanRecord(Pageable pageable) {
		return outpatientPlanRecordRepo.findAll(pageable).getContent();
	}

	public OutPatientPlanRecord findPlanRecord(String planRecordId) {
		return outpatientPlanRecordRepo.findOne(planRecordId);
	}

	/**
	 * @roseuid 58B7C82C00AD
	 */
	public List<OutPatientPlanRecord> findNotFullPlanRecord(Date date) {
		return outpatientPlanRecordRepo.findNotFullPlanRecord(date);
	}

}
