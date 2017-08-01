//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitRepo.java

package com.neusoft.hs.domain.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AbstractUserDAO {

	@Autowired
	private AbstractUserRepo abstractUserRepo;

	public Nurse findNurse(String id) {
		return abstractUserRepo.findNurse(id);
	}
}
