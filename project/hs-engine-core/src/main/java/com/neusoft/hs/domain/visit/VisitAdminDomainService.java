package com.neusoft.hs.domain.visit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitAdminDomainService {

	@Autowired
	private VisitRepo visitRepo;

	public void clear() {
		visitRepo.deleteAll();
	}

	public List<Visit> find(Pageable pageable) {
		return visitRepo.findAll(pageable).getContent();
	}

}
