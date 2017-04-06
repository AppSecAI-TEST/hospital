package com.neusoft.hs.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "myDao")
@Transactional(rollbackFor = Exception.class)
public class OrderExecuteDAO {

	@Autowired
	private OrderExecuteRepo orderExecuteRepo;
	
	public OrderExecuteDAO(){
		System.out.println("OrderExecuteDAO create");
	}

	public OrderExecute find(String id) {
		return orderExecuteRepo.findOne(id);
	}

}
