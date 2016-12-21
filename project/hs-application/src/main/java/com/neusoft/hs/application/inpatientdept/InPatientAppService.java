//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\inpatientdept\\InPatientAppService.java

package com.neusoft.hs.application.inpatientdept;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;

@Service
@Transactional(rollbackFor = Exception.class)
public class InPatientAppService {

	/**
	 * @roseuid 58573EC70055
	 */
	public InPatientAppService() {

	}

	/**
	 * @param user001
	 * @param receiveVisitVO
	 * @roseuid 584E114B01A1
	 */
	public void receive(ReceiveVisitVO receiveVisitVO, AbstractUser user) {

	}

	/**
	 * @roseuid 58524F350096
	 */
	public void leave() {

	}

	/**
	 * @roseuid 58524FAE0056
	 */
	public void checkLeave() {

	}
}
