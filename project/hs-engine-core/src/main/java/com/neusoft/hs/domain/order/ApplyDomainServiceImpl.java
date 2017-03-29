package com.neusoft.hs.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ApplyDomainServiceImpl implements ApplyDomainService{

	@Autowired
	private ApplyRepo applyRepo;

	public Apply find(String applyId) {
		return applyRepo.findOne(applyId);
	}

	public void save(Apply apply) {
		applyRepo.save(apply);
	}

}
