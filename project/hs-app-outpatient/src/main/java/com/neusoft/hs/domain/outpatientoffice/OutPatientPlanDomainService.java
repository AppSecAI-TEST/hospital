//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\outpatientoffice\\OutpatientPlanDomainService.java

package com.neusoft.hs.domain.outpatientoffice;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OutPatientPlanDomainService {

	@Autowired
	private OutPatientPlanRecordRepo outpatientPlanRecordRepo;

	/**
	 * @throws HsException
	 * @roseuid 58B7C812025D
	 */
	public void createPlanRecord(OutPatientPlanRecord planRecord)
			throws HsException {

		List<OutPatientPlanRecord> records = outpatientPlanRecordRepo.find(
				planRecord.getDoctor(), planRecord.getPlanStartDate(),
				planRecord.getPlanEndDate());
		if (records.size() > 0) {
			throw new HsException("医生[%s]要创建的出诊记录由时间重复，[%s]", planRecord
					.getDoctor().getName(), records.get(0).getId());
		}
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

	public OutPatientPlanRecord findPlanRecord(AbstractUser doctor, Date date) {
		return outpatientPlanRecordRepo
				.findByDoctorAndPlanStartDateLessThanAndPlanEndDateGreaterThan(
						doctor, date, date);
	}

	/**
	 * @roseuid 58B7C82C00AD
	 */
	public List<OutPatientPlanRecord> findNotFullPlanRecord(Date date) {
		return outpatientPlanRecordRepo.findNotFullPlanRecord(date);
	}

}
