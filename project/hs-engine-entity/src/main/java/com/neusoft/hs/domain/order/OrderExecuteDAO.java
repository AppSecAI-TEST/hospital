package com.neusoft.hs.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderExecuteDAO {

	@Autowired
	private OrderExecuteRepo orderExecuteRepo;

	public OrderExecute find(String id) {
		return orderExecuteRepo.findOne(id);
	}

}
