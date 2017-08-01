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

	@Autowired
	private VisitLogRepo visitLogRepo;

	public void clear() {
		visitRepo.deleteAll();
	}

	/**
	 * @param visitId
	 * @roseuid 584E03140020
	 */
	public Visit find(String visitId) {
		return visitRepo.findOne(visitId);
	}

	public List<Visit> find(Pageable pageable) {
		return visitRepo.findAll(pageable).getContent();
	}

	public List<VisitLog> getVisitLogs(Visit visit, Pageable pageable) {
		return visitLogRepo.findByVisit(visit, pageable);
	}

	public void delete(String cardNumber) {
		Visit visit = this.visitRepo.findByCardNumber(cardNumber);
		if (visit != null) {
			visit.delete();
		}

	}

}
