package com.neusoft.hs.domain.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.visit.Visit;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderExecuteAdminService {
	
	@Autowired
	private OrderExecuteRepo orderExecuteRepo;
	
	@Autowired
	private OrderExecuteTeamRepo orderExecuteTeamRepo;
	
	public List<OrderExecute> find(Visit visit, Pageable pageable) {
		return orderExecuteRepo.findByVisit(visit, pageable);
	}

	public void clearExecuteTeams() {
		orderExecuteTeamRepo.deleteAll();
	}

}
