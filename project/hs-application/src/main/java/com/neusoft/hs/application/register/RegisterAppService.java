//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\application\\register\\RegisterAppService.java

package com.neusoft.hs.application.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.user.User;

@Service
@Transactional(rollbackFor = Exception.class)
public class RegisterAppService {

	@Autowired
	private VisitDomainService visitDomainService;

	/**
	 * @roseuid 584A697D031B
	 */
	public void register(Visit visit, User user) {
		visitDomainService.create(visit, user);

	}
}
