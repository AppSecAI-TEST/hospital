//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\outpatientdept\\OutPatientDeptDomainService.java

package com.neusoft.hs.domain.outpatientdept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanDomainService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;

@Service
@Transactional(rollbackFor = Exception.class)
public class OutPatientDeptDomainService {

	@Autowired
	private OutPatientPlanDomainService outPatientPlanDomainService;

	/**
	 * @roseuid 58BE187701AE
	 */
	public OutPatientDeptDomainService() {

	}

	/**
	 * 叫号
	 * 
	 * @param planRecordId
	 * @throws OutPatientDeptException
	 * @roseuid 58BE14CB03A3
	 */
	public boolean nextVoucher(String planRecordId)
			throws OutPatientDeptException {
		OutPatientPlanRecord record = outPatientPlanDomainService
				.findPlanRecord(planRecordId);
		if (record == null) {
			throw new OutPatientDeptException("门诊医生排班记录[" + planRecordId
					+ "]不存在");
		}
		boolean rtn = record.nextVoucher();

		return rtn;
	}
}
