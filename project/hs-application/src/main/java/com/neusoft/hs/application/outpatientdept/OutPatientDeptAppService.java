//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\outpatientdept\\OutPatientDeptAppService.java

package com.neusoft.hs.application.outpatientdept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.outpatientdept.OutPatientDeptDomainService;
import com.neusoft.hs.domain.outpatientdept.OutPatientDeptException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OutPatientDeptAppService {

	@Autowired
	private OutPatientDeptDomainService outPatientDeptDomainService;

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
		return outPatientDeptDomainService.nextVoucher(planRecordId);
	}
}
