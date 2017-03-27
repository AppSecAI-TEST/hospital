//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\outpatientdept\\OutPatientDeptDomainService.java

package com.neusoft.hs.domain.outpatientdept;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.platform.log.LogUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class OutPatientDeptDomainService {

	/**
	 * 叫号
	 * 
	 * @param record
	 * @throws OutPatientDeptException
	 * @roseuid 58BE14CB03A3
	 */
	public boolean nextVoucher(OutPatientPlanRecord record)
			throws OutPatientDeptException {

		boolean rtn = record.nextVoucher();

		LogUtil.log(this.getClass(), "医生[{}]叫号", record.getDoctor().getId());

		return rtn;
	}
}
