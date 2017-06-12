package com.neusoft.hs.domain.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.visit.Visit;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderDAO {

	@Autowired
	private OrderRepo orderRepo;

	public List<Order> findExecutingByVisitAndOrderType(Visit visit, OrderType orderType) {
		return orderRepo.findByVisitAndOrderTypeAndState(visit, orderType,
				Order.State_Executing);
	}

}
