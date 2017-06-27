//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\outpatientdept\\OutPatientDeptAppService.java

package com.neusoft.hs.application.outpatientdept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.outpatientdept.OutPatientDeptDomainService;
import com.neusoft.hs.domain.outpatientdept.OutPatientDeptException;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanDomainService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;

@Service
@Transactional(rollbackFor = Exception.class)
public class OutPatientDeptAppService {

	@Autowired
	private OutPatientDeptDomainService outPatientDeptDomainService;

	@Autowired
	private OutPatientPlanDomainService outPatientPlanDomainService;

	/**
	 * @roseuid 58BE1877017D
	 */
	public OutPatientDeptAppService() {

	}

	/**
	 * @throws OutPatientDeptException
	 * @roseuid 58BE144801EB
	 */
	public boolean nextVoucher(String planRecordId)
			throws OutPatientDeptException {
		OutPatientPlanRecord record = outPatientPlanDomainService
				.findPlanRecord(planRecordId);
		if (record == null) {
			throw new OutPatientDeptException("门诊医生排班记录[%s]不存在", planRecordId);
		}
		return outPatientDeptDomainService.nextVoucher(record);
	}
}
