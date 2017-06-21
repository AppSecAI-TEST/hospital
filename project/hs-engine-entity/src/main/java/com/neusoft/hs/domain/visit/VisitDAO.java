package com.neusoft.hs.domain.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitDAO {

	@Autowired
	private VisitRepo visitRepo;

	public Visit find(String id) {
		return visitRepo.findOne(id);
	}

}
